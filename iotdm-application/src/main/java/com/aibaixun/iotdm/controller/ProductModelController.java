package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.Product;
import com.aibaixun.iotdm.entity.ProductModel;
import com.aibaixun.iotdm.service.IProductModelService;
import com.aibaixun.iotdm.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 产品模型 Web Api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("product-model")
public class ProductModelController extends BaseController{


    private IProductModelService productModelService;

    private IProductService productService;

    @PutMapping
    public JsonResult<Boolean> updateProductModel(@RequestBody @Valid ProductModel productModel) throws BaseException {
        String id = productModel.getId();
        if (StringUtils.isBlank(id)){
            throw new BaseException("被更改的产品模型不存在id", BaseResultCode.BAD_PARAMS);
        }
        boolean updateResult = productModelService.updateProductModel(productModel);
        return JsonResult.success(updateResult);
    }



    @DeleteMapping("/{modelId}")
    public JsonResult<Boolean> removeProductModel(@PathVariable String modelId) throws BaseException {
        if (StringUtils.isBlank(modelId)){
            throw new BaseException("产品模型id不允许为空", BaseResultCode.BAD_PARAMS);
        }
        boolean remove = productModelService.removeById(modelId);
        return JsonResult.success(remove);
    }



    @PostMapping
    public JsonResult<Boolean> createProductModel (@RequestBody @Valid ProductModel productModel) throws BaseException {
        String productId = productModel.getProductId();
        if (StringUtils.isBlank(productId)){
            throw new BaseException("产品id不允许为空", BaseResultCode.BAD_PARAMS);
        }
        Product product = productService.getById(productId);
        if (Objects.isNull(product)){
            throw new BaseException("创建的模型的产品不存在", BaseResultCode.BAD_PARAMS);
        }
        if (StringUtils.isBlank(productModel.getModelType())){
            productModel.setModelType(productModel.getModelLabel());
        }
        boolean save = productModelService.save(productModel);
        return JsonResult.success(save);
    }




    @Autowired
    public void setProductModelService(IProductModelService productModelService) {
        this.productModelService = productModelService;
    }

    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
}
