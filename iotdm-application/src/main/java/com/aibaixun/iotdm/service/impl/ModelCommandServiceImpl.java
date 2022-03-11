package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.ModelCommandEntity;
import com.aibaixun.iotdm.mapper.ModelCommandMapper;
import com.aibaixun.iotdm.service.IModelCommandService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 模型命令 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class ModelCommandServiceImpl extends ServiceImpl<ModelCommandMapper, ModelCommandEntity> implements IModelCommandService {

    @Override
    public Long countModelCommandByLabel(String modelId, String commandLabel) {
        LambdaQueryWrapper<ModelCommandEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ModelCommandEntity::getCommandLabel,commandLabel).eq(ModelCommandEntity::getProductModelId,modelId);
        return count(queryWrapper);
    }

    @Override
    public List<ModelCommandEntity> listQueryByModelId(String modelId) {
        return list(Wrappers.<ModelCommandEntity>lambdaQuery().eq(ModelCommandEntity::getProductModelId,modelId));
    }
}
