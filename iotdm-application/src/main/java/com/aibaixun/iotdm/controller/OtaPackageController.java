package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.OtaPackageEntity;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.service.IOtaPackageService;
import com.aibaixun.iotdm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/24
 */
@RestController
@RequestMapping("/ota-package")
public class OtaPackageController extends BaseController{

    private IOtaPackageService otaPackageService;

    private IProductService productService;


    @PostMapping
    public JsonResult<String> createOtaPackage(@RequestBody @Valid OtaPackageEntity otaPackage) throws BaseException {
        String productId = otaPackage.getProductId();
        ProductEntity byId = productService.getById(productId);
        checkEntity(byId,"产品信息不存在");
        otaPackageService.save(otaPackage);
        return JsonResult.success(otaPackage.getId());
    }

 

    @PostMapping("/file")
    public JsonResult<Boolean> createOtaPackageFile(@RequestParam String otaPackageId,
                                                         @RequestParam String checkNum,
                                                         @RequestBody MultipartFile file) throws BaseException {
        checkParameter("otaPackageId", otaPackageId);
        checkParameter("checkNum", checkNum);
        OtaPackageEntity otaPackage = otaPackageService.getById(otaPackageId);
        checkEntity(otaPackage,"ota升级包不存在无法进行文件上传");
        try {
            byte[] bytes = file.getBytes();
            otaPackage.setCheckNum(checkNum);
            otaPackage.setFileName(file.getOriginalFilename());
            otaPackage.setContentType(file.getContentType());
            otaPackage.setData(bytes);
            otaPackage.setDataSize((long) bytes.length);
            otaPackageService.updateById(otaPackage);
        }catch (IOException e){
            throw new BaseException("获取文件字节失败", BaseResultCode.GENERAL_ERROR);
        }
        return JsonResult.success(true);
    }





    @Autowired
    public void setOtaPackageService(IOtaPackageService otaPackageService) {
        this.otaPackageService = otaPackageService;
    }

    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
}
