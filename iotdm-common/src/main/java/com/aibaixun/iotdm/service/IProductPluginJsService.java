package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.ProductPluginJs;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 产品插件-js插件 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IProductPluginJsService extends IService<ProductPluginJs> {

    /**
     * 查询 产品js 插件
     * @param productId 产品id
     * @return js插件
     */
    ProductPluginJs queryByProductPluginJs(String productId);


    /**
     * 卸载产品js 插件 也就是让js 插件回到debug 状态
     * @param productId 产品id
     * @return 修改状态
     */
    Boolean uninstallJsPlugin(String productId);

}
