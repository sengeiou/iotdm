package com.aibaixun.iotdm.controller;


import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.Product;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.support.ProductInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
}
