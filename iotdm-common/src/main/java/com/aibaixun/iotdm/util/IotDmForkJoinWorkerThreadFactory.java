package com.aibaixun.iotdm.util;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicLong;

/**
 * iotdm fork join 工厂 类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class IotDmForkJoinWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory{

    private final String namePrefix;
    private final AtomicLong threadNumber = new AtomicLong(1);

    public IotDmForkJoinWorkerThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public final ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        ForkJoinWorkerThread thread = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        thread.setContextClassLoader(this.getClass().getClassLoader());
        thread.setName(namePrefix +"-"+thread.getPoolIndex()+"-"+threadNumber.getAndIncrement());
        return thread;
    }
}
