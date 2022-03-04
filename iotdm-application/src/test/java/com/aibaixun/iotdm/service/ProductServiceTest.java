package com.aibaixun.iotdm.service;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.iotdm.IOTdmApplication;
import com.aibaixun.iotdm.entity.Product;
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
public class ProductServiceTest {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    @Autowired
    private IProductService productService;


    @Test
    public void  testCreateProduct() throws BaseException {
        Product product = new Product();
        product.setProductLabel("测试产品");
        product.setDescription("单元测试产生产品");
        product.setDataFormat(DataFormat.BINARY);
        product.setProtocolType(ProtocolType.MQTT);
        boolean product1 = createProduct(product);
        logger.info(String.valueOf(product1));
    }


    @Test
    public void testPageQueryProduct (){
        Page<Product> productPage = pageQueryProduct(1, 10, "测试");
        for (Product record : productPage.getRecords()) {
            logger.info(String.valueOf(record));
            productService.removeById(record.getId());
        }
    }

    @Test
    public void testListQueryProduct (){
        List<Product> products = listQueryProduct(10);
        for (Product product : products) {
            logger.info(product.toString());
        }
    }


    private  boolean createProduct (Product product) throws BaseException {
        String productLabel = product.getProductLabel();
        Product checkProduct = productService.queryProductByLabel(productLabel);
        if (Objects.nonNull(checkProduct)){
            throw new BaseException("当前租户下已有同名产品", BaseResultCode.BAD_PARAMS);
        }
        return productService.save(product);
    }

    private List<Product> listQueryProduct (int limit) {
        return productService.queryProducts(limit);
    }


    private Page<Product> pageQueryProduct (int page, int pageSize, String productLabel) {
        return productService.pageQueryByLabel(page, pageSize, productLabel);
    }



}
