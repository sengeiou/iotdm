package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.entity.ProductModelEntity;
import com.aibaixun.iotdm.service.IProductModelService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.util.UserInfoUtil;
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
    public JsonResult<Boolean> updateProductModel(@RequestBody @Valid ProductModelEntity productModelEntity) throws BaseException {
        String id = productModelEntity.getId();
        if (StringUtils.isBlank(id)){
            throw new BaseException("被更改的产品模型不存在id", BaseResultCode.BAD_PARAMS);
        }
        boolean updateResult = productModelService.updateProductModel(productModelEntity);
        return JsonResult.success(updateResult);
    }



    @DeleteMapping("/{modelId}")
    public JsonResult<Boolean> removeProductModel(@PathVariable String modelId) throws BaseException {
        if (StringUtils.isBlank(modelId)){
            throw new BaseException("产品模型id不允许为空", BaseResultCode.BAD_PARAMS);
        }
        ProductModelEntity productModelEntity = productModelService.getById(modelId);
        if (Objects.isNull(productModelEntity)){
            throw new BaseException("产品模型id不允许为空", BaseResultCode.BAD_PARAMS);
        }
        if (!StringUtils.equals(productModelEntity.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("产品模型必须由创建人删除", BaseResultCode.BAD_PARAMS);
        }
        boolean remove = productModelService.removeById(modelId);
        return JsonResult.success(remove);
    }



    @PostMapping
    public JsonResult<Boolean> createProductModel (@RequestBody @Valid ProductModelEntity productModelEntity) throws BaseException {
        String productId = productModelEntity.getProductId();
        if (StringUtils.isBlank(productId)){
            throw new BaseException("产品id不允许为空", BaseResultCode.BAD_PARAMS);
        }
        ProductEntity productEntity = productService.getById(productId);
        if (Objects.isNull(productEntity)){
            throw new BaseException("创建的模型的产品不存在", BaseResultCode.BAD_PARAMS);
        }
        if (StringUtils.isBlank(productModelEntity.getModelType())){
            productModelEntity.setModelType(productModelEntity.getModelLabel());
        }
        boolean save = productModelService.save(productModelEntity);
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
