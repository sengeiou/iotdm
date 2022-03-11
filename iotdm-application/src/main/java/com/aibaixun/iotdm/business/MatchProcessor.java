package com.aibaixun.iotdm.business;

import com.aibaixun.iotdm.data.ProductEntityInfo;
import com.aibaixun.iotdm.entity.ProductEntity;
import org.springframework.stereotype.Component;

/**
 * 物模型匹配处理器
 * @author wangxiao@aibaixun.com
 * @date 2022/3/11
 */
@Component
public class MatchProcessor extends AbstractReportProcessor<PrePropertyBusinessMsg,MessageBusinessMsg> {




    @Override
    public void processProperty(PrePropertyBusinessMsg propertyBusinessMsg) {
        String productId = propertyBusinessMsg.getMetaData().getProductId();
        ProductEntityInfo productInfo = productService.queryProductInfoById(productId);
    }

    @Override
    public void processMessage(MessageBusinessMsg messageBusinessMsg) {

    }
}
