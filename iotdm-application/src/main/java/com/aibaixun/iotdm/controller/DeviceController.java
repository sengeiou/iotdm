package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.Device;
import com.aibaixun.iotdm.entity.Product;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.support.KvData;
import com.aibaixun.iotdm.support.DeviceInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
    public JsonResult<List<Device>> listQueryDevice(@RequestParam(required = false) String productId){
        List<Device> devices = deviceService.queryDevice(productId);
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





    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
}
