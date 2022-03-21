package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.msg.TsData;

import java.util.List;

/**
 * 转换之后数据
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
public class PostPropertyBusinessMsg extends AbstractBusinessMsg{

    List<TsData> tsData;


    public PostPropertyBusinessMsg() {
    }

    public PostPropertyBusinessMsg(MetaData metaData, List<TsData> tsData) {
        super(metaData);
        this.tsData = tsData;
    }
}
