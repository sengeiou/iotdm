package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.ModelPropertyEntity;
import com.aibaixun.iotdm.mapper.ModelPropertyMapper;
import com.aibaixun.iotdm.service.IModelPropertyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 模型属性 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class ModelPropertyServiceImpl extends ServiceImpl<ModelPropertyMapper, ModelPropertyEntity> implements IModelPropertyService {


    @Override
    public Long countModelPropertyByLabel(String propertyLabel, String modelId) {
        LambdaQueryWrapper<ModelPropertyEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ModelPropertyEntity::getPropertyLabel,propertyLabel).eq(ModelPropertyEntity::getProductModelId,modelId);
        return count(queryWrapper);
    }

    @Override
    public Long countModelPropertyByLabel(String propertyLabel, String modelId, String id) {
        LambdaQueryWrapper<ModelPropertyEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ModelPropertyEntity::getPropertyLabel,propertyLabel)
                .eq(ModelPropertyEntity::getProductModelId,modelId)
                .ne(ModelPropertyEntity::getId,id);
        return count(queryWrapper);
    }

    @Override
    public List<ModelPropertyEntity> listQueryByModelId(String modelId) {
        return list(Wrappers.<ModelPropertyEntity>lambdaQuery().eq(ModelPropertyEntity::getProductModelId,modelId));
    }
}
