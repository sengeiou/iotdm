package com.aibaixun.iotdm.script;


/**
 * js 执行
 * @author wangxiao@aibaixun.com
 * @date 2022/3/10
 */
public interface JsInvokeService {

    /**
     * 执行js body 返回 函数名称
     * @param productId 产品id
     * @param scriptBody 函数题
     * @param argNames 参数名称
     * @return 函数名称
     */
    String testEval(String productId, String scriptBody, String... argNames);

    /**
     * 执行js body 返回 函数名称
     * @param productId 产品id
     * @param scriptBody 函数题
     * @return 函数名称
     */
    String eval(String productId,String scriptBody);


    /**
     *  执行encode函数
     * @param productId 产品id
     * @param args 参数
     * @return 结果
     */
    Object invokeEncodeFunction(String productId, Object... args);



    /**
     *  执行decode函数
     * @param productId 产品id
     * @param args 参数
     * @return 结果
     */
    Object invokeDecodeFunction(String productId, Object... args);


    /**
     * 释放
     * @param productId 产品id
     */
    void release(String productId);
}
