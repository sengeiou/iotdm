package com.aibaixun.iotdm.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

/**
 * 执行 工具类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class IotDmExecutors {


    /**
     * 创建 线程 工厂
     * @param parallelism 目标并发级别
     * @param namePrefix 用于定义线程名称
     * @return 线程工厂
     */
    public static ExecutorService newWorkStealingPool(int parallelism, String namePrefix) {
        return new ForkJoinPool(parallelism,
                new IotDmForkJoinWorkerThreadFactory(namePrefix),
                null, true);
    }

    public static ExecutorService newWorkStealingPool(int parallelism, Class clazz) {
        return newWorkStealingPool(parallelism, clazz.getSimpleName());
    }
}
