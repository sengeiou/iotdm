package com.aibaixun.iotdm.config;

import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
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
