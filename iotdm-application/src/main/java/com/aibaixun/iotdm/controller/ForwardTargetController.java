package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.entity.ForwardRuleEntity;
import com.aibaixun.iotdm.entity.ForwardTargetEntity;
import com.aibaixun.iotdm.entity.RuleResourceEntity;
import com.aibaixun.iotdm.service.IForwardRuleService;
import com.aibaixun.iotdm.service.IForwardTargetService;
import com.aibaixun.iotdm.service.IRuleResourceService;
import com.aibaixun.iotdm.data.RuleTargetEntityResource;
import com.aibaixun.iotdm.util.UserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
        checkResourceAndRule(forwardTargetEntity);
        redisRepository.delHashValues(IOT_TENANT_FORWARD_KEY, UserInfoUtil.getTenantIdOfNull());
        boolean save = forwardTargetService.save(forwardTargetEntity);
        return JsonResult.success(save);
    }


    @PutMapping
    public JsonResult<Boolean> updateRuleForwardTarget (@RequestBody @Valid ForwardTargetEntity forwardTargetEntity) throws BaseException {

        String id = forwardTargetEntity.getId();
        if (StringUtils.isBlank(id)){
            throw new BaseException("更改转发目标id不允许为空", BaseResultCode.BAD_PARAMS);
        }
        checkResourceAndRule(forwardTargetEntity);
        redisRepository.delHashValues(IOT_TENANT_FORWARD_KEY, UserInfoUtil.getTenantIdOfNull());
        boolean save = forwardTargetService.save(forwardTargetEntity);
        return JsonResult.success(save);
    }

    private void checkResourceAndRule(ForwardTargetEntity forwardTargetEntity) throws BaseException {
        String forwardRuleId = forwardTargetEntity.getForwardRuleId();
        String ruleResourceId = forwardTargetEntity.getRuleResourceId();
        ForwardRuleEntity forwardRuleEntity = forwardRuleService.getById(forwardRuleId);
        if (Objects.isNull(forwardRuleEntity)){
            throw new BaseException("转发规则不存在", BaseResultCode.GENERAL_ERROR);
        }
        RuleResourceEntity ruleResource = ruleResourceService.getById(ruleResourceId);
        if (Objects.isNull(ruleResource)){
            throw new BaseException("转发资源不存在", BaseResultCode.GENERAL_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeForwardTarget(@PathVariable String id) throws BaseException {
        ForwardTargetEntity forwardTargetEntity = forwardTargetService.getById(id);
        if (Objects.isNull(forwardTargetEntity)){
            throw new BaseException("转发目标不存在", BaseResultCode.GENERAL_ERROR);
        }
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