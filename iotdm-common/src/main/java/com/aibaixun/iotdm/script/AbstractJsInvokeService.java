package com.aibaixun.iotdm.script;


/**
 * js 抽象执行期
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public abstract class AbstractJsInvokeService implements JsInvokeService{


    @Override
    public String testEval(String productId, String scriptBody, String... argNames) {
        return null;
    }

    @Override
    public String eval(String productId, String scriptBody) {
        return null;
    }

    @Override
    public Object invokeEncodeFunction(String productId, Object... args) {
        return null;
    }

    @Override
    public Object invokeDecodeFunction(String productId, Object... args) {
        return null;
    }

    @Override
    public void release(String productId) {

    }
}
