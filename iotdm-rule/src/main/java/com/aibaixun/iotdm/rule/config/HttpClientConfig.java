package com.aibaixun.iotdm.rule.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/14
 */
@Configuration
public class HttpClientConfig {

    @Value("${bx.rule.http.connect-timeout}")
    private Integer connectTimeout;
    @Value("${bx.rule.http.read-timeout}")
    private Integer readTimeout;
    @Value("${bx.rule.http.write-timeout}")
    private Integer writeTimeout;
    @Value("${bx.rule.http.max-idle-connections}")
    private Integer maxIdleConnections;
    @Value("${bx.rule.http.keep-alive-duration}")
    private Long keepAliveDuration;
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout,TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }


    @Bean
    public ConnectionPool pool() {
        return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
    }

}
