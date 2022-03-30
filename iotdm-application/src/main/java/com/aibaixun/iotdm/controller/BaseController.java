package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.iotdm.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

import static com.aibaixun.iotdm.constants.DataConstants.QUERY_PAGE_MAX;

/**
 * 基础controller
 * <p>参数校验与异常处理</p>
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public abstract class BaseController {

     void checkPage(Integer page,Integer pageSize) throws BaseException {
        if (page<=0){
            throw new BaseException("当前页不能小于等于0", BaseResultCode.BAD_PARAMS);
        }

        if (pageSize<0 || pageSize>QUERY_PAGE_MAX){
            throw new BaseException("请选", BaseResultCode.BAD_PARAMS);
        }
    }

    void checkParameter(String name, String param) throws BaseException {
        if (StringUtils.isEmpty(param)) {
            throw new BaseException("参数 " + name + " 不允许为空", BaseResultCode.BAD_PARAMS);
        }
    }


    void checkParameterValue(String value, String message) throws BaseException {
        if (StringUtils.isEmpty(value)) {
            throw new BaseException(message, BaseResultCode.BAD_PARAMS);
        }
    }

    <E extends BaseEntity> void checkEntity(E entity,String message) throws BaseException {
        if (Objects.isNull(entity)){
            throw new BaseException(message, BaseResultCode.GENERAL_ERROR);
        }
    }

    <T> void checkEntityParam(T entity,String message) throws BaseException {
        if (Objects.isNull(entity)){
            throw new BaseException(message, BaseResultCode.GENERAL_ERROR);
        }
    }

    <E extends BaseEntity> void checkEntities(Collection<E> entities, String message) throws BaseException {
        if (CollectionUtils.isEmpty(entities)){
            throw new BaseException(message, BaseResultCode.GENERAL_ERROR);
        }
    }
}
