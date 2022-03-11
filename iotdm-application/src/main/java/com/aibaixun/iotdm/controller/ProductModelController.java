package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.data.ProductModelEntityInfo;
import com.aibaixun.iotdm.entity.ModelCommandEntity;
import com.aibaixun.iotdm.entity.ModelPropertyEntity;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.entity.ProductModelEntity;
import com.aibaixun.iotdm.service.IProductModelService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.util.UserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
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




    @GetMapping("/list")
    public JsonResult<List<ProductModelEntityInfo>> listQueryProductModel(@RequestParam @NotBlank(message = "产品id不允许为空") String productId){
        List<ProductModelEntityInfo> productModelEntityInfos = productModelService.queryProductModelInfoByProductId(productId);
        return JsonResult.success(productModelEntityInfos);
    }


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
            throw new BaseException("创建的模型的产品不存在", BaseResultCode.GENERAL_ERROR);
        }
        if (StringUtils.isBlank(productModelEntity.getModelType())){
            productModelEntity.setModelType(productModelEntity.getModelLabel());
        }
        boolean save = productModelService.save(productModelEntity);
        return JsonResult.success(save);
    }



    @GetMapping("/property")
    public JsonResult<List<ModelPropertyEntity>> queryModelProperty(@RequestParam @NotBlank(message = "模型id不能为空") String modelId){
        List<ModelPropertyEntity> modelPropertyEntities = productModelService.queryModelPropertyByModelId(modelId);
        return JsonResult.success(modelPropertyEntities);
    }


    @PostMapping("/property")
    public JsonResult<Boolean> createModelProperty(@RequestBody @Valid ModelPropertyEntity modelPropertyEntity) throws BaseException {
        String productModelId = modelPropertyEntity.getProductModelId();
        ProductModelEntity modelEntity = productModelService.getById(productModelId);
        if (Objects.isNull(modelEntity)){
            throw new BaseException("创建的模型属性的产品不存在", BaseResultCode.GENERAL_ERROR);
        }
        String productId = modelEntity.getProductId();
        Boolean aBoolean = productModelService.saveModelProperty(productId, modelPropertyEntity);
        return JsonResult.success(aBoolean);
    }

    @DeleteMapping("/property/{id}")
    public JsonResult<Boolean> removeModelProperty(@PathVariable String id) throws BaseException {
        ModelPropertyEntity byId = productModelService.getModelPropertyByPropertyId(id);
        if (Objects.isNull(byId)){
            throw new BaseException("模型下属性已经不存在无法删除", BaseResultCode.GENERAL_ERROR);
        }
        ProductModelEntity modelEntity = productModelService.getById(byId.getProductModelId());
        if (Objects.isNull(modelEntity)){
            throw new BaseException("删除的模型属性的产品不存在", BaseResultCode.GENERAL_ERROR);
        }
        String productId = modelEntity.getProductId();
        Boolean aBoolean = productModelService.removeModelProperty(productId, id);
        return JsonResult.success(aBoolean);
    }

    @PutMapping("/property")
    public JsonResult<Boolean> updateModelProperty(@RequestBody @Valid ModelPropertyEntity modelPropertyEntity) throws BaseException {
        String modelPropertyEntityId = modelPropertyEntity.getId();
        if (StringUtils.isBlank(modelPropertyEntityId)) {
            throw new BaseException("模型属性id为空不允许更改", BaseResultCode.BAD_PARAMS);
        }
        String productModelId = modelPropertyEntity.getProductModelId();
        ProductModelEntity modelEntity = productModelService.getById(productModelId);
        if (Objects.isNull(modelEntity)){
            throw new BaseException("更改的模型属性的产品不存在", BaseResultCode.GENERAL_ERROR);
        }
        Boolean aBoolean = productModelService.updateModelProperty(modelEntity.getProductId(), modelPropertyEntity);
        return JsonResult.success(aBoolean);
    }


    @GetMapping("/command")
    public JsonResult<List<ModelCommandEntity>> queryModelCommand(@RequestParam @NotBlank(message = "模型id不能为空") String modelId){
        List<ModelCommandEntity> modelCommandEntities = productModelService.queryModelCommandByModelId(modelId);
        return JsonResult.success(modelCommandEntities);
    }

    @PostMapping("/command")
    public JsonResult<Boolean> createModelCommand(@RequestBody @Valid ModelCommandEntity modelCommandEntity) throws BaseException {
        String productModelId = modelCommandEntity.getProductModelId();
        ProductModelEntity modelEntity = productModelService.getById(productModelId);
        if (Objects.isNull(modelEntity)){
            throw new BaseException("创建的模型命令的产品不存在", BaseResultCode.GENERAL_ERROR);
        }
        String productId = modelEntity.getProductId();
        Boolean aBoolean = productModelService.saveModelCommand(productId, modelCommandEntity);
        return JsonResult.success(aBoolean);
    }

    @DeleteMapping("/command/{id}")
    public JsonResult<Boolean> removeModelCommand(@PathVariable String id) throws BaseException {
        ModelCommandEntity byId = productModelService.getModelCommandByPropertyId(id);
        if (Objects.isNull(byId)){
            throw new BaseException("模型下命令已经不存在无法删除", BaseResultCode.GENERAL_ERROR);
        }
        ProductModelEntity modelEntity = productModelService.getById(byId.getProductModelId());
        if (Objects.isNull(modelEntity)){
            throw new BaseException("删除的模型命令的产品不存在", BaseResultCode.GENERAL_ERROR);
        }
        String productId = modelEntity.getProductId();
        Boolean aBoolean = productModelService.removeModelCommand(productId, id);
        return JsonResult.success(aBoolean);
    }

    @PutMapping("/command")
    public JsonResult<Boolean> updateModelCommand(@RequestBody @Valid ModelCommandEntity modelCommandEntity) throws BaseException {
        String modelPropertyEntityId = modelCommandEntity.getId();
        if (StringUtils.isBlank(modelPropertyEntityId)) {
            throw new BaseException("模型命令id为空不允许更改", BaseResultCode.BAD_PARAMS);
        }
        String productModelId = modelCommandEntity.getProductModelId();
        ProductModelEntity modelEntity = productModelService.getById(productModelId);
        if (Objects.isNull(modelEntity)){
            throw new BaseException("更改的模型命令的产品不存在", BaseResultCode.GENERAL_ERROR);
        }
        Boolean aBoolean = productModelService.updateModelCommand(modelEntity.getProductId(), modelCommandEntity);
        return JsonResult.success(aBoolean);
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
