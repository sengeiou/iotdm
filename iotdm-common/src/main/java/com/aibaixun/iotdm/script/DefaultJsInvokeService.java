package com.aibaixun.iotdm.script;

import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 默认js 函数 执行服务
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
@Service
public class DefaultJsInvokeService implements JsInvokeService{


    private ScriptEngine scriptEngine;

    @Override
    public ListenableFuture<String> testEval(String productId, String scriptBody, String... argNames) {
        return null;
    }

    @Override
    public ListenableFuture<Object> invokeEncodeFunction(String productId, Object... args) {
        return null;
    }

    @Override
    public ListenableFuture<Object> invokeDecodeFunction(String productId, Object... args) {
        return null;
    }

    @Override
    public ListenableFuture<Void> release(String productId) {
        return null;
    }


    @PostConstruct
    public void  initJsService () {
        ScriptEngineManager factory = new ScriptEngineManager();
        scriptEngine = factory.getEngineByName("nashorn");
    }
}
