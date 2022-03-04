package com.aibaixun.iotdm.mapper;

import com.aibaixun.iotdm.entity.Device;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.support.DeviceInfo;
import com.aibaixun.iotdm.support.KvData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author baixun
 * @since 2022-03-03
 */
public interface DeviceMapper extends BaseMapper<Device> {

    /**
     * 统计
     * @param productId 产品id
     * @param tenantId 租户id
     * @return 设备统计信息
     */
    List<KvData<Long>> countDevice(@Param("productId") String productId,@Param("tenantId") String tenantId);


    /**
     * 查询设备列表
     * @param page 分页信息
     * @param tenantId 租户id
     * @param deviceStatus 设备状态
     * @param searchKey 查询key
     * @param searchValue 查询条件
     * @return 设备分页信息
     */
    Page<DeviceInfo> selectPageDeviceInfo(Page<DeviceInfo> page, String tenantId,DeviceStatus deviceStatus,String searchKey,String searchValue);


    /**
     * 查询网关子设备
     * @param page 分页信息
     * @param gateWayId 网关id
     * @return 设备信息
     */
    Page<DeviceInfo> selectPageSubDeviceInfo(Page<DeviceInfo> page, String gateWayId);


    /**
     * 查询群组设备
     * @param page 分页
     * @param tenantId 租户id
     * @param productId 产品id
     * @param groupId 分组信息
     * @param deviceCode 设备标识码
     * @param deviceLabel 设备名称
     * @return 设备信息
     */
    Page<DeviceInfo> selectPageDeviceInfoByGroup(Page<DeviceInfo> page, String tenantId,String productId,String groupId,String deviceCode,String deviceLabel);


}
