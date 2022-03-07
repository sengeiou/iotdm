package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.RuleResource;
import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.service.IForwardTargetService;
import com.aibaixun.iotdm.service.IRuleResourceService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 规则资源 web api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/7
 */
@RestController
@RequestMapping("rule-resource")
public class RuleResourceController extends BaseController{



    private IRuleResourceService ruleResourceService;


    private IForwardTargetService forwardTargetService;

    @GetMapping("/page")
    public JsonResult<Page<RuleResource>> pageQueryRuleResource (@RequestParam Integer page,
                                                                 @RequestParam Integer pageSize,
                                                                 @RequestParam(required = false)ResourceType resourceType) throws BaseException {

        checkPage(page,pageSize);
        Page<RuleResource> ruleResourcePage = ruleResourceService.pageQueryRuleResource(page, pageSize, resourceType);
        return JsonResult.success(ruleResourcePage);
    }


    @PostMapping
    public JsonResult<Boolean> createRuleResource (@RequestBody @Valid RuleResource ruleResource) {
        boolean save = ruleResourceService.save(ruleResource);
        return JsonResult.success(save);
    }


    @PutMapping
    public JsonResult<Boolean> updateRuleResource (@RequestBody @Valid RuleResource ruleResource) throws BaseException {
        String id = ruleResource.getId();
        if (StringUtils.isBlank(id)){
            throw new BaseException("", BaseResultCode.BAD_PARAMS);
        }
        boolean update = ruleResourceService.updateById(ruleResource);
        return JsonResult.success(update);
    }


    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeRuleResource (@PathVariable String id) throws BaseException {
        Long resourceUseNum = forwardTargetService.countTargetByResourceId(id);
        if (resourceUseNum > 0){
            throw new BaseException("",BaseResultCode.GENERAL_ERROR);
        }
        boolean remove = ruleResourceService.removeById(id);
        return JsonResult.success(remove);
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
