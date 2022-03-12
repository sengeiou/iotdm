package com.aibaixun.iotdm.scheduler;

import org.springframework.stereotype.Component;

/**
 * 规则线程池 执行数据转发
 * @author wangxiao@aibaixun.com
 * @date 2022/3/12
 */
@Component
public class RuleExecutorService extends AbstractListeningExecutor{

    @Override
    protected int getThreadPollSize() {
        return 10;
    }
}
