package com.aibaixun.iotdm.rule.send;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.rule.QueueReceiveServiceImpl;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.aibaixun.iotdm.support.BaseTargetConfig;
import com.aibaixun.iotdm.support.HttpResourceConfig;
import com.aibaixun.iotdm.support.HttpTargetConfig;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

/**
 * http 消息发送
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Component
public class HttpSendService implements SendService{

    private final Logger log = LoggerFactory.getLogger(HttpSendService.class);

    private OkHttpClient okHttpClient;

    @Override
    public <T> void doSendMessage(T message, BaseResourceConfig resourceConfig, BaseTargetConfig targetConfig) {
        try {
            HttpResourceConfig httpResourceConfig = (HttpResourceConfig) resourceConfig;
            String host = httpResourceConfig.getHost();
            Map<String, String> headers = httpResourceConfig.getHeaders();
            HttpTargetConfig httpTargetConfig = (HttpTargetConfig) targetConfig;
            HttpMethod httpMethod = httpTargetConfig.getHttpMethod();
            String path = httpTargetConfig.getPath();
            if (!CollectionUtils.isEmpty(httpTargetConfig.getHeaders())){
                headers.putAll(httpTargetConfig.getHeaders());
            }


        }catch (Exception e){
            log.info("HttpSendService.doSendMessage >> is error ,error msg is :{}",e.getMessage() );
        }
    }

    @Override
    @PostConstruct
    public void init() {
        registerService(ResourceType.HTTP,this);
    }


    @Autowired
    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }





}
