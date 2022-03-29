package com.aibaixun.iotdm.service;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.iotdm.IotDmApplication;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.enums.DataFormat;
import com.aibaixun.iotdm.enums.ProtocolType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

/**
 * 产品单元测试
 * @author wangxiao@aibaixun.com
 * @date 2022/3/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IotDmApplication.class)
public class ProductEntityServiceTest {

    private final Logger logger = LoggerFactory.getLogger(ProductEntityServiceTest.class);

    @Autowired
    private IProductService productService;


    @Test
    public void  testCreateProduct() throws BaseException {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductLabel("测试产品");
        productEntity.setDescription("单元测试产生产品");
        productEntity.setDataFormat(DataFormat.BINARY);
        productEntity.setProtocolType(ProtocolType.MQTT);
//        boolean product1 = createProduct(productEntity);
//        logger.info(String.valueOf(product1));
    }


    @Test
    public void testPageQueryProduct (){
        Page<ProductEntity> productPage = pageQueryProduct();
        for (ProductEntity record : productPage.getRecords()) {
            logger.info(String.valueOf(record));
            productService.removeById(record.getId());
        }
    }

    @Test
    public void testListQueryProduct (){

    }


//    private  boolean createProduct (ProductEntity productEntity) throws BaseException {
//        String productLabel = productEntity.getProductLabel();
//        ProductEntity checkProductEntity = productService.queryProductByLabel(productLabel);
//        if (Objects.nonNull(checkProductEntity)){
//            throw new BaseException("当前租户下已有同名产品", BaseResultCode.BAD_PARAMS);
//        }
//        return productService.save(productEntity);
//    }




    private Page<ProductEntity> pageQueryProduct() {
        return productService.pageQueryByLabel(1, 10, "测试");
    }



}
