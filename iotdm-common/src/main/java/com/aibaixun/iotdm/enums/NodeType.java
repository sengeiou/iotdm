package com.aibaixun.iotdm.enums;

/**
 * 节点类型 也是设备连接平台类型
 * <p>节点的含义是网络连接节点</p>
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public enum NodeType {

    /**
     * 网关 也是直接连接设备 标识有联网能力
     */
    GATEWAY,
    /**
     * 网关子设备 需要借助其他设备的网络能力 上报数据
     */
    ENDPOINT;
}
