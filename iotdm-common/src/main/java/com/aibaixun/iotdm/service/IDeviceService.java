package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.Device;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.support.DeviceInfo;
import com.aibaixun.iotdm.support.KvData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface IDeviceService extends IService<Device> {


    /**
     * 通过id 查询 此查询主要是为了不把其他信息展示给前端
     * @param id id
     * @return Device
     */
    Device queryById(String id);

    /**
     * 统计设备状态
     * @param productId 产品id
     * @return 统计后的kv 数据
     */
    List<KvData<Long>> countDevice(String productId);


    /**
     * 查询设备列表
     * @param productId 产品id
     * @param limit 限制数量
     * @param deviceLabel 设备名称
     * @return 设备列表
     */
    List<Device> queryDevice(String productId,Integer limit,String deviceLabel);

    /**
     * 分页查询设备
     * @param page 页码
     * @param pageSize 页容
     * @param deviceStatus 设备状态
     * @param searchKey 查询key
     * @param searchValue 查询value
     * @return 设备分页
     */
    Page<DeviceInfo> pageQueryDeviceInfos(Integer page, Integer pageSize, DeviceStatus deviceStatus, String searchKey, String searchValue);


    /**
     * 更改设备名称
     * @param deviceId 设备id
     * @param deviceLabel 设备名称
     * @return 更改结果
     */
    Boolean updateDeviceLabel(String deviceId,String deviceLabel);


    /**
     * 查询网关子设备
     * @param page 页码
     * @param pageSize 页容
     * @param gateWayId 网关id
     * @return 设备信息
     */
    Page<DeviceInfo> pageQuerySubDeviceInfos(Integer page, Integer pageSize, String gateWayId);


    /**
     * 分组查询设备信息
     * @param page 页码
     * @param pageSize 页容
     * @param groupId 分组id
     * @param productId 产品id
     * @param deviceCode 设备标识码
     * @param deviceLabel 设备名称
     * @return 设备信息
     */
    Page<DeviceInfo> pageQueryDeviceByGroup(Integer page,Integer pageSize,String groupId,String productId,String deviceCode,String deviceLabel);

}
