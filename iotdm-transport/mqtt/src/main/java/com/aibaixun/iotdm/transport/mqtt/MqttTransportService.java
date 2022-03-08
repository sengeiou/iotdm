package com.aibaixun.iotdm.transport.mqtt;

import com.aibaixun.iotdm.constants.DataConstants;
import com.aibaixun.iotdm.transport.BxTransportService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.ResourceLeakDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * Mqtt 服务启动类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
@Service("mqttTransportService")
@ConditionalOnExpression("'${transport.mqtt.enabled}'=='true'")
public class MqttTransportService implements BxTransportService {

    /**
     * 远程 地址存储
     */
    public static AttributeKey<InetSocketAddress> ADDRESS = AttributeKey.newInstance("SRC_ADDRESS");

    private final Logger logger  = LoggerFactory.getLogger("mqtt-transport");


    @Value("${transport.mqtt.bind_address}")
    private String host;

    @Value("${transport.mqtt.bind_port}")
    private Integer port;

    @Value("${transport.mqtt.ssl.enabled}")
    private boolean sslEnabled;

    @Value("${transport.mqtt.ssl.bind_address}")
    private String sslHost;
    @Value("${transport.mqtt.ssl.bind_port}")
    private Integer sslPort;

    @Value("${transport.mqtt.netty.leak_detector_level}")
    private String leakDetectorLevel;
    @Value("${transport.mqtt.netty.boss_group_thread_count}")
    private Integer bossGroupThreadCount;
    @Value("${transport.mqtt.netty.worker_group_thread_count}")
    private Integer workerGroupThreadCount;
    @Value("${transport.mqtt.netty.so_keep_alive}")
    private boolean keepAlive;


    private MqttTransportContext context;

    private Channel serverChannel;
    private Channel sslServerChannel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @PostConstruct
    public void init() throws Exception {
        logger.info("MqttTransportService.init >> Setting resource leak detector level to {}", leakDetectorLevel);
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(leakDetectorLevel.toUpperCase()));

        logger.info("MqttTransportService.init >> Starting MQTT transport...");
        bossGroup = new NioEventLoopGroup(bossGroupThreadCount);
        workerGroup = new NioEventLoopGroup(workerGroupThreadCount);
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new MqttTransportServerInitializer(context, false))
                .childOption(ChannelOption.SO_KEEPALIVE, keepAlive);

        serverChannel = b.bind(host, port).sync().channel();
        if (sslEnabled) {
            b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MqttTransportServerInitializer(context, true))
                    .childOption(ChannelOption.SO_KEEPALIVE, keepAlive);
            sslServerChannel = b.bind(sslHost, sslPort).sync().channel();
        }
        logger.info("MqttTransportService.init >> Mqtt transport started!");
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        logger.info("MqttTransportService.shutdown >> Stopping MQTT transport!");
        try {
            serverChannel.close().sync();
            if (sslEnabled) {
                sslServerChannel.close().sync();
            }
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        logger.info("MqttTransportService.shutdown >> MQTT transport stopped!");
    }


    @Override
    public String getServiceName() {
        return DataConstants.MQTT_TRANSPORT_NAME;
    }

    @Autowired
    public void setContext(MqttTransportContext context) {
        this.context = context;
    }
}
