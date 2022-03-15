package com.aibaixun.iotdm.service.impl;

import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.enums.DeviceAuthType;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.mapper.DeviceMapper;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.data.DeviceEntityInfo;
import com.aibaixun.iotdm.data.KvData;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.aibaixun.toolkit.coomon.exception.BaiXunException;
import com.aibaixun.toolkit.coomon.result.BaseResultCode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, DeviceEntity> implements IDeviceService {


    @Override
    public DeviceEntity queryById(String id) {
        DeviceEntity deviceEntity = getById(id);
        deviceEntity.setTenantId(null);
        deviceEntity.setLastConnectTs(null);
        deviceEntity.setCreator(null);
        return deviceEntity;
    }

    @Override
    public List<KvData<Long>> countDevice(String productId) {
        String tenantId = UserInfoUtil.getTenantIdOfNull();
        return baseMapper.countDevice(productId, tenantId);
    }


    @Override
    public List<DeviceEntity> listQueryDevice(String productId, Integer limit, String deviceLabel) {
        LambdaQueryWrapper<DeviceEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(DeviceEntity::getId, DeviceEntity::getDeviceLabel, DeviceEntity::getDeviceCode, DeviceEntity::getDeviceStatus, DeviceEntity::getInvented);
        queryWrapper.eq(DeviceEntity::getTenantId,UserInfoUtil.getTenantIdOfNull());
        if (StringUtils.isNotBlank(productId)){
            queryWrapper.eq(DeviceEntity::getProductId,productId);
        }
        if (StringUtils.isNotBlank(deviceLabel)){
            queryWrapper.likeRight(DeviceEntity::getDeviceLabel,deviceLabel);
        }
        queryWrapper.orderByDesc(DeviceEntity::getCreateTime);
        queryWrapper.last(" LIMIT "+limit);
        return list(queryWrapper);
    }

    @Override
    public Page<DeviceEntityInfo> pageQueryDeviceInfos(Integer page, Integer pageSize, DeviceStatus deviceStatus, String searchKey, String searchValue) {
        return baseMapper.selectPageDeviceInfo(Page.of(page,pageSize),UserInfoUtil.getTenantIdOfNull(),deviceStatus,searchKey,searchValue);
    }


    @Override
    public Boolean updateDeviceLabel(String deviceId, String deviceLabel) throws BaiXunException {
        DeviceEntity deviceEntity = getById(deviceId);
        if (Objects.isNull(deviceEntity)){
            throw new BaiXunException("设备不存在无法更改", BaseResultCode.GENERAL_ERROR);
        }
        LambdaUpdateWrapper<DeviceEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(DeviceEntity::getDeviceLabel,deviceLabel);
        updateWrapper.eq(DeviceEntity::getId,deviceId);
        return update(updateWrapper);
    }


    @Override
    public Boolean updateDeviceStatus(String deviceId, DeviceStatus deviceStatus) throws BaiXunException {
        DeviceEntity deviceEntity = getById(deviceId);
        if (Objects.isNull(deviceEntity)){
            throw new BaiXunException("设备不存在无法更改", BaseResultCode.GENERAL_ERROR);
        }
        if (Objects.equals(deviceEntity.getDeviceStatus(),deviceStatus)){
            throw new BaiXunException("设备一致在无法更改", BaseResultCode.GENERAL_ERROR);
        }
        LambdaUpdateWrapper<DeviceEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(DeviceEntity::getDeviceStatus,deviceStatus);
        updateWrapper.eq(DeviceEntity::getId,deviceId);
        return update(updateWrapper);
    }

    @Override
    public Page<DeviceEntityInfo> pageQuerySubDeviceInfos(Integer page, Integer pageSize, String gateWayId) {
        return baseMapper.selectPageSubDeviceInfo(Page.of(page,pageSize),gateWayId);
    }


    @Override
    public Page<DeviceEntityInfo> pageQueryDeviceByGroup(Integer page, Integer pageSize, String groupId, String productId, String deviceCode, String deviceLabel) {
        String tenantId = UserInfoUtil.getTenantIdOfNull();
        return baseMapper.selectPageDeviceInfoByGroup(Page.of(page,pageSize),tenantId,productId,groupId,deviceCode,deviceLabel);
    }


    @Override
    public Long countDeviceByDeviceCodeAndProductId(String deviceCode, String productId) {
        return count(Wrappers.<DeviceEntity>lambdaQuery().eq(DeviceEntity::getDeviceCode,deviceCode).eq(DeviceEntity::getProductId,productId));
    }

    @Override
    public DeviceEntity queryBy3Param(String clientId, String username, String password) {
        LambdaQueryWrapper<DeviceEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DeviceEntity::getId,clientId)
                .eq(DeviceEntity::getDeviceCode,username)
                .eq(DeviceEntity::getDeviceSecret,password)
                .eq(DeviceEntity::getAuthType, DeviceAuthType.SECRET);
        return getOne(queryWrapper, false);
    }


    @Override
    public Boolean updateDeviceStatus(String id, DeviceStatus targetStatus, Long lastConnect, Long lastActivity, String host) {
        if (StringUtils.isEmpty(id)){
            return false;
        }
        LambdaUpdateWrapper<DeviceEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(DeviceEntity::getId,id).set(DeviceEntity::getDeviceStatus,targetStatus);
        if (Objects.nonNull(lastActivity)){
            updateWrapper.set(DeviceEntity::getLastActivityTs,lastActivity);
        }
        if (Objects.nonNull(lastConnect)){
            updateWrapper.set(DeviceEntity::getLastConnectTs,lastConnect);
        }
        if (Objects.nonNull(host)){
            updateWrapper.set(DeviceEntity::getLastRemoteAddress,host);
        }
        return update(updateWrapper);
    }

}
