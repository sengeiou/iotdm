package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.ModelCommandEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 模型命令 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IModelCommandService extends IService<ModelCommandEntity> {

    /**
     * 统计模型下命令 数目
     * @param modelId 模型di
     * @param commandLabel 命令名称
     * @return 数目
     */
    Long countModelCommandByLabel(String modelId, String commandLabel);


    /**
     * 统计模型下命令 数目
     * @param modelId 模型di
     * @param commandLabel 命令名称
     * @param id id
     * @return 数目
     */
    Long countModelCommandByLabel(String modelId, String commandLabel,String id);

    /**
     * 查询模型命令
     * @param modelId 模型id
     * @return 模型命令
     */
    List<ModelCommandEntity> listQueryByModelId(String modelId);
}
