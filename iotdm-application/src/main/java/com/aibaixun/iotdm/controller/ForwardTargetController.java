package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.ForwardRule;
import com.aibaixun.iotdm.entity.ForwardTarget;
import com.aibaixun.iotdm.entity.RuleResource;
import com.aibaixun.iotdm.service.IForwardRuleService;
import com.aibaixun.iotdm.service.IForwardTargetService;
import com.aibaixun.iotdm.service.IRuleResourceService;
import com.aibaixun.iotdm.support.RuleTargetResource;
import com.aibaixun.iotdm.util.UserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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


    @GetMapping("/list")
    public JsonResult<List<RuleTargetResource>> listQueryByRuleId(@RequestParam String ruleId) {
        List<RuleTargetResource> ruleTargetResources = forwardTargetService.listQueryRuleTargetAndResource(ruleId);
        return JsonResult.success(ruleTargetResources);
    }



    @PostMapping
    public JsonResult<Boolean> createRuleForwardTarget (@RequestBody @Valid ForwardTarget forwardTarget) throws BaseException {
        checkResourceAndRule(forwardTarget);
        boolean save = forwardTargetService.save(forwardTarget);
        return JsonResult.success(save);
    }


    @PutMapping
    public JsonResult<Boolean> updateRuleForwardTarget (@RequestBody @Valid ForwardTarget forwardTarget) throws BaseException {

        String id = forwardTarget.getId();
        if (StringUtils.isBlank(id)){
            throw new BaseException("更改转发目标id不允许为空", BaseResultCode.BAD_PARAMS);
        }
        checkResourceAndRule(forwardTarget);
        boolean save = forwardTargetService.save(forwardTarget);
        return JsonResult.success(save);
    }

    private void checkResourceAndRule(ForwardTarget forwardTarget) throws BaseException {
        String forwardRuleId = forwardTarget.getForwardRuleId();
        String ruleResourceId = forwardTarget.getRuleResourceId();
        ForwardRule forwardRule = forwardRuleService.getById(forwardRuleId);
        if (Objects.isNull(forwardRule)){
            throw new BaseException("转发规则不存在", BaseResultCode.GENERAL_ERROR);
        }
        RuleResource ruleResource = ruleResourceService.getById(ruleResourceId);
        if (Objects.isNull(ruleResource)){
            throw new BaseException("转发资源不存在", BaseResultCode.GENERAL_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeForwardTarget(@PathVariable String id) throws BaseException {
        ForwardTarget forwardTarget = forwardTargetService.getById(id);
        if (Objects.isNull(forwardTarget)){
            throw new BaseException("转发目标不存在", BaseResultCode.GENERAL_ERROR);
        }
        if (!StringUtils.equals(forwardTarget.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("转发目标必须由创建人删除", BaseResultCode.GENERAL_ERROR);
        }
        boolean removeById = forwardTargetService.removeById(forwardTarget);
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

}