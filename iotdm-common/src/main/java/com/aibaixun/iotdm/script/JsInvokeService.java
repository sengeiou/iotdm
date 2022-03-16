package com.aibaixun.iotdm.script;


import javax.script.ScriptException;

/**
 * js 执行
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public interface JsInvokeService {

    /**
     * 执行js body 返回 函数名称
     * @param scriptBody 函数题
     * @param argNames 参数名称
     * @return 函数名称
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    String testEncode(String scriptBody, Object... argNames) throws ScriptException, NoSuchMethodException;


    /**
     * 执行js body 返回 函数名称
     * @param scriptBody 函数题
     * @param argNames 参数名称
     * @return 函数名称
     */
    String testdecode(String scriptBody, Object... argNames) throws ScriptException, NoSuchMethodException;

    /**
     * 执行js body 返回 函数名称
     * @param productId 产品id
     * @param scriptBody 函数题
     * @return 函数名称
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    Object eval(String productId,String scriptBody) throws ScriptException;


    /**
     *  执行encode函数
     * @param productId 产品id
     * @param args 参数
     * @return 结果
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    Object invokeEncodeFunction(String productId, Object... args) throws ScriptException, NoSuchMethodException;



    /**
     *  执行decode函数
     * @param productId 产品id
     * @param args 参数
     * @return 结果
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    Object invokeDecodeFunction(String productId, Object... args) throws ScriptException, NoSuchMethodException;


    /**
     * 释放
     * @param productId 产品id
     * @throws ScriptException
     */
    void release(String productId) throws ScriptException;
}
