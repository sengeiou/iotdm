package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.DeviceAuthType;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.enums.NodeType;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * <p>
 * 设备表
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_device")
public class DeviceEntity extends BaseEntity {

    /**
     * 产品id
     */
    private String productId;

    /**
     * 设备标识码
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    private String deviceLabel;

    /**
     * 设备状态
     */
    private DeviceStatus deviceStatus;

    /**
     * 节点类型
     */
    private NodeType nodeType;

    /**
     * 描述
     */
    private String description;

    /**
     * 网关id
     */
    private String gatewayId;

    /**
     * 认证方式
     */
    private DeviceAuthType authType;

    /**
     * 设备密钥
     */
    private String deviceSecret;

    /**
     * 最后连接时间
     */
    private Long lastConnectTs;

    /**
     * 最后活跃时间
     */
    private Long lastActivityTs;

    /**
     * 最后连接地址
     */
    private String lastRemoteAddress;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 是否是虚拟设备
     */
    private Boolean invented;



    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceLabel() {
        return deviceLabel;
    }

    public void setDeviceLabel(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public DeviceAuthType getAuthType() {
        return authType;
    }

    public void setAuthType(DeviceAuthType authType) {
        this.authType = authType;
    }

    public String getDeviceSecret() {
        return deviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret;
    }

    public Long getLastConnectTs() {
        return lastConnectTs;
    }

    public void setLastConnectTs(Long lastConnectTs) {
        this.lastConnectTs = lastConnectTs;
    }


    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setInvented(Boolean invented) {
        this.invented = invented;
    }

    public Boolean getInvented() {
        return invented;
    }


    public Long getLastActivityTs() {
        return lastActivityTs;
    }

    public void setLastActivityTs(Long lastActivityTs) {
        this.lastActivityTs = lastActivityTs;
    }

    public String getLastRemoteAddress() {
        return lastRemoteAddress;
    }

    public void setLastRemoteAddress(String lastRemoteAddress) {
        this.lastRemoteAddress = lastRemoteAddress;
    }

    @Override
    public String toString() {
        return "DeviceEntity{" +
                "productId='" + productId + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceLabel='" + deviceLabel + '\'' +
                ", deviceStatus=" + deviceStatus +
                ", nodeType=" + nodeType +
                ", description='" + description + '\'' +
                ", gatewayId='" + gatewayId + '\'' +
                ", authType=" + authType +
                ", deviceSecret='" + deviceSecret + '\'' +
                ", lastConnectTs=" + lastConnectTs +
                ", lastActivityTs=" + lastActivityTs +
                ", lastRemoteAddress='" + lastRemoteAddress + '\'' +
                ", deleted=" + deleted +
                ", invented=" + invented +
                '}';
    }
}
