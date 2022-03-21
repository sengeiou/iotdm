package com.aibaixun.iotdm.service;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.iotdm.entity.ModelCommandEntity;
import com.aibaixun.iotdm.entity.ModelPropertyEntity;
import com.aibaixun.iotdm.entity.ProductModelEntity;
import com.aibaixun.iotdm.data.ProductModelEntityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

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

    /**
     * 移除产品模型
     * @param productId 产品id
     * @param productModelId 模型id
     * @return result
     */
    Boolean removeProductModel(String productId,String productModelId);

    /**
     * 保存模型 属性
     * @param productId  产品id
     * @param modelPropertyEntity 模型属性
     * @return 更改结果
     * @throws BaseException
     */
    Boolean saveModelProperty(String productId, ModelPropertyEntity modelPropertyEntity) throws BaseException;


    /**
     * 更改模型 属性
     * @param productId  产品id
     * @param modelPropertyEntity 模型属性
     * @return 更改结果
     * @throws BaseException
     */
    Boolean updateModelProperty(String productId, ModelPropertyEntity modelPropertyEntity) throws BaseException;


    /**
     * 更改模型 属性
     * @param productId  产品id
     * @param propertyId 模型属性id
     * @return 更改结果
     */
    Boolean removeModelProperty(String productId, String propertyId) ;



    /**
     * 创建模型 命令
     * @param productId  产品id
     * @param modelCommandEntity 模型命令id
     * @return 更改结果
     * @throws BaseException
     */
    Boolean saveModelCommand(String productId, ModelCommandEntity modelCommandEntity) throws BaseException;


    /**
     * 更改模型 命令
     * @param productId  产品id
     * @param modelCommandEntity 模型命令
     * @return 更改结果
     * @throws BaseException
     */
    Boolean updateModelCommand(String productId, ModelCommandEntity modelCommandEntity) throws BaseException;


    /**
     * 移除模型 命令
     * @param productId  产品id
     * @param commandId 模型命令id
     * @return 更改结果
     */
    Boolean removeModelCommand(String productId, String commandId);


    /**
     * 获取模型 属性
     * @param propertyId 属性id
     * @return s属性
     */
    ModelPropertyEntity getModelPropertyByPropertyId(String propertyId);

    /**
     * 获取模型 命令
     * @param commandId 命令id
     * @return 命令
     */
    ModelCommandEntity getModelCommandByPropertyId(String commandId);


    /**
     * 查询模型属性
     * @param modelId 模型id
     * @return 模型属性
     */
    List<ModelPropertyEntity> queryModelPropertyByModelId(String modelId);

    /**
     * 查询模型命令
     * @param modelId 模型id
     * @return 模型命令
     */
    List<ModelCommandEntity> queryModelCommandByModelId(String modelId);

    /**
     * 保存模型
     * * @param productModelEntity 模型
     * @return 结果
     */
    Boolean saveProductModelEntity(ProductModelEntity productModelEntity);
}
