package com.aibaixun.iotdm.constants;

/**
 * 主题常量
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class TopicConstants {

    private TopicConstants (){}


    public static final String PROPERTIES_UP = "$oc/properties/up";
    public static final String PROPERTIES_GET = "$oc/properties/get";

    public static final String MESSAGE_UP = "$oc/messages/up";




    public static final String OTA_RESP = "$oc/ota/response";
    public static final String OTA_REQ = "$oc/ota/request";



    public static final String CONFIG_REQ = "$oc/config/request";
    public static final String CONFIG_RESP = "$oc/config/response";


    public static final String CONTROL_REQ = "$oc/control/request";
    public static final String CONTROL_RESP = "$oc/control/response";


    public static final String WARN = "$oc/warn/self";

}
