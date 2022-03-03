package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.ProductModel;
import com.aibaixun.iotdm.service.IProductModelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 产品模型 Web Api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("product-model")
public class ProductModelController extends BaseController{


    private IProductModelService productModelService;

    @PutMapping
    public JsonResult<Boolean> updateProductModel(@RequestBody @Valid ProductModel productModel) throws BaseException {
        String id = productModel.getId();
        if (StringUtils.isBlank(id)){
            throw new BaseException("被更改的产品模型不存在id", BaseResultCode.BAD_PARAMS);
        }
        boolean updateResult = productModelService.updateProductModel(productModel);
        return JsonResult.success(updateResult);
    }





    @Autowired
    public void setProductModelService(IProductModelService productModelService) {
        this.productModelService = productModelService;
    }
}
