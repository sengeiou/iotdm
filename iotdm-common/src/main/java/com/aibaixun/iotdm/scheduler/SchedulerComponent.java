package com.aibaixun.iotdm.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 定时 任务处理类
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public interface SchedulerComponent {


    /**
     * 延时执行任务
     * @param command 任务
     * @param delay 延时时间
     * @param unit 时间单位
     * @return ScheduledFuture
     */
    ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);


    /**
     * 延时执行任务
     * @param callable 任务
     * @param delay 延时时间
     * @param unit 时间单位
     * @return result
     */
    <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);


    /**
     * 延时 执行任务
     * @param command 任务
     * @param initialDelay 延时时间
     * @param unit 时间单位
     * @param period 间隔时间
     * @return ScheduledFuture
     */
    ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);


    /**
     * 延时定期 执行任务
     * @param command 任务
     * @param initialDelay 延时时间
     * @param unit 时间单位
     * @param delay 延时
     * @return ScheduledFuture
     */
    ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);

}
