package com.aibaixun.iotdm.data;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;

/**
 * 发送到设备的命令参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/15
 */
public class ToDeviceCommandParam {

    @NotBlank(message = "设备id不允许为空")
    private String deviceId;

    @NotBlank(message = "命令id不允许为空")
    private String commandId;

    public HashMap<String,Object> params;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

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
