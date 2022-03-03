package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.ProductPluginJs;
import com.aibaixun.iotdm.service.IProductPluginJsService;
import com.aibaixun.iotdm.support.JsDebugParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * js 插件开发 Web Api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("js-plugin")
public class ProductPluginJsController extends BaseController{

    private IProductPluginJsService productPluginJsService;

    @GetMapping
    public JsonResult<ProductPluginJs> queryJsPlugin (@RequestParam String productId) throws BaseException {
        if (StringUtils.isBlank(productId)){
            throw new BaseException("产品id不存在", BaseResultCode.BAD_PARAMS);
        }
        ProductPluginJs pluginJs = productPluginJsService.queryByProductPluginJs(productId);
        return JsonResult.success(pluginJs);
    }


    @PostMapping
    public JsonResult<Boolean> createOrUpdateJsPlugin (@RequestBody @Valid ProductPluginJs productPluginJs){
        String id = productPluginJs.getId();
        boolean result ;
        if (StringUtils.isBlank(id)){
            result = productPluginJsService.save(productPluginJs);
        }else {
            result = productPluginJsService.updateById(productPluginJs);
        }
        return JsonResult.success(result);
    }



    @PutMapping
    public JsonResult<Boolean> uninstallJsPlugin (@RequestParam String productId) {
        Boolean uninstallRes = productPluginJsService.uninstallJsPlugin(productId);
        return JsonResult.success(uninstallRes);
    }


    @PostMapping("/debug")
    public JsonResult<String> debugJsPlugin(@RequestBody @Valid JsDebugParam jsDebugParam) {
        // todo  js 执行函数逻辑
        return JsonResult.success("{}");
    }




    @Autowired
    public void setProductPluginJsService(IProductPluginJsService productPluginJsService) {
        this.productPluginJsService = productPluginJsService;
    }
}
