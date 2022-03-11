package com.aibaixun.iotdm.script;

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

    @PostConstruct
    public void  initJsService () {
        ScriptEngineManager factory = new ScriptEngineManager();
        scriptEngine = factory.getEngineByName("nashorn");
    }
}
