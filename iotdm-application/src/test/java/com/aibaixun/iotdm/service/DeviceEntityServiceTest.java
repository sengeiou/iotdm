package com.aibaixun.iotdm.service;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.iotdm.IotDmApplication;
import com.aibaixun.iotdm.data.DeviceEntityInfo;
import com.aibaixun.iotdm.data.DeviceParam;
import com.aibaixun.iotdm.data.SubDeviceParam;
import com.aibaixun.iotdm.entity.DeviceEntity;
import com.aibaixun.iotdm.entity.ProductEntity;
import com.aibaixun.iotdm.enums.DeviceAuthType;
import com.aibaixun.iotdm.enums.DeviceStatus;
import com.aibaixun.iotdm.enums.NodeType;
import com.aibaixun.iotdm.util.Base64Util;
import com.aibaixun.iotdm.util.UserInfoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 设备单元测试
 * @author wangxiao@aibaixun.com
 * @date 2022/3/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IotDmApplication.class)
public class DeviceEntityServiceTest extends BaseTest{


    private IDeviceService deviceService;


    private IProductService productService;



    private final Set<String> searchKeys = Set.of("deviceLabel","deviceCode","id");

    private final Logger logger = LoggerFactory.getLogger(DeviceEntityServiceTest.class);

    @Autowired
    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @Test
    public void  testCreateDevice () throws BaseException {

        DeviceParam deviceParam = new DeviceParam();
        deviceParam.setDeviceCode("q1");
        deviceParam.setDeviceSecret(Base64Util.encode("mysql"));
        deviceParam.setAuthType(DeviceAuthType.SECRET);
        deviceParam.setProductId("37985ac6adb627e2e0d8e73e23055bd8");
        deviceParam.setConfirmSecret(Base64Util.encode("mysql"));

        String deviceSecret = deviceParam.getDeviceSecret();
        boolean secretBlank = StringUtils.isNotBlank(deviceSecret);
        if ( !secretBlank && StringUtils.equals(deviceSecret,deviceParam.getConfirmSecret())){
            throw new BaseException("设备密钥不一致", BaseResultCode.BAD_PARAMS);
        }
        if (deviceParam.getAuthType().equals(DeviceAuthType.SECRET)){
            deviceSecret = secretBlank? RandomStringUtils.randomAlphanumeric(20): Base64Util.decode(deviceSecret);
        }
        String productId = deviceParam.getProductId();
        checkProductId(productId);
        DeviceEntity saveDeviceEntity = new DeviceEntity();
        saveDeviceEntity.setDeviceCode(deviceParam.getDeviceCode());
        saveDeviceEntity.setDeviceLabel(deviceParam.getDeviceLabel());
        saveDeviceEntity.setDeviceSecret(deviceSecret);
        saveDeviceEntity.setAuthType(deviceParam.getAuthType());
        saveDeviceEntity.setProductId(productId);
        saveDeviceEntity.setNodeType(NodeType.GATEWAY);
        saveDeviceEntity.setInvented(false);
        saveDeviceEntity.setDeviceStatus(DeviceStatus.INACTIVE);
        deviceService.save(saveDeviceEntity);
    }

    private void checkProductId(String productId) throws BaseException {
        ProductEntity productEntity = productService.getById(productId);
        if (Objects.isNull(productEntity)){
            throw new BaseException("所属产品不存在",BaseResultCode.BAD_PARAMS);
        }
    }


    @Autowired
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }


    @Test
    public void testPageQueryDevice () throws BaseException {
        int page = 0;
        int pageSize =20;
        checkPage(page,pageSize);
        String searchKey = "deviceLabel";
        String searchValue = "";
        DeviceStatus deviceStatus = null;
        if (StringUtils.isNotBlank(searchKey) && !searchKeys.contains(searchKey)){
            throw new BaseException("下拉查询条件不满足,应该选择设备名称,设备标识码,设备id", BaseResultCode.BAD_PARAMS);
        }
        Page<DeviceEntityInfo> devicePage = deviceService.pageQueryDeviceInfos(page, pageSize, deviceStatus, searchKey, searchValue);
        logger.info(String.valueOf(devicePage.getPages()));
    }



    @Test
    public void testListQueryDevice () throws BaseException {
        Integer limit = null;
        if (Objects.isNull(limit)){
            limit = 50;
        }
        List<DeviceEntity> deviceEntities = deviceService.queryDevice("37985ac6adb627e2e0d8e73e23055bd8",limit,null);
        logger.info(deviceEntities.toString());
    }


    @Test
    public void  testRemoveDevices() throws BaseException {
        String [] ids = new String[]{"b04dffecc27381005af47283536a12e3","123"};
        List<DeviceEntity> deviceEntities = deviceService.listByIds(Arrays.asList(ids));
        if (Objects.isNull(deviceEntities)){
            throw new BaseException("设备不存在",BaseResultCode.GENERAL_ERROR);
        }
        List<String> collectIds = deviceEntities.stream().filter(e -> StringUtils.equals(e.getCreator(), UserInfoUtil.getUserIdOfNull())).map(DeviceEntity::getId).collect(Collectors.toList());
        boolean batchRemove = deviceService.removeBatchByIds(collectIds);
        logger.info(String.valueOf(batchRemove));
    }



    @Test
    public void testSubDevice () throws BaseException {
        SubDeviceParam subDeviceParam = new SubDeviceParam();
        subDeviceParam.setDeviceCode("aa");
        subDeviceParam.setDeviceLabel("aa");
        subDeviceParam.setProductId("37985ac6adb627e2e0d8e73e23055bd8");
        subDeviceParam.setGatewayId("b04dffecc27381005af47283536a12e3");
        String gatewayId = subDeviceParam.getGatewayId();
        DeviceEntity byId = deviceService.getById(gatewayId);
        if (Objects.isNull(byId)){
            throw new BaseException("网关设备不存在",BaseResultCode.BAD_PARAMS);
        }

        String productId = subDeviceParam.getProductId();
        checkProductId(productId);
        DeviceEntity saveDeviceEntity = new DeviceEntity();
        saveDeviceEntity.setDeviceCode(subDeviceParam.getDeviceCode());
        saveDeviceEntity.setDeviceLabel(subDeviceParam.getDeviceLabel());
        saveDeviceEntity.setDeviceSecret(RandomStringUtils.randomAlphanumeric(20));
        saveDeviceEntity.setAuthType(byId.getAuthType());
        saveDeviceEntity.setProductId(productId);
        saveDeviceEntity.setNodeType(NodeType.ENDPOINT);
        saveDeviceEntity.setInvented(false);
        saveDeviceEntity.setDeviceStatus(DeviceStatus.INACTIVE);
        saveDeviceEntity.setGatewayId(gatewayId);
        boolean save = deviceService.save(saveDeviceEntity);
        logger.info(String.valueOf(save));
    }


    @Test
    public void testQuerySubDevice () throws BaseException {
        int page =1,pageSize =10;
        checkPage(page,pageSize);
        Page<DeviceEntityInfo> subDeviceInfos = deviceService.pageQuerySubDeviceInfos(page, pageSize, "b04dffecc27381005af47283536a12e3");
        logger.info(String.valueOf(subDeviceInfos));
    }
}
