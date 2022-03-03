package com.aibaixun.iotdm.support;

import com.aibaixun.iotdm.entity.Product;

import java.util.List;

/**
 * 产品详细信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class ProductInfo extends Product {

    public List<ProductModelInfo> models;


    public ProductInfo(Product product,List<ProductModelInfo> models) {
        setId(product.getId());
        setProductLabel(product.getProductLabel());
        setDeviceType(product.getDeviceType());
        setDescription(product.getDescription());
        setProtocolType(product.getProtocolType());
        setDataFormat(product.getDataFormat());
        this.models = models;
    }
}
