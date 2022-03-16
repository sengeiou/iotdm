package com.aibaixun.iotdm.business;

import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.constants.DataConstants;
import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.BusinessType;
import com.aibaixun.iotdm.service.*;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Objects;

/**
 * 抽象业务处理类
 * 主要提供抽象方法 做设备 调试判断 记录消息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public abstract class AbstractReportProcessor<P extends AbstractBusinessMsg,M extends AbstractBusinessMsg> implements BusinessReportProcessor<P,M> {

    protected IProductService productService;


    private RedisRepository redisRepository;


    private IMessageTraceService messageTraceService;


    protected IDevicePropertyReportService propertyReportService;


    protected IDeviceMessageReportService messageReportService;

    @Override
    public void doProcessProperty(P propertyBusinessMsg) {
        processProperty(propertyBusinessMsg);
    }

    @Override
    public void doProcessMessage(M messageBusinessMsg) {
        processMessage(messageBusinessMsg);
    }


    @Override
    public void doLog(String deviceId, BusinessType businessType, BusinessStep businessStep, String businessDetails,Boolean messageStatus) {
        Long ttl = ((Long) redisRepository.getHashValues(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY , deviceId));
        if (Objects.nonNull(ttl) && ttl < Instant.now().toEpochMilli()){
            Futures.submit(()->messageTraceService.logDeviceMessageTrace(deviceId,businessType,businessStep,businessDetails,messageStatus), MoreExecutors.directExecutor());
        }else {
            redisRepository.delHashValues(DataConstants.IOT_DEVICE_DEBUG_CACHE_KEY , deviceId);
        }
    }

    /**
     * 属性上报
     * @param propertyBusinessMsg 属性数据
     */
    public abstract void processProperty(P propertyBusinessMsg);

    /**
     *消息上报
     * @param messageBusinessMsg 消息数据
     */
    public abstract void processMessage(M messageBusinessMsg);



    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }


    @Autowired
    public void setMessageTraceService(IMessageTraceService messageTraceService) {
        this.messageTraceService = messageTraceService;
    }

    @Autowired
    public void setPropertyReportService(IDevicePropertyReportService propertyReportService) {
        this.propertyReportService = propertyReportService;
    }

    @Autowired
    public void setMessageReportService(IDeviceMessageReportService messageReportService) {
        this.messageReportService = messageReportService;
    }
}
