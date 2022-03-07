package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.ProductModelEntity;
import com.aibaixun.iotdm.mapper.ProductModelMapper;
import com.aibaixun.iotdm.service.IProductModelService;
import com.aibaixun.iotdm.data.ProductModelEntityInfo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 产品模型 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class ProductModelServiceImpl extends ServiceImpl<ProductModelMapper, ProductModelEntity> implements IProductModelService {

    @Override
    public List<ProductModelEntityInfo> queryProductModelInfoByProductId(String productId) {
        return baseMapper.selectProductModelInfoByProductId(productId);
    }

    @Override
    public Boolean updateProductModel(ProductModelEntity productModelEntity) {
        if (Objects.isNull(productModelEntity)){
            return false;
        }
        LambdaUpdateWrapper<ProductModelEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(ProductModelEntity::getId, productModelEntity.getId());
        if (StringUtils.isNotBlank(productModelEntity.getModelLabel())){
            updateWrapper.set(ProductModelEntity::getModelLabel, productModelEntity.getModelLabel());
        }else if (StringUtils.isNotBlank(productModelEntity.getProductId())){
            updateWrapper.set(ProductModelEntity::getProductId, productModelEntity.getProductId());
        }else if (StringUtils.isNotBlank(productModelEntity.getDescription())){
            updateWrapper.set(ProductModelEntity::getDescription, productModelEntity.getDescription());
        }else if(StringUtils.isNotBlank(productModelEntity.getModelType())){
            updateWrapper.set(ProductModelEntity::getModelType, productModelEntity.getModelType());
        }else {
            return false;
        }
        return update(updateWrapper);
    }
}
