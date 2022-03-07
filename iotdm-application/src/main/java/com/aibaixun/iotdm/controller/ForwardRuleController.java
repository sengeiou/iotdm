package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.ForwardRule;
import com.aibaixun.iotdm.service.IForwardRuleService;
import com.aibaixun.iotdm.service.IForwardTargetService;
import com.aibaixun.iotdm.support.RuleStatusParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 转发规则 web api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@RestController
@RequestMapping("/forward-rule")
public class ForwardRuleController extends BaseController{



    private IForwardRuleService forwardRuleService;


    private IForwardTargetService forwardTargetService;

    @GetMapping("/page")
    public JsonResult<Page<ForwardRule>> pageQueryForwardRule (@RequestParam Integer page,
                                                   @RequestParam Integer pageSize,
                                                   @RequestParam(required = false) String ruleLabel) {
        Page<ForwardRule> forwardRulePage = forwardRuleService.pageQueryForwardRule(page, pageSize, ruleLabel);
        return JsonResult.success(forwardRulePage);
    }



    @GetMapping("/{id}")
    public JsonResult<ForwardRule> queryPyId (@PathVariable String id){
        ForwardRule forwardRule = forwardRuleService.getById(id);
        return JsonResult.success(forwardRule);
    }


    @PostMapping
    public JsonResult<String> createForwardRule (@RequestBody @Valid ForwardRule forwardRule) {
        forwardRuleService.save(forwardRule);
        String id = forwardRule.getId();
        return JsonResult.success(id);
    }



    @PutMapping("/status")
    public JsonResult<Boolean> updateForwardRuleStatus (@RequestBody @Valid RuleStatusParam statusParam) throws BaseException {

        Boolean status = statusParam.getStatus();
        String forwardRuleId = statusParam.getForwardRuleId();
        ForwardRule forwardRule = forwardRuleService.getById(forwardRuleId);
        if (Objects.isNull(forwardRule)){
            throw new BaseException("转发规则不存在", BaseResultCode.GENERAL_ERROR);
        }
        if (Objects.equals(status,forwardRule.getRuleStatus())){
            throw new BaseException("转发规则状态与当前状态一致，不需要更改", BaseResultCode.GENERAL_ERROR);
        }
        if (status && forwardTargetService.countTargetByRuleId(forwardRuleId)<=0){
            throw new BaseException("当前规则没有设置转发目标，无法激活", BaseResultCode.GENERAL_ERROR);
        }
        Boolean updateRuleStatus = forwardRuleService.updateRuleStatus(forwardRuleId, status);
        return JsonResult.success(updateRuleStatus);
    }


    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeForwardRule (@PathVariable String id) throws BaseException {
        ForwardRule forwardRule = forwardRuleService.getById(id);
        if (Objects.isNull(forwardRule)){
            throw new BaseException("转发规则不存在", BaseResultCode.GENERAL_ERROR);
        }
        if (forwardRule.getRuleStatus()){
            throw new BaseException("转发规则正在运行，无法停止", BaseResultCode.GENERAL_ERROR);
        }
        boolean remove = forwardRuleService.removeById(forwardRule);
        return  JsonResult.success(remove);
    }






    @Autowired
    public void setForwardRuleService(IForwardRuleService forwardRuleService) {
        this.forwardRuleService = forwardRuleService;
    }

    @Autowired
    public void setForwardTargetService(IForwardTargetService forwardTargetService) {
        this.forwardTargetService = forwardTargetService;
    }
}
