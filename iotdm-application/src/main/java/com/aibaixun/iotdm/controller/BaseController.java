package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;

import static com.aibaixun.iotdm.Constants.QUERY_PAGE_MAX;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public abstract class BaseController {

    public void checkPage(Integer page,Integer pageSize) throws BaseException {
        if (page<=0){
            throw new BaseException("当前页不能小于等于0", BaseResultCode.BAD_PARAMS);
        }

        if (pageSize<0 || pageSize>QUERY_PAGE_MAX){
            throw new BaseException("请选", BaseResultCode.BAD_PARAMS);
        }
    }
}
