package com.aibaixun.iotdm.data;

import java.util.HashMap;
import java.util.List;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/15
 */
public class SendDeviceCommandParam {

    private String commandId;

    public HashMap<String,Object> params;


    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }
}
