package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.ProductModel;
import com.aibaixun.iotdm.mapper.ProductModelMapper;
import com.aibaixun.iotdm.service.IProductModelService;
import com.aibaixun.iotdm.support.ProductModelInfo;
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
public class ProductModelServiceImpl extends ServiceImpl<ProductModelMapper, ProductModel> implements IProductModelService {

    @Override
    public List<ProductModelInfo> queryProductModelInfoByProductId(String productId) {
        return baseMapper.selectProductModelInfoByProductId(productId);
    }

    @Override
    public Boolean updateProductModel(ProductModel productModel) {
        if (Objects.isNull(productModel)){
            return false;
        }
        LambdaUpdateWrapper<ProductModel> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(ProductModel::getId,productModel.getId());
        if (StringUtils.isNotBlank(productModel.getModelLabel())){
            updateWrapper.set(ProductModel::getModelLabel,productModel.getModelLabel());
        }else if (StringUtils.isNotBlank(productModel.getProductId())){
            updateWrapper.set(ProductModel::getProductId,productModel.getProductId());
        }else if (StringUtils.isNotBlank(productModel.getDescription())){
            updateWrapper.set(ProductModel::getDescription,productModel.getDescription());
        }else if(StringUtils.isNotBlank(productModel.getModelType())){
            updateWrapper.set(ProductModel::getModelType,productModel.getModelType());
        }else {
            return false;
        }
        return update(updateWrapper);
    }
}
