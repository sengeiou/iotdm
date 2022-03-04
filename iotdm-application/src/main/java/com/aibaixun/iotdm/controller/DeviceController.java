package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.Device;
import com.aibaixun.iotdm.entity.Product;
import com.aibaixun.iotdm.enums.DeviceAuthType;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.enums.NodeType;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.support.*;
import com.aibaixun.iotdm.util.Base64Util;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static com.aibaixun.iotdm.Constants.NULL_STR;

/**
 * 设备 Web Api
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@RestController
@RequestMapping("/device")
public class DeviceController extends BaseController{

    private final Map<String,Long> emptyMap = Map.of("TOTAL", 0L, "OFFLINE", 0L, "ONLINE", 0L, "INACTIVE", 0L, "WARN", 0L);

    private final Set<String> searchKeys = Set.of("deviceLabel","deviceCode","id");


    private IDeviceService deviceService;

    private IProductService productService;

    @GetMapping("/count")
    public JsonResult<Map<String,Long>> countDevice(@RequestParam(required = false)String productId) {
        List<KvData<Long>> kvDataList = deviceService.countDevice(productId);
        Map<String,Long> countMap = new HashMap<>(emptyMap);
        if (CollectionUtils.isEmpty(kvDataList)){
            return JsonResult.success(countMap);
        }
        long total = 0L;
        for (KvData<Long> longKvData : kvDataList) {
            String key = longKvData.getKey();
            Long value = longKvData.getValue();
            if (null!=value){
                total+=value;
            }
            countMap.computeIfPresent(key,(k,v)-> null!=value?value:0L);
        }
        countMap.put("TOTAL",total);
        return JsonResult.success(countMap);
    }



    @GetMapping("/list")
    public JsonResult<List<Device>> listQueryDevice(@RequestParam(required = false) String productId,@RequestParam Integer limit){
        List<Device> devices = deviceService.queryDevice(productId,limit);
        return JsonResult.success(devices);
    }


    @GetMapping("/page")
    public JsonResult<Page<DeviceInfo>> pageQueryDevice (@RequestParam(required = false) DeviceStatus deviceStatus,
                                                         @RequestParam Integer page,
                                                         @RequestParam Integer pageSize,
                                                         @RequestParam(required = false) String searchKey,
                                                         @RequestParam(required = false) String searchValue) throws BaseException {
        checkPage(page,pageSize);
        if (StringUtils.isNotBlank(searchKey) && !searchKeys.contains(searchKey)){
            throw new BaseException("下拉查询条件不满足,应该选择设备名称,设备标识码,设备id", BaseResultCode.BAD_PARAMS);
        }
        Page<DeviceInfo> devicePage = deviceService.pageQueryDeviceInfos(page, pageSize, deviceStatus, searchKey, searchValue);
        return JsonResult.success(devicePage);
    }


    @GetMapping("/{id}")
    public JsonResult<DeviceInfo> queryDeviceById(@PathVariable String id){
        Device device = deviceService.queryById(id);
        if (Objects.isNull(device)){
            return JsonResult.success(null);
        }
        String productId = device.getProductId();
        Product product = productService.getById(productId);
        return JsonResult.success(new DeviceInfo(device,Objects.nonNull(product)?product.getProductLabel():NULL_STR));
    }


    @PutMapping("/device-label")
    public JsonResult<Boolean> updateDeviceLabel (@RequestBody @Valid UpdateDeviceLabelParam updateDeviceLabelParam) {
        Boolean aBoolean = deviceService.updateDeviceLabel(updateDeviceLabelParam.getDeviceId(), updateDeviceLabelParam.getDeviceLabel());
        return JsonResult.success(aBoolean);
    }




    @PostMapping
    public JsonResult<Boolean> createDevice (@RequestBody @Valid DeviceParam deviceParam) throws BaseException {

        String deviceSecret = deviceParam.getDeviceSecret();
        boolean secretBlank = StringUtils.isNotBlank(deviceSecret);
        if ( !secretBlank && StringUtils.equals(deviceSecret,deviceParam.getConfirmSecret())){
            throw new BaseException("设备密钥不一致",BaseResultCode.BAD_PARAMS);
        }
        if (deviceParam.getAuthType().equals(DeviceAuthType.SECRET)){
            deviceSecret = secretBlank? RandomStringUtils.randomAlphanumeric(20): Base64Util.decode(deviceSecret);
        }
        String productId = deviceParam.getProductId();
        checkProductId(productId);
        Device saveDevice = new Device();
        saveDevice.setDeviceCode(deviceParam.getDeviceCode());
        saveDevice.setDeviceLabel(deviceParam.getDeviceLabel());
        saveDevice.setDeviceSecret(deviceSecret);
        saveDevice.setAuthType(deviceParam.getAuthType());
        saveDevice.setProductId(productId);
        saveDevice.setNodeType(NodeType.GATEWAY);
        saveDevice.setVirtual(false);
        saveDevice.setDeviceStatus(DeviceStatus.INACTIVE);
        deviceService.save(saveDevice);
        return null;
    }



    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeDevice (@PathVariable String id){
        boolean remove = deviceService.removeById(id);
        return JsonResult.success(remove);
    }


    @PostMapping("sub-device")
    public JsonResult<Boolean> createSubDevice (@RequestParam @Valid SubDeviceParam subDeviceParam) throws BaseException {
        String gatewayId = subDeviceParam.getGatewayId();
        Device byId = deviceService.getById(gatewayId);
        if (Objects.isNull(byId)){
            throw new BaseException("网关设备不存在",BaseResultCode.BAD_PARAMS);
        }

        String productId = subDeviceParam.getProductId();
        checkProductId(productId);
        Device saveDevice = new Device();
        saveDevice.setDeviceCode(subDeviceParam.getDeviceCode());
        saveDevice.setDeviceLabel(subDeviceParam.getDeviceLabel());
        saveDevice.setDeviceSecret(RandomStringUtils.randomAlphanumeric(20));
        saveDevice.setAuthType(byId.getAuthType());
        saveDevice.setProductId(productId);
        saveDevice.setNodeType(NodeType.ENDPOINT);
        saveDevice.setVirtual(false);
        saveDevice.setDeviceStatus(DeviceStatus.INACTIVE);
        saveDevice.setGatewayId(gatewayId);
        boolean save = deviceService.save(saveDevice);
        return JsonResult.success(save);
    }



    @GetMapping("/sub-device/page")
    public JsonResult<Page<DeviceInfo>> pageQuerySubDevices(@RequestParam String gateWayId,
                                                            @RequestParam Integer page,
                                                            @RequestParam Integer pageSize) throws BaseException {
        checkPage(page,pageSize);
        Page<DeviceInfo> subDeviceInfos = deviceService.pageQuerySubDeviceInfos(page, pageSize, gateWayId);
        return JsonResult.success(subDeviceInfos);
    }



    private void checkProductId(String productId) throws BaseException {
        Product product = productService.getById(productId);
        if (Objects.isNull(product)){
            throw new BaseException("所属产品不存在",BaseResultCode.BAD_PARAMS);
        }
    }


    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
}
