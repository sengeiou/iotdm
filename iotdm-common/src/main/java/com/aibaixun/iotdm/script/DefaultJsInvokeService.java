package com.aibaixun.iotdm.script;

import com.aibaixun.common.util.JsonUtil;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 默认js 函数 执行服务
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
@Service
public class DefaultJsInvokeService implements JsInvokeService{



    private ScriptEngine scriptEngine;

    private final String encodeFunName = "encode_";
    private final String decodeFunName = "decode_";

    @Override
    public String testEncode(String scriptBody, Object... args) throws  ScriptException, NoSuchMethodException {

        String uuid = RandomStringUtils.randomAlphanumeric(10);
        eval(uuid,scriptBody);
        Object o =  invokeEncodeFunction(uuid,args);
        release(uuid);
        return JsonUtil.toJSONString(o);
    }

    @Override
    public Object eval(String productId, String scriptBody) throws ScriptException {
        String jsScriptBody = scriptBody.replaceAll("encode",   encodeFunName +productId)
                .replaceAll("decode",   decodeFunName +productId);
        return scriptEngine.eval(jsScriptBody);
    }

    @Override
    public Object invokeEncodeFunction(String productId, Object... args) throws ScriptException, NoSuchMethodException {
        Invocable invocable = (Invocable) scriptEngine;
        return invocable.invokeFunction (encodeFunName+productId,args);

    }

    @Override
    public Object invokeDecodeFunction(String productId, Object... args) throws ScriptException, NoSuchMethodException {
        Invocable invocable = (Invocable) scriptEngine;
        return invocable.invokeFunction(  decodeFunName+productId,args);
    }

    @Override
    public void release(String productId) throws ScriptException {
        scriptEngine.eval(  decodeFunName+  productId +" = undefined;");
        scriptEngine.eval( encodeFunName + productId+" = undefined;");

    }

    @PostConstruct
    public void  initJsService () {
        ScriptEngineManager factory = new ScriptEngineManager();
        scriptEngine = factory.getEngineByName("javascript");
    }

    @Override
    public String testDecode(String scriptBody, Object... argNames) throws ScriptException, NoSuchMethodException {
        String uuid = RandomStringUtils.randomAlphanumeric(10);
        eval(uuid,scriptBody);
        Object o =  invokeDecodeFunction(uuid,argNames);
        release(uuid);
        return JsonUtil.toJSONString(o);
    }
}
