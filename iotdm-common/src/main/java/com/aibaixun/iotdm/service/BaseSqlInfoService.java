package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.scheduler.SqlExecutorService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/9
 */
public abstract class BaseSqlInfoService {

    protected SqlExecutorService sqlExecutorService;


    @Autowired
    public void setSqlExecutorService(SqlExecutorService sqlExecutorService) {
        this.sqlExecutorService = sqlExecutorService;
    }
}
