package com.aibaixun.iotdm.service;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;

import static com.aibaixun.iotdm.constants.DataConstants.QUERY_PAGE_MAX;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
public class BaseTest {

    public void checkPage(Integer page,Integer pageSize) throws BaseException {
        if (page<=0){
            throw new BaseException("当前页不能小于等于0", BaseResultCode.BAD_PARAMS);
        }

        if (pageSize<0 || pageSize>QUERY_PAGE_MAX){
            throw new BaseException("请选", BaseResultCode.BAD_PARAMS);
        }
    }
}
