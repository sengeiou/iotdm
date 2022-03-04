package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.DeviceGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 设备分组 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IDeviceGroupService extends IService<DeviceGroup> {


    /**
     * 查询设备分组
     * @param limit 限制数量
     * @return 设备分组列表
     */
    List<DeviceGroup> listQueryDeviceGroup(Integer limit);


    /**
     * 统计子分组信息
     * @param superGroupId 上级分组id
     * @return 子分组信息
     */
    Long countSubGroup(String superGroupId);

}
