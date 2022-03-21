package com.aibaixun.iotdm.controller;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.enums.*;
import com.aibaixun.iotdm.event.EntityChangeEvent;
import com.aibaixun.iotdm.server.ToDeviceProcessor;
import com.aibaixun.iotdm.service.IDeviceService;
import com.aibaixun.iotdm.service.IProductService;
import com.aibaixun.iotdm.data.*;
import com.aibaixun.iotdm.service.IotDmEventPublisher;
import com.aibaixun.iotdm.util.Base64Util;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.aibaixun.iotdm.constants.DataConstants.NULL_STR;

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

    private IotDmEventPublisher iotDmEventPublisher;

    private ToDeviceProcessor toDeviceProcessor;

    @GetMapping("/count")
    public JsonResult<Map<String,Long>> countDevice(@RequestParam(required = false)String productId) {
        List<KvData<Long>> kvDataList = deviceService.countDevice(productId);
        Map<String,Long> countMap = new HashMap<>(emptyMap);
        if (CollectionUtils.isEmpty(kvDataList)){
            return JsonResult.success(countMap);
        }
        long total = 0L;
        for (KvData<Long> longKvData : kvDataList) {
            String key = longKvData.getLabel();
            Long value = longKvData.getDataValue();
            if (null!=value){
                total+=value;
            }
            countMap.computeIfPresent(key,(k,v)-> null!=value?value:0L);
        }
        countMap.put("TOTAL",total);
        return JsonResult.success(countMap);
    }



    @GetMapping("/list")
    public JsonResult<List<DeviceEntity>> listQueryDevice(@RequestParam(required = false) String productId,
                                                          @RequestParam(required = false) Integer limit,
                                                          @RequestParam(required = false) String deviceLabel){
        if (Objects.isNull(limit)){
            limit = 50;
        }
        List<DeviceEntity> deviceEntities = deviceService.listQueryDevice(productId,limit,deviceLabel);
        return JsonResult.success(deviceEntities);
    }


    @GetMapping("/page")
    public JsonResult<Page<DeviceEntityInfo>> pageQueryDevice (@RequestParam(required = false) DeviceStatus deviceStatus,
                                                               @RequestParam Integer page,
                                                               @RequestParam Integer pageSize,
                                                               @RequestParam(required = false) String searchKey,
                                                               @RequestParam(required = false) String searchValue) throws BaseException {
        checkPage(page,pageSize);
        if (StringUtils.isNotBlank(searchKey) && !searchKeys.contains(searchKey)){
            throw new BaseException("下拉查询条件不满足,应该选择设备名称,设备标识码,设备id", BaseResultCode.BAD_PARAMS);
        }
        Page<DeviceEntityInfo> devicePage = deviceService.pageQueryDeviceInfos(page, pageSize, deviceStatus, searchKey, searchValue);
        return JsonResult.success(devicePage);
    }


    @GetMapping("/{id}")
    public JsonResult<DeviceEntityInfo> queryDeviceById(@PathVariable String id) throws BaseException {
        DeviceEntity deviceEntity = deviceService.queryById(id);
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备不存在，无法查询", BaseResultCode.GENERAL_ERROR);
        }
        String productId = deviceEntity.getProductId();
        ProductEntity productEntity = productService.getById(productId);
        return JsonResult.success(new DeviceEntityInfo(deviceEntity,Objects.nonNull(productEntity)? productEntity.getProductLabel():NULL_STR));
    }


    @PutMapping("/device-label")
    public JsonResult<Boolean> updateDeviceLabel (@RequestBody @Valid UpdateDeviceLabelParam updateDeviceLabelParam) throws BaseException {
        String deviceId = updateDeviceLabelParam.getDeviceId();
        DeviceEntity deviceEntity = deviceService.queryById(deviceId);
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备不存在，无法更改", BaseResultCode.GENERAL_ERROR);
        }
        String deviceLabel = updateDeviceLabelParam.getDeviceLabel();
        if (Objects.equals(deviceEntity.getDeviceLabel(),deviceLabel)){
            throw new BaseException("设备已经是当前名称，无法进行修改", BaseResultCode.GENERAL_ERROR);
        }
        Boolean aBoolean = deviceService.updateDeviceLabel(updateDeviceLabelParam.getDeviceId(), updateDeviceLabelParam.getDeviceLabel());
        return JsonResult.success(aBoolean);
    }


    @PutMapping("/device-status")
    public JsonResult<Boolean> updateDeviceStatus (@RequestBody @Valid UpdateDeviceStatusParam updateDeviceStatusParam) throws BaseException {
        String deviceId = updateDeviceStatusParam.getDeviceId();
        DeviceEntity deviceEntity = deviceService.queryById(deviceId);
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备不存在，无法更改", BaseResultCode.GENERAL_ERROR);
        }
        DeviceStatus deviceStatus = updateDeviceStatusParam.getDeviceStatus();
        if (Objects.equals(deviceEntity.getDeviceStatus(),deviceStatus)){
            throw new BaseException("设备已经是当前状态，无法进行修改", BaseResultCode.GENERAL_ERROR);
        }
        Boolean aBoolean = deviceService.updateDeviceStatus(updateDeviceStatusParam.getDeviceId(), updateDeviceStatusParam.getDeviceStatus());
        if (aBoolean && Objects.equals(DeviceStatus.ONLINE,deviceEntity.getDeviceStatus())){
            toDeviceProcessor.processCloseConnectDevice(deviceId,deviceEntity.getProductId());
        }
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
        String deviceCode = deviceParam.getDeviceCode();
        String productId = deviceParam.getProductId();
        checkProductId(productId);
        checkDeviceCode(deviceCode,productId);
        DeviceEntity saveDeviceEntity = new DeviceEntity();
        saveDeviceEntity.setDeviceCode(deviceCode);
        saveDeviceEntity.setDeviceLabel(deviceParam.getDeviceLabel());
        saveDeviceEntity.setDeviceSecret(deviceSecret);
        saveDeviceEntity.setAuthType(deviceParam.getAuthType());
        saveDeviceEntity.setProductId(productId);
        saveDeviceEntity.setNodeType(NodeType.GATEWAY);
        saveDeviceEntity.setInvented(false);
        saveDeviceEntity.setDeviceStatus(DeviceStatus.INACTIVE);
        boolean save = deviceService.save(saveDeviceEntity);
        if (save){
            iotDmEventPublisher.publishEntityChangeEvent(new EntityChangeEvent(SubjectResource.DEVICE, SubjectEvent.DEVICE_CREATE,UserInfoUtil.getTenantIdOfNull()));
        }
        return JsonResult.success(save);
    }



    @DeleteMapping("/{id}")
    public JsonResult<Boolean> removeDevice (@PathVariable String id) throws BaseException {
        DeviceEntity deviceEntity = deviceService.getById(id);
        if (Objects.isNull(deviceEntity)){
            throw new BaseException("设备不存在",BaseResultCode.GENERAL_ERROR);
        }
        if (!StringUtils.equals(deviceEntity.getCreator(), UserInfoUtil.getUserIdOfNull())){
            throw new BaseException("设备必须由创建人删除",BaseResultCode.GENERAL_ERROR);
        }
        boolean remove = deviceService.removeById(id);
        if (remove){
            iotDmEventPublisher.publishEntityChangeEvent(new EntityChangeEvent(SubjectResource.DEVICE, SubjectEvent.DEVICE_DELETE,UserInfoUtil.getTenantIdOfNull()));
        }
        if (Objects.equals(DeviceStatus.ONLINE,deviceEntity.getDeviceStatus())){
            toDeviceProcessor.processCloseConnectDevice(id,deviceEntity.getProductId());
        }
        return JsonResult.success(remove);
    }


    @DeleteMapping
    public JsonResult<Boolean> removeDevices(@RequestBody String [] ids ) throws BaseException {
        List<DeviceEntity> deviceEntities = deviceService.listByIds(Arrays.asList(ids));
        if (Objects.isNull(deviceEntities)){
            throw new BaseException("设备不存在",BaseResultCode.GENERAL_ERROR);
        }
        List<String> collectIds = deviceEntities.stream().filter(e -> StringUtils.equals(e.getCreator(), UserInfoUtil.getUserIdOfNull())).map(DeviceEntity::getId).collect(Collectors.toList());
        boolean batchRemove = deviceService.removeBatchByIds(collectIds);
        return JsonResult.successWithMsg("只能移除本人创建的设备，已经过滤了非本人创建的设备",batchRemove);
    }


    @PostMapping("sub-device")
    public JsonResult<Boolean> createSubDevice (@RequestBody @Valid SubDeviceParam subDeviceParam) throws BaseException {
        String gatewayId = subDeviceParam.getGatewayId();
        DeviceEntity byId = deviceService.getById(gatewayId);
        if (Objects.isNull(byId)){
            throw new BaseException("网关设备不存在",BaseResultCode.BAD_PARAMS);
        }

        String productId = subDeviceParam.getProductId();
        checkProductId(productId);
        String deviceCode = subDeviceParam.getDeviceCode();
        checkDeviceCode(deviceCode,productId);
        DeviceEntity saveDeviceEntity = new DeviceEntity();
        saveDeviceEntity.setDeviceCode(deviceCode);
        saveDeviceEntity.setDeviceLabel(subDeviceParam.getDeviceLabel());
        saveDeviceEntity.setDeviceSecret(RandomStringUtils.randomAlphanumeric(20));
        saveDeviceEntity.setAuthType(byId.getAuthType());
        saveDeviceEntity.setProductId(productId);
        saveDeviceEntity.setNodeType(NodeType.ENDPOINT);
        saveDeviceEntity.setInvented(false);
        saveDeviceEntity.setDeviceStatus(DeviceStatus.INACTIVE);
        saveDeviceEntity.setGatewayId(gatewayId);
        saveDeviceEntity.setDeleted(false);
        boolean save = deviceService.save(saveDeviceEntity);
        return JsonResult.success(save);
    }



    @GetMapping("/sub-device/page")
    public JsonResult<Page<DeviceEntityInfo>> pageQuerySubDevices(@RequestParam String gateWayId,
                                                                  @RequestParam Integer page,
                                                                  @RequestParam Integer pageSize) throws BaseException {
        checkPage(page,pageSize);
        Page<DeviceEntityInfo> subDeviceInfos = deviceService.pageQuerySubDeviceInfos(page, pageSize, gateWayId);
        return JsonResult.success(subDeviceInfos);
    }


    @PostMapping("/invented/{productId}")
    public JsonResult<Boolean> createInventedDevice(@PathVariable String productId) throws BaseException {
        String deviceSecret = RandomStringUtils.randomAlphanumeric(20);
        String deviceCode = RandomStringUtils.randomAlphanumeric(10);
        String deviceLabel = "invented"+RandomStringUtils.randomAlphanumeric(10);
        checkProductId(productId);
        checkDeviceCode(deviceCode,productId);
        DeviceEntity saveDeviceEntity = new DeviceEntity();
        saveDeviceEntity.setDeviceCode(deviceCode);
        saveDeviceEntity.setDeviceLabel(deviceLabel);
        saveDeviceEntity.setDeviceSecret(deviceSecret);
        saveDeviceEntity.setAuthType(DeviceAuthType.SECRET);
        saveDeviceEntity.setProductId(productId);
        saveDeviceEntity.setNodeType(NodeType.GATEWAY);
        saveDeviceEntity.setInvented(true);
        saveDeviceEntity.setDeviceStatus(DeviceStatus.ONLINE);
        boolean save = deviceService.save(saveDeviceEntity);
        return JsonResult.success(save);
    }

    private void checkProductId(String productId) throws BaseException {
        ProductEntity productEntity = productService.getById(productId);
        if (Objects.isNull(productEntity)){
            throw new BaseException("所属产品不存在",BaseResultCode.GENERAL_ERROR);
        }
    }

    private void checkDeviceCode (String deviceCode,String productId) throws BaseException {
        Long deviceNum = deviceService.countDeviceByDeviceCodeAndProductId(deviceCode, productId);
        if (deviceNum>0){
            throw new BaseException("产品下已经存在该标识码设备",BaseResultCode.GENERAL_ERROR);
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


    @Autowired
    public void setIotDmEventPublisher(IotDmEventPublisher iotDmEventPublisher) {
        this.iotDmEventPublisher = iotDmEventPublisher;
    }

    @Autowired
    public void setToDeviceProcessor(ToDeviceProcessor toDeviceProcessor) {
        this.toDeviceProcessor = toDeviceProcessor;
    }


}
