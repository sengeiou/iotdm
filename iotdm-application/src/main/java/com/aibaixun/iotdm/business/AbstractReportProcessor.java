package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.BusinessType;
import com.aibaixun.iotdm.server.DeviceLogProcessor;
import com.aibaixun.iotdm.service.IDeviceMessageReportService;
import com.aibaixun.iotdm.service.IDevicePropertyReportService;
import com.aibaixun.iotdm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * 抽象业务处理类
 * 主要提供抽象方法 做设备 调试判断 记录消息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public abstract class AbstractReportProcessor<P extends AbstractBusinessMsg,M extends AbstractBusinessMsg> implements BusinessReportProcessor<P,M> {

    protected IProductService productService;

    private DeviceLogProcessor deviceLogProcessor;

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
        deviceLogProcessor.doDevice2PlatformLog(deviceId,businessStep,businessDetails,messageStatus);
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
    public void setDeviceLogProcessor(DeviceLogProcessor deviceLogProcessor) {
        this.deviceLogProcessor = deviceLogProcessor;
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
