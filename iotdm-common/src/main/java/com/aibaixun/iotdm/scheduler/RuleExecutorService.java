package com.aibaixun.iotdm.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 规则线程池 执行数据转发
 * @author wangxiao@aibaixun.com
 * @date 2022/3/12
 */
@Component
public class RuleExecutorService extends AbstractListeningExecutor{

    @Value("${bx.rule.poll_size:5}")
    private int poolSize;

    @Override
    protected int getThreadPollSize() {
        return poolSize;
    }
}
