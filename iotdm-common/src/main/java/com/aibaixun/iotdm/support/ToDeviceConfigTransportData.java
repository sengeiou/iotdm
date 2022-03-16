package com.aibaixun.iotdm.support;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/16
 */
public class ToDeviceConfigTransportData extends ToDeviceBaseData{

    private String host;

    private Integer port;

    public ToDeviceConfigTransportData(ToDeviceType toDeviceType, String host, Integer port) {
        super(toDeviceType);
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
