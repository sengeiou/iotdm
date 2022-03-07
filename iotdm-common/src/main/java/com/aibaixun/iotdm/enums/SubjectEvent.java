package com.aibaixun.iotdm.enums;

/**
 * 数据触发事件
 * <p>设备类 ：设备的crd</p>
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
public enum SubjectEvent {

    /**
     * 设备创建
     */
    DEVICE_CREATE,

    /**
     * 设备更新
     */
    DEVICE_UPDATE,

    /**
     * 设备删除
     */
    DEVICE_DELETE,

    /**
     * 设备属性上报
     */
    DEVICE_PROPERTY_REPORT,

    /**
     * 设备消息上报
     */
    DEVICE_MESSAGE_REPORT,

    /**
     * 设备状态更改
     */
    DEVICE_STATUS_UPDATE,

    /**
     * 产品创建
     */
    PRODUCT_CREATE,

    /**
     * 产品更新
     */
    PRODUCT_UPDATE,

    /**
     * 产品删除
     */
    PRODUCT_DELETE,
}
