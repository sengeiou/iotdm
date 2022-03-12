package com.aibaixun.iotdm.server;

import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.msg.ForwardRuleInfo;
import com.aibaixun.iotdm.rule.server.RuleService;
import com.aibaixun.iotdm.service.IForwardRuleService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.toolkit.coomon.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.aibaixun.iotdm.constants.DataConstants.IOT_PRODUCT_TENANT_KEY;
import static com.aibaixun.iotdm.constants.DataConstants.IOT_TENANT_FORWARD_KEY;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/3/12
 */
@Service
public class RuleServiceImpl implements RuleService {


    private IProductService productService;

    private RedisRepository redisRepository;


    private IForwardRuleService forwardRuleService;

    @Override
    public List<ForwardRuleInfo> queryForwardRule(String tenantId) {
        if (StringUtil.isBlank(tenantId)){
            return null;
        }
        Object hashValues = redisRepository.getHashValues(IOT_TENANT_FORWARD_KEY, tenantId);
        if (Objects.nonNull(hashValues)){
            return (List<ForwardRuleInfo>) hashValues;
        }
        List<ForwardRuleInfo> forwardRuleInfos = forwardRuleService.queryForwardRuleByTenantId(tenantId);
        redisRepository.putHashValue(IOT_TENANT_FORWARD_KEY, tenantId,forwardRuleInfos);
        return forwardRuleInfos;
    }

    @Override
    public String getCurrentProductTenantId(String productId) {
        Object hashValues = redisRepository.getHashValues(IOT_PRODUCT_TENANT_KEY, productId);
        if (Objects.nonNull(hashValues)){
            return (String) hashValues;
        }
        ProductEntity byId = productService.getById(productId);
        if (Objects.nonNull(byId)){
            redisRepository.putHashValue(IOT_PRODUCT_TENANT_KEY, productId,byId.getTenantId());
            return byId.getTenantId();
        }
        return null;
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
    public void setForwardRuleService(IForwardRuleService forwardRuleService) {
        this.forwardRuleService = forwardRuleService;
    }
}
