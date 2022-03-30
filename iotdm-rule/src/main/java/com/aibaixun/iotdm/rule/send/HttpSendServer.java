package com.aibaixun.iotdm.rule.send;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.aibaixun.iotdm.support.BaseTargetConfig;
import com.aibaixun.iotdm.support.HttpResourceConfig;
import com.aibaixun.iotdm.support.HttpTargetConfig;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

/**
 * http 消息发送
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Component
public class HttpSendServer implements SendServer {

    private final Logger log = LoggerFactory.getLogger(HttpSendServer.class);

    private OkHttpClient okHttpClient;

    private final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    @Override
    public <T> void doSendMessage(T message, BaseResourceConfig resourceConfig, BaseTargetConfig targetConfig) {
        try {
            HttpResourceConfig httpResourceConfig = (HttpResourceConfig) resourceConfig;
            String host = httpResourceConfig.getHost();
            String httpPrefix = "http";
            if (!host.startsWith(httpPrefix)){
                host = httpPrefix +"://" + host;
            }
            Map<String, String> headers = httpResourceConfig.getHeaders();
            HttpTargetConfig httpTargetConfig = (HttpTargetConfig) targetConfig;
            HttpMethod httpMethod = httpTargetConfig.getHttpMethod();
            String path = httpTargetConfig.getPath();
            if (!CollectionUtils.isEmpty(httpTargetConfig.getHeaders())){
                headers.putAll(httpTargetConfig.getHeaders());
            }
            doExecuteHttp(host+path,headers,httpMethod.name(),OBJECT_MAPPER.writeValueAsString(message));
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


    private void doExecuteHttp (String url,Map<String,String> headers,String method,String data) {

        Request.Builder builder= new Request.Builder();
        builder.url(url).method(method,RequestBody.create(mediaType, data));
        for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
            builder.addHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        Request request = builder.build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("HttpSendService.doExecuteHttp is fail,url is:{},message:{}",url,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                log.info("HttpSendService.doExecuteHttp is response,url is:{},response code is:{}",url,response.code());
            }
        });
    }




}
