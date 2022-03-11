package com.aibaixun.iotdm.business;


import com.aibaixun.iotdm.enums.BusinessStep;
import com.aibaixun.iotdm.enums.BusinessType;

/**
 * 业务上报数据处理器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public interface BusinessReportProcessor<P extends AbstractBusinessMsg,M extends AbstractBusinessMsg> {

    /**
     * 处理属性上报数据
     * @param propertyBusinessMsg 属性数据
     */
    void doProcessProperty(P propertyBusinessMsg);


    /**
     * 处理消息上报数据
     * @param messageBusinessMsg 消息数据
     */
    void doProcessMessage (M messageBusinessMsg);

    /**
     * 记录日志
     * @param deviceId 设备id
     * @param businessType 业务类型
     * @param businessStep 业务步骤
     * @param businessDetails 业务细节
     */
    void doLog (String deviceId, BusinessType businessType, BusinessStep businessStep, String businessDetails);

}
