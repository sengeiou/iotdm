package com.aibaixun.iotdm.transport;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/15
 */
public final class SessionId {

    private String deviceId;

    private String productId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public SessionId() {
    }

    public SessionId(String deviceId, String productId) {
        this.deviceId = deviceId;
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionId sessionId = (SessionId) o;

        return new EqualsBuilder().append(deviceId, sessionId.deviceId).append(productId, sessionId.productId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(deviceId).append(productId).toHashCode();
    }
}
