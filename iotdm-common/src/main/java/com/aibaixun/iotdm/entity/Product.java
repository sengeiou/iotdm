package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.enums.ProtocolType;
import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 产品表
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@TableName("t_product")
public class Product extends BaseEntity {

    /**
     * 产品名称
     */
    @NotBlank(message = "产品名称不能为空")
    private String productLabel;

    /**
     * 协议类型
     */
    @NotNull(message = "协议类型不能为空")
    private ProtocolType protocolType;

    /**
     * 数据格式
     */
    @NotNull(message = "数据格式不能为空")
    private DataFormat dataFormat;

    /**
     * 描述
     */
    @Length(max = 128,message = "产品描述不能超过128字符")
    private String description;

    /**
     * 设备类型
     */
    @NotBlank(message = "设备类型不能为空")
    private String deviceType;

    /**
     * 是否删除
     */
    private Boolean deleted;


    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", productLabel='" + productLabel + '\'' +
                ", protocolType=" + protocolType +
                ", dataFormat=" + dataFormat +
                ", description='" + description + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
