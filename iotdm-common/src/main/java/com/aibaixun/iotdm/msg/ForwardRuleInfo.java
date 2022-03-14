package com.aibaixun.iotdm.msg;

import com.aibaixun.iotdm.enums.SubjectEvent;
import com.aibaixun.iotdm.enums.SubjectResource;


import java.io.Serializable;
import java.util.List;

/**
 * 转发规则信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/12
 */
public class ForwardRuleInfo implements Serializable {


    private String ruleId;


    /**
     * 数据来源
     */
    private SubjectResource subjectResource;

    /**
     * 触发事件
     */
    private SubjectEvent subjectEvent;


    public List<TargetResourceInfo> targetResourceInfos;



    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

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

    public List<TargetResourceInfo> getTargetResourceInfos() {
        return targetResourceInfos;
    }

    public void setTargetResourceInfos(List<TargetResourceInfo> targetResourceInfos) {
        this.targetResourceInfos = targetResourceInfos;
    }
}
