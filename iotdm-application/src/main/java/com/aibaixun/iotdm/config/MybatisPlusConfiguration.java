package com.aibaixun.iotdm.config;

import com.aibaixun.basic.context.UserContextHolder;
import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

/**
 * mybatis-plus 配置
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@Configuration
@MapperScan("com.aibaixun.iotdm.mapper")
public class MybatisPlusConfiguration {

    private final Logger logger = LoggerFactory.getLogger(MybatisPlusConfiguration.class);

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> {
            GlobalConfig globalConfig = properties.getGlobalConfig();
            globalConfig.setBanner(false);
            MybatisConfiguration configuration = new MybatisConfiguration();
            configuration.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
            properties.setConfiguration(configuration);
        };
    }


    @Bean
    public MetaObjectHandler iotdmMetaObjectHandler (){
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", Long.class, Instant.now().toEpochMilli());
                this.strictInsertFill(metaObject, "tenantId",String.class, UserInfoUtil.getTenantIdOfNull());
                this.strictInsertFill(metaObject, "creator",String.class,UserInfoUtil.getUserIdOfNull() );
            }
            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateTime", Long.class, Instant.now().toEpochMilli());
            }
        };
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

}
