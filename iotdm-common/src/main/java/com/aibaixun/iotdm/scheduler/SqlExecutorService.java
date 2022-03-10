package com.aibaixun.iotdm.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 执行sql executor
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
@Component
public class SqlExecutorService extends AbstractListeningExecutor{

    @Value("${spring.datasource.hikari.maximumPoolSize}")
    private int poolSize;

    @Override
    protected int getThreadPollSize() {
        return poolSize/2;
    }
}
