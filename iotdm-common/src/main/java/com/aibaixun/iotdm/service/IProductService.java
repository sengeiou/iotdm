package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.Product;
import com.aibaixun.iotdm.support.ProductInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品表 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IProductService extends IService<Product> {

    /**
     * 分页查询 产品
     * @param page 页码
     * @param pageSize 页容
     * @param productLabel 产品名称
     * @return 分页的产品信息
     */
    Page<Product> pageQueryByLabel(Integer page,Integer pageSize,String productLabel);


    /**
     * id查询 产品详情
     * @param id id
     * @return 产品详情
     */
    ProductInfo queryProductInfoById(String id);


    /**
     * 产品名称查询 产品
     * @param productLabel 产品名称
     * @return 产品
     */
    Product queryProductByLabel(String productLabel);


    /**
     * 查询产品名称
     * @param productIds - 产品id数组
     * @return id-label 对应
     */
    Map<String,String> queryProductLabelByIds(List<String> productIds);


    /**
     * 产品列表
     * @param limit 限制数
     * @return 产品列表
     */
    List<Product> queryProducts (Integer limit);




}
