package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.ProductPluginJs;
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
public class ProductPluginJsServiceImpl extends ServiceImpl<ProductPluginJsMapper, ProductPluginJs> implements IProductPluginJsService {

    @Override
    public ProductPluginJs queryByProductPluginJs(String productId) {
        return getOne(Wrappers.<ProductPluginJs>lambdaQuery().eq(ProductPluginJs::getProductId,productId),false);
    }


    @Override
    public Boolean uninstallJsPlugin(String productId) {
        LambdaUpdateWrapper<ProductPluginJs> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(ProductPluginJs::getDeployment, Deployment.DEBUG);
        updateWrapper.eq(ProductPluginJs::getProductId,productId);
        return update(updateWrapper);
    }
}
