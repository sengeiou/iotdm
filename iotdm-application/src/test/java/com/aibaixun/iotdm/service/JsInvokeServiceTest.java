package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.IotDmApplication;
import com.aibaixun.iotdm.script.DefaultJsInvokeService;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.*;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IotDmApplication.class)
public class JsInvokeServiceTest {


    ScriptEngine scriptEngine;

    @Autowired
    private DefaultJsInvokeService defaultJsInvokeService;

    String a= "function decode(payload, topic) {\n" +
            "    var jsonObj = {};\n" +
            "    return JSON.stringify(jsonObj);\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * 物联网平台下发指令时，调用此接口进行编码, 将产品模型定义的JSON格式数据编码为设备的原始码流。\n" +
            " * 该接口名称和入参格式已经定义好，开发者只需要实现具体接口即可。\n" +
            " * @param string json      符合产品模型定义的JSON格式字符串\n" +
            " * @return byte[] payload  编码后的原始码流\n" +
            " */\n" +
            "function encode(json) {\n" +
            "    var payload = [];\n" +
            "    return payload;\n" +
            "}";

    @Test
    public void  testJs() {

        try {
//            Invocable invocable = ((Invocable) scriptEngine);
//            Object a = invocable.invokeFunction("decode_89765","{\"a\":10}","");
//
//            System.out.println(a);

            String s = defaultJsInvokeService.testEncode(a, "", "");
            System.out.println(s);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Before
    public void init () throws ScriptException {
        ScriptEngineManager factory = new ScriptEngineManager();
         scriptEngine = factory.getEngineByName("javascript");
        scriptEngine.eval(a);
    }
}
