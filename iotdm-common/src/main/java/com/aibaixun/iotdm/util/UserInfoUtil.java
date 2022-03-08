package com.aibaixun.iotdm.util;

import com.aibaixun.basic.context.UserContextHolder;
import com.aibaixun.basic.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.aibaixun.iotdm.constants.DataConstants.NULL_STR;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class UserInfoUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoUtil.class);

    private UserInfoUtil(){}


    public static String getTenantIdOfNull() {
        String tenantId = NULL_STR;
        try {
            tenantId = UserContextHolder.getTenantId();
        }catch (BaseException e){
            logger.info("UserInfoUtil-getTenantIdOfNull is error,msg: 获取用户信息错误");
        }
        return tenantId;
    }

    public static String getUserIdOfNull(){
        String userId = NULL_STR;
        try {
            userId = UserContextHolder.getUserId();
        }catch (BaseException e){
            logger.info("UserInfoUtil-getUserIdOfNull is error,msg: 获取用户信息错误");
        }
        return userId;
    }

}
