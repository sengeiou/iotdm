package com.aibaixun.iotdm.data;

import javax.validation.constraints.NotBlank;

/**
 * js 插件 debug 参数
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class JsDebugParam {

    @NotBlank(message = "输入内容不允许为空")
    private String input;

    @NotBlank(message = "jsScript脚本不允许为空")
    private String jsScriptBody;

    @NotBlank(message = "主题不允许为空")
    private String topic;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getJsScriptBody() {
        return jsScriptBody;
    }

    public void setJsScriptBody(String jsScriptBody) {
        this.jsScriptBody = jsScriptBody;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    @Override
    public String toString() {
        return "JsDebugParam{" +
                "input='" + input + '\'' +
                ", jsScriptBody='" + jsScriptBody + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
