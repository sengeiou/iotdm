package com.aibaixun.iotdm.service;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.iotdm.IOTdmApplication;
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
 * @author wangxiao@aibaixun.com
 * @date 2022/3/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IOTdmApplication.class)
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
        boolean product1 = createProduct(productEntity);
        logger.info(String.valueOf(product1));
    }


    @Test
    public void testPageQueryProduct (){
        Page<ProductEntity> productPage = pageQueryProduct(1, 10, "测试");
        for (ProductEntity record : productPage.getRecords()) {
            logger.info(String.valueOf(record));
            productService.removeById(record.getId());
        }
    }

    @Test
    public void testListQueryProduct (){
        List<ProductEntity> productEntities = listQueryProduct(10);
        for (ProductEntity productEntity : productEntities) {
            logger.info(productEntity.toString());
        }
    }


    private  boolean createProduct (ProductEntity productEntity) throws BaseException {
        String productLabel = productEntity.getProductLabel();
        ProductEntity checkProductEntity = productService.queryProductByLabel(productLabel);
        if (Objects.nonNull(checkProductEntity)){
            throw new BaseException("当前租户下已有同名产品", BaseResultCode.BAD_PARAMS);
        }
        return productService.save(productEntity);
    }

    private List<ProductEntity> listQueryProduct (int limit) {
        return productService.queryProducts(limit);
    }


    private Page<ProductEntity> pageQueryProduct (int page, int pageSize, String productLabel) {
        return productService.pageQueryByLabel(page, pageSize, productLabel);
    }



}
