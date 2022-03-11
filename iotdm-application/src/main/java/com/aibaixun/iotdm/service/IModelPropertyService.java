package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.ModelPropertyEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 模型属性 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IModelPropertyService extends IService<ModelPropertyEntity> {

    /**
     * 统计模型下 属性名称个数
     * @param propertyLabel 属性名称
     * @param modelId 模型id
     * @return 数目
     */
    Long countModelPropertyByLabel(String propertyLabel,String modelId);


    /**
     * 查询模型属性
     * @param modelId 模型id
     * @return 模型属性
     */
    List<ModelPropertyEntity> listQueryByModelId(String modelId);
}
