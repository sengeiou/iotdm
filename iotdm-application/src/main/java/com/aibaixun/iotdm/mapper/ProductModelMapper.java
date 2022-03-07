package com.aibaixun.iotdm.mapper;

import com.aibaixun.iotdm.entity.ProductModelEntity;
import com.aibaixun.iotdm.data.ProductModelEntityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 产品模型 Mapper 接口
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface ProductModelMapper extends BaseMapper<ProductModelEntity> {


    /**
     * 查询产品模型详情 一并查询出 所有的属性与命令
     * @param productId 产品id
     * @return 产品模型信息
     */
    List<ProductModelEntityInfo> selectProductModelInfoByProductId(@Param("productId") String productId);

}
