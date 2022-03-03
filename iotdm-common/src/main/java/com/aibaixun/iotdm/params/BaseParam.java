package com.aibaixun.iotdm.params;

import com.aibaixun.iotdm.enums.DataType;
import com.aibaixun.iotdm.enums.ParamScope;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class BaseParam {

    private String paramLabel;

    private DataType dataType;

    private Boolean required;

    private String unit;

    private String minValue;

    private String maxValue;

    private ParamScope scope;
}
