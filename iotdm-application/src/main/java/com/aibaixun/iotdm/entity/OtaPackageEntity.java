package com.aibaixun.iotdm.entity;

import com.aibaixun.iotdm.enums.OtaPackageType;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/24
 */
@TableName("t_ota_package")
public class OtaPackageEntity extends BaseEntity{

    private String productId;

    @NotNull(message = "ota包类型不允许为空")
    private OtaPackageType otaPackageType;

    @NotNull(message = "ota包类型名称不允许为空")
    private String otaPackageLabel;

    @NotNull(message = "ota包版本不允许为空")
    private String otaPackageVersion;

    private String fileUrl;

    private String fileName;

    private String contentType;

    private byte [] data;

    private String checkNum;

    private Long dataSize;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public OtaPackageType getOtaPackageType() {
        return otaPackageType;
    }

    public void setOtaPackageType(OtaPackageType otaPackageType) {
        this.otaPackageType = otaPackageType;
    }

    public String getOtaPackageLabel() {
        return otaPackageLabel;
    }

    public void setOtaPackageLabel(String otaPackageLabel) {
        this.otaPackageLabel = otaPackageLabel;
    }

    public String getOtaPackageVersion() {
        return otaPackageVersion;
    }

    public void setOtaPackageVersion(String otaPackageVersion) {
        this.otaPackageVersion = otaPackageVersion;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public Long getDataSize() {
        return dataSize;
    }

    public void setDataSize(Long dataSize) {
        this.dataSize = dataSize;
    }
}
