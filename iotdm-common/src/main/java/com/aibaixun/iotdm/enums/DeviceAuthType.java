package com.aibaixun.iotdm.enums;

/**
 * 设备认证方式
 * <p>密钥与X.509证书</p>
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public enum DeviceAuthType {

    /**
     * 密钥认证
     */
    SECRET,

    /**
     * X509 证书
     */
    X509CERT;
}
