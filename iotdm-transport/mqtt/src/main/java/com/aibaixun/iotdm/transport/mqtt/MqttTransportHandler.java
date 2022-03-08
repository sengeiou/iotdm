package com.aibaixun.iotdm.transport.mqtt;

import com.aibaixun.iotdm.enums.ProtocolType;
import com.aibaixun.iotdm.msg.DeviceAuthRespMsg;
import com.aibaixun.iotdm.msg.DeviceAuthSecretReqMsg;
import com.aibaixun.iotdm.msg.TransportSessionInfo;
import com.aibaixun.iotdm.msg.TransportSessionInfoCreator;
import com.aibaixun.iotdm.transport.TransportService;
import com.aibaixun.iotdm.transport.TransportServiceCallback;
import com.aibaixun.iotdm.transport.TransportSessionListener;
import com.aibaixun.iotdm.transport.mqtt.session.DeviceSessionCtx;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.util.UUID;

import static io.netty.handler.codec.mqtt.MqttConnectReturnCode.CONNECTION_ACCEPTED;
import static io.netty.handler.codec.mqtt.MqttConnectReturnCode.CONNECTION_REFUSED_NOT_AUTHORIZED;
import static io.netty.handler.codec.mqtt.MqttMessageType.*;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_MOST_ONCE;


