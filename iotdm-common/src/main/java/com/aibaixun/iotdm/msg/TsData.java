package com.aibaixun.iotdm.msg;

import java.io.Serializable;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public class TsData implements Serializable {

    private Long ts;

    private String tsId;

    private String tsLabel;

    private String tsValue;


    public TsData(Long ts, String tsId, String tsLabel, String tsValue) {
        this.ts = ts;
        this.tsId = tsId;
        this.tsLabel = tsLabel;
        this.tsValue = tsValue;
    }

    public TsData() {
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getTsId() {
        return tsId;
    }

    public void setTsId(String tsId) {
        this.tsId = tsId;
    }

    public String getTsLabel() {
        return tsLabel;
    }

    public void setTsLabel(String tsLabel) {
        this.tsLabel = tsLabel;
    }

    public String getTsValue() {
        return tsValue;
    }

    public void setTsValue(String tsValue) {
        this.tsValue = tsValue;
    }
}
