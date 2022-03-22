package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.ProductPluginJsEntity;
import com.aibaixun.iotdm.enums.Deployment;
import com.aibaixun.iotdm.mapper.ProductPluginJsMapper;
import com.aibaixun.iotdm.service.IProductPluginJsService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品插件-js插件 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class ProductPluginJsServiceImpl extends ServiceImpl<ProductPluginJsMapper, ProductPluginJsEntity> implements IProductPluginJsService {

    @Override
    public ProductPluginJsEntity queryByProductPluginJs(String productId) {
        return getOne(Wrappers.<ProductPluginJsEntity>lambdaQuery().eq(ProductPluginJsEntity::getProductId,productId),false);
    }


    @Override
    public Boolean uninstallJsPlugin(String productId) {
        LambdaUpdateWrapper<ProductPluginJsEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(ProductPluginJsEntity::getDeployment, Deployment.DEBUG);
        updateWrapper.eq(ProductPluginJsEntity::getProductId,productId);
        return update(updateWrapper);
    }

    @Override
    public Boolean removeJsPlugin(String productId) {
        return remove(Wrappers.<ProductPluginJsEntity>lambdaQuery().eq(ProductPluginJsEntity::getProductId,productId));
    }
}
