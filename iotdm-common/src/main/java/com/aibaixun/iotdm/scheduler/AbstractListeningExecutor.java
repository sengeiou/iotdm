package com.aibaixun.iotdm.scheduler;


import com.aibaixun.iotdm.util.IotDmExecutors;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Callable;

/**
 * 监听者 执行器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
public abstract class AbstractListeningExecutor implements ListeningExecutor {

    private ListeningExecutorService service;

    @PostConstruct
    public void init() {
        this.service = MoreExecutors.listeningDecorator(IotDmExecutors.newWorkStealingPool(getThreadPollSize(), getClass()));
    }

    @PreDestroy
    public void destroy() {
        if (this.service != null) {
            this.service.shutdown();
        }
    }

    @Override
    public <T> ListenableFuture<T> executeAsync(Callable<T> task) {
        return service.submit(task);
    }

    @Override
    public void execute( Runnable command) {
        service.execute(command);
    }

    public ListeningExecutorService executor() {
        return service;
    }

    /**
     * 获取线程池 大小
     * @return 线程池大小
     */
    protected abstract int getThreadPollSize();

}
