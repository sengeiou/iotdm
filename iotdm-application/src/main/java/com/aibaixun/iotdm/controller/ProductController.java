package com.aibaixun.iotdm.controller;


import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.data.UpdateProductParam;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.enums.SubjectEvent;
import com.aibaixun.iotdm.enums.SubjectResource;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.data.ProductEntityInfo;
import com.aibaixun.iotdm.service.IotDmEventPublisher;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.aibaixun.iotdm.constants.DataConstants.IOT_PRODUCT_TENANT_KEY;


/**
 * 产品 Web Api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController{

    private  IProductService productService;

    private RedisRepository redisRepository;

    private IotDmEventPublisher iotDmEventPublisher;

    private IDeviceService deviceService;

    @GetMapping("/page")
    public JsonResult<Page<ProductEntity>> pageQueryProducts(@RequestParam Integer page,
                                                             @RequestParam Integer pageSize,
                                                             @RequestParam(required = false) String productLabel) throws BaseException {
        checkPage(page,pageSize);
        Page<ProductEntity> productPage = productService.pageQueryByLabel(page, pageSize, productLabel);
        return JsonResult.success(productPage);
    }



    @GetMapping("/{id}")
    public JsonResult<ProductEntityInfo> queryById(@PathVariable String id){
        ProductEntityInfo productInfo = productService.queryProductInfoById(id);
        return JsonResult.success(productInfo);
    }


    @GetMapping("/list")
    public JsonResult<List<ProductEntity>> listQueryProducts (@RequestParam(required = false) Integer limit,
                                                              @RequestParam(required = false) String productLabel) {
        List<ProductEntity> productEntities = productService.queryProducts(limit,productLabel);
        return JsonResult.success(productEntities);
    }


    @PostMapping
    public JsonResult<Boolean> createProduct(@RequestBody @Valid ProductEntity productEntity) throws BaseException {
        String productLabel = productEntity.getProductLabel();
        ProductEntity checkProductEntity = productService.queryProductByLabel(productLabel);
        if (Objects.nonNull(checkProductEntity)){
            throw new BaseException("当前租户下已有同名产品", BaseResultCode.BAD_PARAMS);
        }
        boolean saveResult = productService.save(productEntity);
        if (saveResult){
            iotDmEventPublisher.publishEntityChangeEvent(new EntityChangeEvent(SubjectResource.PRODUCT, SubjectEvent.PRODUCT_CREATE,UserInfoUtil.getTenantIdOfNull()));
        }
        return JsonResult.success(saveResult);
    }

    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeProduct (@PathVariable String id) throws BaseException {
        ProductEntity pr = productService.getById(id);
        if (Objects.isNull(pr)){
            throw new BaseException("产品不存在无法删除",BaseResultCode.GENERAL_ERROR);
        }
        if (!StringUtils.equals(pr.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("产品必须由创建人删除",BaseResultCode.GENERAL_ERROR);
        }
        Long aLong = deviceService.countDeviceByProductId(id);
        if (aLong>0){
            throw new BaseException("产品下还有相关设备，无法删除",BaseResultCode.GENERAL_ERROR);
        }
        redisRepository.delHashValues(IOT_PRODUCT_TENANT_KEY, id);
        boolean remove = productService.removeById(id);
        if (remove){
            iotDmEventPublisher.publishEntityChangeEvent(new EntityChangeEvent(SubjectResource.PRODUCT, SubjectEvent.PRODUCT_DELETE,UserInfoUtil.getTenantIdOfNull()));
        }
        return JsonResult.success(remove);
    }


    @PutMapping
    public JsonResult<Boolean> updateProduct (@RequestBody @Valid UpdateProductParam updateProductParam) throws BaseException {
        ProductEntity pr = productService.getById(updateProductParam.getId());
        if (Objects.isNull(pr)){
            throw new BaseException("产品不存在无法更改",BaseResultCode.GENERAL_ERROR);
        }
        ProductEntity checkProductEntity = productService.queryProductByLabel(updateProductParam.getProductLabel());
        if (Objects.nonNull(checkProductEntity)){
            throw new BaseException("当前租户下已有同名产品", BaseResultCode.BAD_PARAMS);
        }
        Boolean aBoolean = productService.updateProduct(updateProductParam.getId(), updateProductParam.getProductLabel(), updateProductParam.getDescription());
        return JsonResult.success(aBoolean);
    }

    @GetMapping("/count")
    public JsonResult<Long> countProduct() {
        Long aLong = productService.countProduct();
        return JsonResult.success(aLong);
    }

    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }


    @Autowired
    public void setRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }


    @Autowired
    public void setIotDmEventPublisher(IotDmEventPublisher iotDmEventPublisher) {
        this.iotDmEventPublisher = iotDmEventPublisher;
    }

    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }
}
