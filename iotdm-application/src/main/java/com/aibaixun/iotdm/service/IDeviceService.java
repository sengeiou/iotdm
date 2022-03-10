package com.aibaixun.iotdm.service;

import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.data.DeviceEntityInfo;
import com.aibaixun.iotdm.data.KvData;
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
public interface IDeviceService extends IService<DeviceEntity> {


    /**
     * 通过id 查询 此查询主要是为了不把其他信息展示给前端
     * @param id id
     * @return Device
     */
    DeviceEntity queryById(String id);

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
    List<DeviceEntity> queryDevice(String productId, Integer limit, String deviceLabel);

    /**
     * 分页查询设备
     * @param page 页码
     * @param pageSize 页容
     * @param deviceStatus 设备状态
     * @param searchKey 查询key
     * @param searchValue 查询value
     * @return 设备分页
     */
    Page<DeviceEntityInfo> pageQueryDeviceInfos(Integer page, Integer pageSize, DeviceStatus deviceStatus, String searchKey, String searchValue);


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
    Page<DeviceEntityInfo> pageQuerySubDeviceInfos(Integer page, Integer pageSize, String gateWayId);


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
    Page<DeviceEntityInfo> pageQueryDeviceByGroup(Integer page, Integer pageSize, String groupId, String productId, String deviceCode, String deviceLabel);


    /**
     * 统计产品下 设备标识码是否存在
     * @param deviceCode 设备标识码
     * @param productId 产品id
     * @return 数目
     */
    Long countDeviceByDeviceCodeAndProductId(String deviceCode,String productId);


    /**
     * 通过三元素查询设备
     * @param clientId 客户端id
     * @param username 用户名
     * @param password 密码
     * @return 设备实体
     */
    DeviceEntity queryBy3Param(String clientId,String username,String password);


    /**
     * 修改设备状态
     * @param id 设备id
     * @param targetStatus 目标状态
     * @param lastConnect 连接之间
     * @param lastActivity 活跃时间
     * @param host 地址
     * @return 修改结果
     */
    Boolean updateDeviceStatus(String id,DeviceStatus targetStatus,Long lastConnect,Long lastActivity,String host);




}
