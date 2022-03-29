package com.aibaixun.iotdm.data;

import com.aibaixun.iotdm.entity.ProductEntity;

import java.util.List;

/**
 * 产品详细信息
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
public class ProductEntityInfo extends ProductEntity {

    public List<ProductModelEntityInfo> models;


    public ProductEntityInfo(ProductEntity productEntity, List<ProductModelEntityInfo> models) {
        setId(productEntity.getId());
        setProductLabel(productEntity.getProductLabel());
        setDeviceType(productEntity.getDeviceType());
        setDescription(productEntity.getDescription());
        setProtocolType(productEntity.getProtocolType());
        setDataFormat(productEntity.getDataFormat());
        setCreateTime(productEntity.getCreateTime());
        this.models = models;
    }
}
