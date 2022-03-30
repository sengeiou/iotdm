package com.aibaixun.iotdm.rule.send;

import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.support.BaseResourceConfig;
import com.aibaixun.iotdm.support.BaseTargetConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * mysql 持久化配置类
 * @author Wang Xiao
 * @date 2022/3/30
 */
@Component
public class MySqlPersistenceServer implements SendServer {


    private final Logger log = LoggerFactory.getLogger(MySqlPersistenceServer.class);

    @Override
    public <T> void doSendMessage(T message, BaseResourceConfig resourceConfig, BaseTargetConfig targetConfig) {
        log.info("mysql nothing to do");
    }

    @Override
    public void init() {
        registerService(ResourceType.MYSQL,this);
    }
}
