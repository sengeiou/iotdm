package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.ProductModelEntity;
import com.aibaixun.iotdm.data.ProductModelEntityInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品模型 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IProductModelService extends IService<ProductModelEntity> {


    /**
     * 查询产品模型信息
     * @param productId 产品id
     * @return 产品模型集合
     */
    List<ProductModelEntityInfo> queryProductModelInfoByProductId(String productId);


    /**
     * 更改产品模型的
     * @param productModelEntity  产品模型
     * @return 更改结果
     */
    Boolean updateProductModel (ProductModelEntity productModelEntity);

}
