package com.aibaixun.iotdm.controller;


import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.Product;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.support.ProductInfo;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


/**
 * 产品 Web Api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController{

    private  IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/page")
    public JsonResult<Page<Product>> pageQueryProducts(@RequestParam Integer page,
                                                       @RequestParam Integer pageSize,
                                                       @RequestParam(required = false) String productLabel) throws BaseException {
        checkPage(page,pageSize);
        Page<Product> productPage = productService.pageQueryByLabel(page, pageSize, productLabel);
        return JsonResult.success(productPage);
    }



    @GetMapping("/{id}")
    public JsonResult<ProductInfo> queryById(@PathVariable String id){
        ProductInfo productInfo = productService.queryProductInfoById(id);
        return JsonResult.success(productInfo);
    }


    @GetMapping("/list")
    public JsonResult<List<Product>> listQueryProducts (@RequestParam(required = false) Integer limit) {
        List<Product> products = productService.queryProducts(limit);
        return JsonResult.success(products);
    }


    @PostMapping
    public JsonResult<Boolean> createProduct(@RequestBody @Valid Product product) throws BaseException {
        String productLabel = product.getProductLabel();
        Product checkProduct = productService.queryProductByLabel(productLabel);
        if (Objects.nonNull(checkProduct)){
            throw new BaseException("当前租户下已有同名产品", BaseResultCode.BAD_PARAMS);
        }
        boolean saveResult = productService.save(product);
        return JsonResult.success(saveResult);
    }

    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeProduct (@PathVariable String id) throws BaseException {
        Product pr = productService.getById(id);
        if (Objects.isNull(pr)){
            throw new BaseException("产品不存在无法删除",BaseResultCode.GENERAL_ERROR);
        }
        if (!StringUtils.equals(pr.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("产品必须由创建人删除",BaseResultCode.GENERAL_ERROR);
        }
        boolean remove = productService.removeById(id);
        return JsonResult.success(remove);
    }


    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
}
