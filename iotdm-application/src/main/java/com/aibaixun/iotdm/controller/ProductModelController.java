package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.data.BaseParam;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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
        checkParameterValue(id,"被更改的产品模型不存在id");
        boolean updateResult = productModelService.updateProductModel(productModelEntity);
        return JsonResult.success(updateResult);
    }



    @DeleteMapping("/{modelId}")
    public JsonResult<Boolean> removeProductModel(@PathVariable String modelId) throws BaseException {
        checkParameterValue(modelId,"产品模型id不允许为空");
        ProductModelEntity productModelEntity = productModelService.getById(modelId);
        checkEntity(productModelEntity,"产品模型不存在，无法移除");
        if (!StringUtils.equals(productModelEntity.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("产品模型必须由创建人删除", BaseResultCode.BAD_PARAMS);
        }
        boolean remove = productModelService.removeProductModel(productModelEntity.getProductId(),modelId);
        return JsonResult.success(remove);
    }



    @PostMapping
    public JsonResult<Boolean> createProductModel (@RequestBody @Valid ProductModelEntity productModelEntity) throws BaseException {
        String productId = productModelEntity.getProductId();
        checkParameterValue(productId,"产品id不允许为空");
        ProductEntity productEntity = productService.getById(productId);
        checkEntity(productEntity,"产品信息不存在，无法创建");
        if (StringUtils.isBlank(productModelEntity.getModelType())){
            productModelEntity.setModelType(productModelEntity.getModelLabel());
        }
        boolean save = productModelService.saveProductModelEntity(productModelEntity);
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
        checkEntity(modelEntity,"产品模型不存在，无法创建");
        String productId = modelEntity.getProductId();
        Boolean aBoolean = productModelService.saveModelProperty(productId, modelPropertyEntity);
        return JsonResult.success(aBoolean);
    }

    @DeleteMapping("/property/{id}")
    public JsonResult<Boolean> removeModelProperty(@PathVariable String id) throws BaseException {
        ModelPropertyEntity byId = productModelService.getModelPropertyByPropertyId(id);
        checkEntity(byId,"属性不存在，无法移除");
        ProductModelEntity modelEntity = productModelService.getById(byId.getProductModelId());
        checkEntity(modelEntity,"产品模型不存在，无法移除");
        String productId = modelEntity.getProductId();
        Boolean aBoolean = productModelService.removeModelProperty(productId, id);
        return JsonResult.success(aBoolean);
    }

    @PutMapping("/property")
    public JsonResult<Boolean> updateModelProperty(@RequestBody @Valid ModelPropertyEntity modelPropertyEntity) throws BaseException {
        String modelPropertyEntityId = modelPropertyEntity.getId();
        checkParameterValue(modelPropertyEntityId,"属性id不允许为空");
        String productModelId = modelPropertyEntity.getProductModelId();
        ProductModelEntity modelEntity = productModelService.getById(productModelId);
        checkEntity(modelEntity,"产品模型不存在，无法修改");
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
        if (!checkModelCommandEntityParam(modelCommandEntity)){
            throw new BaseException("下发参数或者响应参数填写有误，请检查",BaseResultCode.BAD_PARAMS);
        }
        ProductModelEntity modelEntity = productModelService.getById(productModelId);
        checkEntity(modelEntity,"产品模型不存在，无法创建");
        String productId = modelEntity.getProductId();
        Boolean aBoolean = productModelService.saveModelCommand(productId, modelCommandEntity);
        return JsonResult.success(aBoolean);
    }

    @DeleteMapping("/command/{id}")
    public JsonResult<Boolean> removeModelCommand(@PathVariable String id) throws BaseException {
        ModelCommandEntity byId = productModelService.getModelCommandByPropertyId(id);
        checkEntity(byId,"命令不存在，无法移除");
        ProductModelEntity modelEntity = productModelService.getById(byId.getProductModelId());
        checkEntity(modelEntity,"产品模型不存在，无法移除");
        String productId = modelEntity.getProductId();
        Boolean aBoolean = productModelService.removeModelCommand(productId, id);
        return JsonResult.success(aBoolean);
    }

    @PutMapping("/command")
    public JsonResult<Boolean> updateModelCommand(@RequestBody @Valid ModelCommandEntity modelCommandEntity) throws BaseException {
        String modelPropertyEntityId = modelCommandEntity.getId();
        if (!checkModelCommandEntityParam(modelCommandEntity)){
            throw new BaseException("下发参数或者响应参数填写有误，请检查",BaseResultCode.BAD_PARAMS);
        }
        checkParameterValue(modelPropertyEntityId,"命令id不允许为空");
        String productModelId = modelCommandEntity.getProductModelId();
        ProductModelEntity modelEntity = productModelService.getById(productModelId);
        checkEntity(modelEntity,"产品模型不存在，无法修改");
        Boolean aBoolean = productModelService.updateModelCommand(modelEntity.getProductId(), modelCommandEntity);
        return JsonResult.success(aBoolean);
    }


    /**
     * 校验 命令 参数
     * @param modelCommandEntity 抹胸命令
     * @return 校验结果
     */
    private boolean checkModelCommandEntityParam (ModelCommandEntity modelCommandEntity){
        List<BaseParam> params = modelCommandEntity.getParams();
        List<BaseParam> responses = modelCommandEntity.getResponses();
        if (CollectionUtils.isEmpty(params) && CollectionUtils.isEmpty(responses)){
            return true;
        }
        List<BaseParam> baseParams = new ArrayList<>();
        baseParams.addAll(params);
        baseParams.addAll(responses);
        for (BaseParam baseParam : baseParams){
            if (!checkBaseParam(baseParam)){
                return false;
            }
        }
        return true;
    }

    /**
     * 校验基础参数
     * @param baseParam 基础参数
     * @return 返回校验结果
     */
    private boolean checkBaseParam (BaseParam baseParam){
        return Objects.nonNull(baseParam) && Objects.nonNull(baseParam.getDataType()) &&Objects.nonNull(baseParam.getParamLabel());
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
