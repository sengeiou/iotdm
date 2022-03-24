package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.data.UpdateRuleResourceStatusParam;
import com.aibaixun.iotdm.entity.RuleResourceEntity;
import com.aibaixun.iotdm.enums.ResourceType;
import com.aibaixun.iotdm.service.IForwardTargetService;
import com.aibaixun.iotdm.service.IRuleResourceService;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
    public JsonResult<Page<RuleResourceEntity>> pageQueryRuleResourceEntity (@RequestParam Integer page,
                                                                 @RequestParam Integer pageSize,
                                                                 @RequestParam(required = false)String resourceLabel) throws BaseException {
        checkPage(page,pageSize);
        Page<RuleResourceEntity> ruleResourceEntityPage = ruleResourceService.pageQueryRuleResource(page, pageSize, resourceLabel);
        return JsonResult.success(ruleResourceEntityPage);
    }



    @GetMapping("/list")
    public JsonResult<List<RuleResourceEntity>> listQueryRuleResourceEntity (@RequestParam(required = false) Integer limit,
                                                                 @RequestParam(required = false) String resourceLabel,
                                                                 @RequestParam(required = false) ResourceType resourceType) {
        if (Objects.isNull(limit)){
            limit =50;
        }
        List<RuleResourceEntity> ruleResourceEntities = ruleResourceService.listQueryRuleResource(limit, resourceLabel,resourceType);
        return JsonResult.success(ruleResourceEntities);
    }


    @PostMapping
    public JsonResult<Boolean> createRuleResourceEntity (@RequestBody @Valid RuleResourceEntity ruleResourceEntity) throws BaseException {


        checkEntityParam(ruleResourceEntity.getConfiguration(),"资源配置项不允许为空");
        if (Objects.isNull(ruleResourceEntity.getResourceType())){
            ruleResourceEntity.setResourceType(ruleResourceEntity.getConfiguration().getResourceType());
        }
        ruleResourceEntity.setResourceStatus(false);
        boolean save = ruleResourceService.save(ruleResourceEntity);
        return JsonResult.success(save);
    }


    @PutMapping
    public JsonResult<Boolean> updateRuleResourceEntity (@RequestBody @Valid RuleResourceEntity ruleResourceEntity) throws BaseException {
        String id = ruleResourceEntity.getId();
        checkParameterValue(id,"资源id不允许为空");
        Long resourceUseNum = forwardTargetService.countTargetByResourceId(id);
        if (resourceUseNum > 0){
            throw new BaseException("资源正在被使用，无法修改",BaseResultCode.GENERAL_ERROR);
        }
        boolean update = ruleResourceService.updateById(ruleResourceEntity);
        return JsonResult.success(update);
    }


    @PutMapping("/resource-status")
    public JsonResult<Boolean> updateRuleResourceStatus (@RequestBody @Valid UpdateRuleResourceStatusParam updateRuleResourceStatusParam) throws BaseException {
        String id = updateRuleResourceStatusParam.getResourceId();
        RuleResourceEntity ruleResourceEntity = ruleResourceService.getById(id);
        checkEntity(ruleResourceEntity,"资源不存在，无法更改");
        Long resourceUseNum = forwardTargetService.countTargetByResourceId(id);
        if (resourceUseNum > 0){
            throw new BaseException("资源正在被使用，无法修改",BaseResultCode.GENERAL_ERROR);
        }
        if (Objects.equals(ruleResourceEntity.getResourceStatus(),updateRuleResourceStatusParam.getResourceStatus())){
            throw new BaseException("资源状态与当前状态一致，无法修改",BaseResultCode.GENERAL_ERROR);
        }
        boolean update = ruleResourceService.updateResourceStatus(id,updateRuleResourceStatusParam.getResourceStatus());
        return JsonResult.success(update);
    }

    @GetMapping("/{id}")
    public JsonResult<RuleResourceEntity> removeRuleResourceEntity (@PathVariable String id) {
        RuleResourceEntity ruleResourceEntity = ruleResourceService.getById(id);
        return JsonResult.success(ruleResourceEntity);
    }



    @DeleteMapping("/{id}")
    public JsonResult<Boolean> getRuleResourceEntity (@PathVariable String id) throws BaseException {

        RuleResourceEntity ruleResourceEntity = ruleResourceService.getById(id);
        checkEntity(ruleResourceEntity,"资源不存在，无法移除");
        Long resourceUseNum = forwardTargetService.countTargetByResourceId(id);
        if (resourceUseNum > 0){
            throw new BaseException("资源正在被使用，无法删除",BaseResultCode.GENERAL_ERROR);
        }
        if(!StringUtils.equals(ruleResourceEntity.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("资源只能由创建人删除",BaseResultCode.GENERAL_ERROR);
        }
        boolean remove = ruleResourceService.removeById(id);
        return JsonResult.success(remove);
    }

    @GetMapping("/count")
    public JsonResult<Long> countResource() {
        Long aLong = ruleResourceService.countResource();
        return JsonResult.success(aLong);
    }


    @Autowired
    public void setForwardTargetService(IForwardTargetService forwardTargetService) {
        this.forwardTargetService = forwardTargetService;
    }

    @Autowired
    public void setRuleResourceService(IRuleResourceService ruleResourceService) {
        this.ruleResourceService = ruleResourceService;
    }
}
