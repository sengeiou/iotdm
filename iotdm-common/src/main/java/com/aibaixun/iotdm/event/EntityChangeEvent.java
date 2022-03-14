package com.aibaixun.iotdm.event;

import com.aibaixun.iotdm.enums.SubjectEvent;
import com.aibaixun.iotdm.enums.SubjectResource;

/**
 * 实体更改 目前只有 设备与 资产
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
public class EntityChangeEvent {

    public SubjectResource subjectResource;

    public SubjectEvent subjectEvent;

    public Long ts;


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
}
