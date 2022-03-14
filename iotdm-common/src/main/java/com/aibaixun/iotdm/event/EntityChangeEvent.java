package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.enums.SubjectEvent;
import com.aibaixun.iotdm.enums.SubjectResource;

import java.time.Instant;

/**
 * 实体更改 目前只有 设备与 资产
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
public class EntityChangeEvent {

    public SubjectResource subjectResource;

    public SubjectEvent subjectEvent;

    public Long ts;

    private String tenantId;


    public SubjectResource getSubjectResource() {
        return subjectResource;
    }

    public void setSubjectResource(SubjectResource subjectResource) {
        this.subjectResource = subjectResource;
    }

    public SubjectEvent getSubjectEvent() {
        return subjectEvent;
    }

    public void setSubjectEvent(SubjectEvent subjectEvent) {
        this.subjectEvent = subjectEvent;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    public EntityChangeEvent() {
    }

    public EntityChangeEvent(SubjectResource subjectResource, SubjectEvent subjectEvent, String tenantId) {
        this.subjectResource = subjectResource;
        this.subjectEvent = subjectEvent;
        this.tenantId = tenantId;
        this.ts= Instant.now().toEpochMilli();
    }
}
