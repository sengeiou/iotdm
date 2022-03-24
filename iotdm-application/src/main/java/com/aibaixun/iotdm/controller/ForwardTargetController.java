package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.data.RuleTargetEntityResource;
import com.aibaixun.iotdm.entity.ForwardRuleEntity;
import com.aibaixun.iotdm.entity.ForwardTargetEntity;
import com.aibaixun.iotdm.entity.RuleResourceEntity;
import com.aibaixun.iotdm.service.IForwardRuleService;
import com.aibaixun.iotdm.service.IForwardTargetService;
import com.aibaixun.iotdm.service.IRuleResourceService;
import com.aibaixun.iotdm.util.UserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.aibaixun.iotdm.constants.DataConstants.IOT_TENANT_FORWARD_KEY;

/**
 * 转发目标 web api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@RestController
@RequestMapping("/forward-target")
public class ForwardTargetController extends BaseController{


    private IForwardTargetService forwardTargetService;


    private IForwardRuleService forwardRuleService;


    private IRuleResourceService ruleResourceService;

    private RedisRepository redisRepository;


    @GetMapping("/list")
    public JsonResult<List<RuleTargetEntityResource>> listQueryByRuleId(@RequestParam String ruleId) {
        List<RuleTargetEntityResource> ruleTargetResources = forwardTargetService.listQueryRuleTargetAndResource(ruleId);
        return JsonResult.success(ruleTargetResources);
    }



    @PostMapping
    public JsonResult<Boolean> createRuleForwardTarget (@RequestBody @Valid ForwardTargetEntity forwardTargetEntity) throws BaseException {
        Long aLong = forwardTargetService.countTargetByRuleId(forwardTargetEntity.getForwardRuleId());
        final  int maxTargetNum = 10;
        if (aLong>=maxTargetNum){
            throw new BaseException("转发目标不允许超过10", BaseResultCode.GENERAL_ERROR);
        }
        checkResourceAndRule(forwardTargetEntity);
        redisRepository.delHashValues(IOT_TENANT_FORWARD_KEY, UserInfoUtil.getTenantIdOfNull());
        boolean save = forwardTargetService.save(forwardTargetEntity);
        return JsonResult.success(save);
    }


    @PutMapping
    public JsonResult<Boolean> updateRuleForwardTarget (@RequestBody @Valid ForwardTargetEntity forwardTargetEntity) throws BaseException {

        String id = forwardTargetEntity.getId();
        checkParameterValue(id,"转发规则目标id不允许为空");
        checkResourceAndRule(forwardTargetEntity);
        redisRepository.delHashValues(IOT_TENANT_FORWARD_KEY, UserInfoUtil.getTenantIdOfNull());
        boolean save = forwardTargetService.updateById(forwardTargetEntity);
        return JsonResult.success(save);
    }

    private void checkResourceAndRule(ForwardTargetEntity forwardTargetEntity) throws BaseException {
        String forwardRuleId = forwardTargetEntity.getForwardRuleId();
        String ruleResourceId = forwardTargetEntity.getRuleResourceId();
        ForwardRuleEntity forwardRuleEntity = forwardRuleService.getById(forwardRuleId);
        checkEntity(forwardRuleEntity,"转发规则目标不存在");
        RuleResourceEntity ruleResource = ruleResourceService.getById(ruleResourceId);
        checkEntity(ruleResource,"转发资源不存在");
    }


    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeForwardTarget(@PathVariable String id) throws BaseException {
        ForwardTargetEntity forwardTargetEntity = forwardTargetService.getById(id);
        checkEntity(forwardTargetEntity,"转发规则目标不存在");
        if (!StringUtils.equals(forwardTargetEntity.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("转发目标必须由创建人删除", BaseResultCode.GENERAL_ERROR);
        }
        boolean removeById = forwardTargetService.removeById(forwardTargetEntity);
        return JsonResult.success(removeById);
    }


    @Autowired
    public void setForwardRuleService(IForwardRuleService forwardRuleService) {
        this.forwardRuleService = forwardRuleService;
    }


    @Autowired
    public void setRuleResourceService(IRuleResourceService ruleResourceService) {
        this.ruleResourceService = ruleResourceService;
    }

    @Autowired
    public void setForwardTargetService(IForwardTargetService forwardTargetService) {
        this.forwardTargetService = forwardTargetService;
    }


    @Autowired
    public void setRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
}