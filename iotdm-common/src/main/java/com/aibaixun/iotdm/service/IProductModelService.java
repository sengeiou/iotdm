package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.ProductModel;
import com.aibaixun.iotdm.support.ProductModelInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

/**
 * <p>
 * 产品模型 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IProductModelService extends IService<ProductModel> {


    /**
     * 查询产品模型信息
     * @param productId 产品id
     * @return 产品模型集合
     */
    List<ProductModelInfo> queryProductModelInfoByProductId(String productId);


    /**
     * 更改产品模型的
     * @param productModel  产品模型
     * @return 更改结果
     */
    Boolean updateProductModel (ProductModel productModel);

}
