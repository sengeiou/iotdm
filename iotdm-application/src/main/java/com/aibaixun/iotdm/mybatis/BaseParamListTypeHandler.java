package com.aibaixun.iotdm.mybatis;

import com.aibaixun.iotdm.data.BaseParam;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/23
 */
public class BaseParamListTypeHandler extends ListTypeHandler<BaseParam> {
    @Override
    protected Class<BaseParam> specificType() {
        return BaseParam.class;
    }
}
