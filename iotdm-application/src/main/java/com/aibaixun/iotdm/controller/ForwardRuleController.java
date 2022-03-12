package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.common.redis.util.RedisRepository;
import com.aibaixun.iotdm.entity.ForwardRuleEntity;
import com.aibaixun.iotdm.service.IForwardRuleService;
import com.aibaixun.iotdm.service.IForwardTargetService;
import com.aibaixun.iotdm.data.RuleStatusParam;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static com.aibaixun.iotdm.constants.DataConstants.IOT_TENANT_FORWARD_KEY;

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

    private RedisRepository redisRepository;

    @GetMapping("/page")
    public JsonResult<Page<ForwardRuleEntity>> pageQueryForwardRule (@RequestParam Integer page,
                                                                     @RequestParam Integer pageSize,
                                                                     @RequestParam(required = false) String ruleLabel) {
        Page<ForwardRuleEntity> forwardRulePage = forwardRuleService.pageQueryForwardRule(page, pageSize, ruleLabel);
        return JsonResult.success(forwardRulePage);
    }



    @GetMapping("/{id}")
    public JsonResult<ForwardRuleEntity> queryPyId (@PathVariable String id){
        ForwardRuleEntity forwardRuleEntity = forwardRuleService.getById(id);
        return JsonResult.success(forwardRuleEntity);
    }


    @PostMapping
    public JsonResult<String> createForwardRule (@RequestBody @Valid ForwardRuleEntity forwardRuleEntity) {
        forwardRuleService.save(forwardRuleEntity);
        String id = forwardRuleEntity.getId();
        return JsonResult.success(id);
    }



    @PutMapping("/status")
    public JsonResult<Boolean> updateForwardRuleStatus (@RequestBody @Valid RuleStatusParam statusParam) throws BaseException {

        Boolean status = statusParam.getStatus();
        String forwardRuleId = statusParam.getForwardRuleId();
        ForwardRuleEntity forwardRuleEntity = forwardRuleService.getById(forwardRuleId);
        if (Objects.isNull(forwardRuleEntity)){
            throw new BaseException("转发规则不存在", BaseResultCode.GENERAL_ERROR);
        }
        if (Objects.equals(status, forwardRuleEntity.getRuleStatus())){
            throw new BaseException("转发规则状态与当前状态一致，不需要更改", BaseResultCode.GENERAL_ERROR);
        }
        if (status && forwardTargetService.countTargetByRuleId(forwardRuleId)<=0){
            throw new BaseException("当前规则没有设置转发目标，无法激活", BaseResultCode.GENERAL_ERROR);
        }
        redisRepository.delHashValues(IOT_TENANT_FORWARD_KEY, UserInfoUtil.getTenantIdOfNull());
        Boolean updateRuleStatus = forwardRuleService.updateRuleStatus(forwardRuleId, status);
        return JsonResult.success(updateRuleStatus);
    }


    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeForwardRule (@PathVariable String id) throws BaseException {
        ForwardRuleEntity forwardRuleEntity = forwardRuleService.getById(id);
        if (Objects.isNull(forwardRuleEntity)){
            throw new BaseException("转发规则不存在", BaseResultCode.GENERAL_ERROR);
        }
        if (forwardRuleEntity.getRuleStatus()){
            throw new BaseException("转发规则正在运行，无法停止", BaseResultCode.GENERAL_ERROR);
        }
        if (!StringUtils.equals(forwardRuleEntity.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("转发规则必须由创建人删除", BaseResultCode.GENERAL_ERROR);
        }
        redisRepository.delHashValues(IOT_TENANT_FORWARD_KEY, UserInfoUtil.getTenantIdOfNull());
        boolean remove = forwardRuleService.removeById(forwardRuleEntity);
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


    @Autowired
    public void setRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
}
