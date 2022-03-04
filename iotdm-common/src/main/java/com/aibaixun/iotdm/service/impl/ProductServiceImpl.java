package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.Product;
import com.aibaixun.iotdm.mapper.ProductMapper;
import com.aibaixun.iotdm.service.IProductModelService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.support.ProductInfo;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {


    private IProductModelService productModelService;


    @Override
    public Page<Product> pageQueryByLabel(Integer page, Integer pageSize, String productLabel) {
        var queryWrapper = Wrappers.<Product>lambdaQuery();
        queryWrapper.eq(Product::getTenantId, UserInfoUtil.getTenantIdOfNull());
        if (StringUtils.isNotBlank(productLabel)){
            queryWrapper.likeLeft(Product::getProductLabel,productLabel);
        }
        queryWrapper.orderByDesc(Product::getCreateTime);
        return page(Page.of(page,pageSize),queryWrapper);
    }

    @Override
    public ProductInfo queryProductInfoById(String id) {

        Product product = getById(id);
        if (null == product){
            return null;
        }
        var productModelInfos = productModelService.queryProductModelInfoByProductId(id);
        return new ProductInfo(product,productModelInfos);
    }

    @Autowired
    public void setProductModelService(IProductModelService productModelService) {
        this.productModelService = productModelService;
    }

    @Override
    public Product queryProductByLabel(String productLabel) {
        LambdaQueryWrapper<Product> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Product::getProductLabel,productLabel);
        return getOne(queryWrapper,false);
    }


    @Override
    public Map<String, String> queryProductLabelByIds(List<String> productIds) {
        List<Product> products = list(Wrappers.<Product>lambdaQuery().in(Product::getId, productIds));
        return products.stream().collect(Collectors.toMap(Product::getId, Product::getProductLabel));
    }


    @Override
    public List<Product> queryProducts(Integer limit) {
        var queryWrapper = Wrappers.<Product>lambdaQuery();
        queryWrapper.eq(Product::getTenantId, UserInfoUtil.getTenantIdOfNull());
        queryWrapper.orderByDesc(Product::getCreateTime);
        return list(queryWrapper);
    }
}