/**
 * Mqtt transport handler
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class MqttTransportHandler extends ChannelInboundHandlerAdapter implements GenericFutureListener<Future<? super Void>>, TransportSessionListener {

    private final Logger log  = LoggerFactory.getLogger("mqtt-transport");

    /**
     * handler id
     */
    private final UUID sessionId;

    /**
     * 上下文
     */
    private final MqttTransportContext context;

    /**
     * transport service
     */
    private final TransportService transportService;

    /**
     * 设备session
     */
    private final DeviceSessionCtx deviceSessionCtx;

    /**
     * 地址
     */
    volatile InetSocketAddress address;



    MqttTransportHandler(MqttTransportContext context) {
        this.sessionId = UUID.randomUUID();
        this.context = context;
        this.transportService = context.getTransportService();
        this.deviceSessionCtx = new DeviceSessionCtx(sessionId,context);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelRegistered(channelHandlerContext);
        context.channelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelUnregistered(channelHandlerContext);
        context.channelUnregistered();
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        log.trace("MqttTransportHandler.channelRead >> [{}] Processing msg: {}", sessionId, msg);
        if (address == null) {
            address = getAddress(channelHandlerContext);
        }
        try {
            if (msg instanceof MqttMessage) {
                MqttMessage message = (MqttMessage) msg;
                if (message.decoderResult().isSuccess()) {
                    processMqttMsg(channelHandlerContext, message);
                } else {
                    log.error("MqttTransportHandler.channelRead >> [{}] Message decoding failed: {}", sessionId, message.decoderResult().cause().getMessage());
                    channelHandlerContext.close();
                }
            } else {
                log.debug("MqttTransportHandler.channelRead >> [{}] Received non mqtt message: {}", sessionId, msg.getClass().getSimpleName());
                channelHandlerContext.close();
            }
        } finally {
            ReferenceCountUtil.safeRelease(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        log.error("MqttTransportHandler.exceptionCaught >> [{}] Unexpected Exception", sessionId, cause);
        channelHandlerContext.close();
        if (cause instanceof OutOfMemoryError) {
            log.error("MqttTransportHandler.exceptionCaught >> Received critical error： OutOfMemoryError. Going to shutdown the service.");
            System.exit(1);
        }
    }

    @Override
    public void operationComplete(Future<? super Void> future) {
        log.trace("MqttTransportHandler.operationComplete >> [{}] Channel closed!", sessionId);
        doDisconnect();
    }

    /**
     * 处理mqtt 消息
     * @param channelHandlerContext ctx
     * @param msg mqtt 消息
     */
    void processMqttMsg(ChannelHandlerContext channelHandlerContext, MqttMessage msg) {
        if (msg.fixedHeader() == null) {
            log.info("MqttTransportHandler.processMqttMsg >> [{}:{}] Invalid message received", address.getHostName(), address.getPort());
            channelHandlerContext.close();
            return;
        }
        deviceSessionCtx.setChannel(channelHandlerContext);
        if (CONNECT.equals(msg.fixedHeader().messageType())) {
            processConnect(channelHandlerContext, (MqttConnectMessage) msg);
        }else {
            processSessionMsg(channelHandlerContext,msg);
        }
    }

    /**
     * 获取地址
     * @param channelHandlerContext  ctx
     * @return 地址
     */
    private InetSocketAddress getAddress(ChannelHandlerContext channelHandlerContext) {
        var address = channelHandlerContext.channel().attr(MqttTransportService.ADDRESS).get();
        if (address == null) {
            log.trace("MqttTransportHandler.getAddress >> [{}] Received empty address.", channelHandlerContext.channel().id());
            InetSocketAddress remoteAddress = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
            log.trace("MqttTransportHandler.getAddress >> [{}] Going to use address: {}", channelHandlerContext.channel().id(), remoteAddress);
            return remoteAddress;
        } else {
            log.trace("MqttTransportHandler.getAddress >> [{}] Received address: {}", channelHandlerContext.channel().id(), address);
        }
        return address;
    }

    /**
     * 处理连接消息
     * @param channelHandlerContext  ctx
     * @param mqttConnectMessage 连接报文
     */
    private void processConnect(ChannelHandlerContext channelHandlerContext,MqttConnectMessage mqttConnectMessage){
        String clientId = mqttConnectMessage.payload().clientIdentifier();
        log.debug("MqttTransportHandler.processConnect >> [{}][{}] Processing connect msg for client: {}!", address, sessionId, clientId);
        processAuthSecretConnect(channelHandlerContext, clientId,mqttConnectMessage);
    }

    /**
     * 设备密钥连接
     * @param channelHandlerContext ctx
     * @param connectMessage 连接报文
     */
    private void processAuthSecretConnect(ChannelHandlerContext channelHandlerContext,String clientId, MqttConnectMessage connectMessage) {
        String userName = connectMessage.payload().userName();
        byte[] passwordBytes = connectMessage.payload().passwordInBytes();
        String password = new String(passwordBytes, CharsetUtil.UTF_8);
        log.debug("MqttTransportHandler.processAuthSecretConnect >> [{}][{}] Processing connect msg for client with clientId: {} ,username: {} ,password:{}!", address, sessionId, clientId,userName,password);
        transportService.processDeviceAuthBySecret(ProtocolType.MQTT, new DeviceAuthSecretReqMsg(clientId, userName, password), new TransportServiceCallback<>() {
            @Override
            public void onSuccess(DeviceAuthRespMsg msg) {
                processDeviceAuthResponse(msg,channelHandlerContext,connectMessage);
            }
            @Override
            public void onError(Throwable e) {
                log.trace("MqttTransportHandler.processAuthSecretConnect [{}] [{}] Failed to process with clientId: {} ,username: {} ,password:{},error msg :{}", address, sessionId, clientId, userName, password, e.getMessage());
                channelHandlerContext.writeAndFlush(createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_REFUSED_SERVER_UNAVAILABLE, connectMessage));
                channelHandlerContext.close();
            }
        });
    }

    private MqttConnAckMessage createMqttConnAckMsg(MqttConnectReturnCode returnCode, MqttConnectMessage msg) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(CONNACK, false, AT_MOST_ONCE, false, 0);
        MqttConnAckVariableHeader mqttConnAckVariableHeader =
                new MqttConnAckVariableHeader(returnCode, !msg.variableHeader().isCleanSession());
        return new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
    }

    /**
     * 处理授权返回消息
     * @param respMsg 授权返回消息
     * @param ctx ctx
     * @param connectMessage 连接报文
     */
    private void processDeviceAuthResponse(DeviceAuthRespMsg respMsg, ChannelHandlerContext ctx, MqttConnectMessage connectMessage) {
        if (!respMsg.hasDeviceInfo()) {
            context.onDeviceAuthFailure(address);
            ctx.writeAndFlush(createMqttConnAckMsg(CONNECTION_REFUSED_NOT_AUTHORIZED, connectMessage));
            ctx.close();
        } else {
            context.onDeviceAuthSuccess(address);
            TransportSessionInfo transportSessionInfo = TransportSessionInfoCreator.create(respMsg.getDeviceInfo(), sessionId);
            deviceSessionCtx.setSessionInfo(transportSessionInfo);
            ctx.writeAndFlush(createMqttConnAckMsg(CONNECTION_ACCEPTED, connectMessage));
            deviceSessionCtx.setConnected();
            transportService.processDeviceConnectSuccess(transportSessionInfo, new TransportServiceCallback<>() {
                @Override
                public void onSuccess(Void msg) {
                    transportService.registerSession(deviceSessionCtx.getSessionInfo(), MqttTransportHandler.this);
                }

                @Override
                public void onError(Throwable e) {
                    log.warn("[{}] Failed to submit session event", sessionId, e);
                    ctx.writeAndFlush(createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_REFUSED_SERVER_UNAVAILABLE, connectMessage));
                    ctx.close();
                }
            });
        }
    }

    /**
     * 处理 会话 报文
     * @param channelHandlerContext  ctx
     * @param mqttMessage 数据报文
     */
    private void  processSessionMsg (ChannelHandlerContext channelHandlerContext,MqttMessage mqttMessage){
        MqttMessageType mqttMessageType = mqttMessage.fixedHeader().messageType();
        switch (mqttMessageType){
            case PUBLISH:
                processPublishMsg(channelHandlerContext,(MqttPublishMessage) mqttMessage);
                break;
            case SUBSCRIBE:
                processSubscribeMsg(channelHandlerContext,(MqttSubscribeMessage) mqttMessage);
                break;
            case UNSUBSCRIBE:
                processUnsubscribeMsg(channelHandlerContext,(MqttUnsubscribeMessage) mqttMessage);
                break;
            case PINGREQ:
                processPingMsg(channelHandlerContext);
                break;
            case DISCONNECT:
                processDisConnectMsg(channelHandlerContext);
                break;
            case PUBACK:
                processPubAckMsg(channelHandlerContext, (MqttPubAckMessage) mqttMessage);
                break;
            default:
                break;
        }
    }


    /**
     * 处理 发布消息
     * @param channelHandlerContext ctx
     * @param mqttPublishMessage 发布消息
     */
   private void  processPublishMsg (ChannelHandlerContext channelHandlerContext,MqttPublishMessage mqttPublishMessage) {

    }

    /**
     * 处理订阅消息
     * @param channelHandlerContext ctx
     * @param mqttSubscribeMessage 订阅消息
     */
    private void processSubscribeMsg(ChannelHandlerContext channelHandlerContext,MqttSubscribeMessage mqttSubscribeMessage){

    }

    /**
     * 处理取消订阅消息
     * @param channelHandlerContext ctx
     * @param mqttUnsubscribeMessage 取消订阅消息
     */
    private void processUnsubscribeMsg(ChannelHandlerContext channelHandlerContext, MqttUnsubscribeMessage mqttUnsubscribeMessage){

    }


    /**
     * 处理 ping 报文
     * @param channelHandlerContext ctx
     */
    private void processPingMsg(ChannelHandlerContext channelHandlerContext){
        if (checkConnected(channelHandlerContext)) {
            channelHandlerContext.writeAndFlush(new MqttMessage(new MqttFixedHeader(PINGRESP, false, AT_MOST_ONCE, false, 0)));
            transportService.reportActivity(deviceSessionCtx.getSessionInfo());
        }
    }

    /**
     * 处理断开报文
     * @param channelHandlerContext ctx
     */
    private void  processDisConnectMsg(ChannelHandlerContext channelHandlerContext){
        channelHandlerContext.close();
    }


    /**
     * 处理 发布 ack
     * @param channelHandlerContext ctx
     * @param mqttPubAckMessage 发布消息
     */
    private void  processPubAckMsg(ChannelHandlerContext channelHandlerContext,MqttPubAckMessage mqttPubAckMessage){}


    /**
     * 监测是否连接
     * @param channelHandlerContext ctx
     * @return bool
     */
    private boolean checkConnected(ChannelHandlerContext channelHandlerContext) {
        if (deviceSessionCtx.isConnected()) {
            return true;
        } else {
            log.info("MqttTransportHandler.checkConnected [{}] [{}] Closing current session ", address,sessionId);
            return false;
        }
    }

    private void doDisconnect() {
        if (deviceSessionCtx.isConnected()) {
            log.debug("MqttTransportHandler.doDisconnect >> [{}] Client disconnected!", sessionId);
            transportService.processDeviceDisConnect(deviceSessionCtx.getSessionInfo(), null);
            transportService.deregisterSession(deviceSessionCtx.getSessionInfo());
            deviceSessionCtx.setDisconnected();
        }
    }






}
