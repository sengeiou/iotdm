package com.aibaixun.iotdm.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public class TestJs {
    static ScriptEngineManager factory = new ScriptEngineManager();
    static  ScriptEngine engine = factory.getEngineByName("nashorn");

    static {


        // 用String定义了一段JavaScript代码这段代码定义了一个对象'obj'
        // 给对象增加了一个名为 hello 的方法（hello这个方法是属于对象的）
        String script = " function decode(payload, topic) {\n" +
                "    var jsonObj = {};\n" +
                "    return JSON.stringify(jsonObj);\n" +
                "}"+" function encode(payload, topic) {\n" +
                "    var jsonObj = {};\n" +
                "    return JSON.stringify(jsonObj);\n" +
                "}";
        String script1 = "var disddwsd1 = new Object(); disddwsd1.encode = function encode(name) { print('Hello1, ' + name); }";
        //执行这段script脚本
        try {
            engine.eval(script);
            engine.eval(script1);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ScriptException, NoSuchMethodException {

        // 获取我们想调用那个方法所属的js对象
        Object obj = engine.get("disddwsd");
        Object obj1 = engine.get("disddwsd1");
        Invocable inv = (Invocable) engine;
        // 执行obj对象的名为hello的方法
        Object encode = inv.invokeMethod(obj, "encode", "{}");
        Object encode1 = inv.invokeMethod(obj1, "encode", "Script 1111111Method !!");
        System.out.println(encode);
        System.out.println(encode1);

    }
}
