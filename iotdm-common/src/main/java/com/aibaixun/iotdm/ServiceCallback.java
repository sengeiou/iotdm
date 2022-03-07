package com.aibaixun.iotdm;

/**
 * 服务回调
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
public interface ServiceCallback<T> {


    ServiceCallback<Void> EMPTY = new ServiceCallback<Void>() {
        @Override
        public void onSuccess(Void msg) {

        }

        @Override
        public void onError(Throwable e) {

        }
    };

    /**
     * 成功
     * @param msg 消息
     */
    void onSuccess(T msg);

    /**
     * 失败
     * @param e 错误信息
     */
    void onError(Throwable e);
}
