package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.mapper.ProductMapper;
import com.aibaixun.iotdm.service.IProductModelService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.data.ProductEntityInfo;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 产品表 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements IProductService {


    private IProductModelService productModelService;


    @Override
    public Page<ProductEntity> pageQueryByLabel(Integer page, Integer pageSize, String productLabel) {
        var queryWrapper = Wrappers.<ProductEntity>lambdaQuery();
        queryWrapper.eq(ProductEntity::getTenantId, UserInfoUtil.getTenantIdOfNull());
        if (StringUtils.isNotBlank(productLabel)){
            queryWrapper.likeRight(ProductEntity::getProductLabel,productLabel);
        }
        queryWrapper.orderByDesc(ProductEntity::getCreateTime);
        return page(Page.of(page,pageSize),queryWrapper);
    }

    @Override
    public ProductEntityInfo queryProductInfoById(String id) {

        ProductEntity productEntity = getById(id);
        if (null == productEntity){
            return null;
        }
        var productModelInfos = productModelService.queryProductModelInfoByProductId(id);
        return new ProductEntityInfo(productEntity,productModelInfos);
    }

    @Autowired
    public void setProductModelService(IProductModelService productModelService) {
        this.productModelService = productModelService;
    }

    @Override
    public ProductEntity queryProductByLabel(String productLabel) {
        LambdaQueryWrapper<ProductEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductEntity::getProductLabel,productLabel);
        return getOne(queryWrapper,false);
    }


    @Override
    public Map<String, String> queryProductLabelByIds(List<String> productIds) {
        List<ProductEntity> productEntities = list(Wrappers.<ProductEntity>lambdaQuery().in(ProductEntity::getId, productIds));
        return productEntities.stream().collect(Collectors.toMap(ProductEntity::getId, ProductEntity::getProductLabel));
    }


    @Override
    public List<ProductEntity> queryProducts(Integer limit) {
        var queryWrapper = Wrappers.<ProductEntity>lambdaQuery();
        queryWrapper.eq(ProductEntity::getTenantId, UserInfoUtil.getTenantIdOfNull());
        if (Objects.isNull(limit)){
            limit = 10;
        }
        queryWrapper.last(" LIMIT "+limit);
        queryWrapper.orderByDesc(ProductEntity::getCreateTime);
        return list(queryWrapper);
    }


    @Override
    public Boolean updateProduct(String productId, String productLabel, String description) {
        LambdaUpdateWrapper<ProductEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(ProductEntity::getProductLabel,productLabel);
        if (StringUtils.isNotBlank(description)){
            updateWrapper.set(ProductEntity::getDescription,description);
        }
        updateWrapper.eq(ProductEntity::getId,productId);
        return update(updateWrapper);
    }
}
