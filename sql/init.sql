CREATE DATABASE IF NOT EXISTS `iotdm` DEFAULT CHARACTER SET utf8;
use iotdm;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_device
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
                            `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
                            `product_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品id',
                            `device_code` varchar(32) NOT NULL COMMENT '设备标识码',
                            `device_label` varchar(255) DEFAULT NULL COMMENT '设备名称',
                            `device_status` varchar(20) NOT NULL DEFAULT 'INACTIVE' COMMENT '设备状态',
                            `node_type` varchar(20) NOT NULL COMMENT '节点类型',
                            `description` varchar(200) DEFAULT NULL COMMENT '描述',
                            `gateway_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '网关id',
                            `auth_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '认证方式',
                            `device_secret` varchar(30) DEFAULT NULL COMMENT '设备密钥',
                            `last_connect_ts` bigint DEFAULT NULL COMMENT '最后连接时间',
                            `last_activity_ts` bigint DEFAULT NULL COMMENT '最后活跃时间',
                            `last_remote_address` varchar(100) DEFAULT NULL COMMENT '最后远程地址',
                            `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                            `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                            `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                            `tenant_id` varchar(20) DEFAULT NULL COMMENT '租户id',
                            `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
                            `invented` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否虚拟',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备表';

-- ----------------------------
-- Table structure for t_device_command_send
-- ----------------------------
DROP TABLE IF EXISTS `t_device_command_send`;
CREATE TABLE `t_device_command_send` (
                                         `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                         `device_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
                                         `command_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '命令id',
                                         `command_label` varchar(100) DEFAULT NULL COMMENT '命令名称',
                                         `params` varchar(255) DEFAULT NULL COMMENT '参数',
                                         `responses` varchar(255) DEFAULT NULL COMMENT '返回',
                                         `ts` bigint NOT NULL COMMENT '下发时间',
                                         `resp_ts` bigint DEFAULT NULL COMMENT '反馈时间',
                                         `req_id` int DEFAULT NULL COMMENT '请求id',
                                         `msg_id` int DEFAULT NULL COMMENT 'mqtt消息id',
                                         `send_status` varchar(20) DEFAULT NULL COMMENT '发送状态',
                                         `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                         `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                         `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                         `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='命令下发记录';

-- ----------------------------
-- Table structure for t_device_group
-- ----------------------------
DROP TABLE IF EXISTS `t_device_group`;
CREATE TABLE `t_device_group` (
                                  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分组id',
                                  `group_label` varchar(200) NOT NULL COMMENT '分组名称',
                                  `description` varchar(200) DEFAULT NULL COMMENT '描述',
                                  `super_group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '上级组id',
                                  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备分组';

-- ----------------------------
-- Table structure for t_device_group_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_device_group_relation`;
CREATE TABLE `t_device_group_relation` (
                                           `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                           `group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分组id',
                                           `device_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
                                           `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                           `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                           `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                           `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备分组';

-- ----------------------------
-- Table structure for t_device_message_report
-- ----------------------------
DROP TABLE IF EXISTS `t_device_message_report`;
CREATE TABLE `t_device_message_report` (
                                           `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                           `device_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
                                           `payload` varchar(255) DEFAULT NULL COMMENT '负载内容',
                                           `ts` bigint NOT NULL COMMENT '创建时间',
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `unique_device_message` (`device_id`) USING BTREE COMMENT '设备消息唯一键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备消息上报';

-- ----------------------------
-- Table structure for t_device_property_report
-- ----------------------------
DROP TABLE IF EXISTS `t_device_property_report`;
CREATE TABLE `t_device_property_report` (
                                            `device_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
                                            `property_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性id',
                                            `property_label` varchar(100) DEFAULT NULL,
                                            `property_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '属性值',
                                            `ts` bigint NOT NULL COMMENT '时间',
                                            UNIQUE KEY `unique_device_property` (`device_id`,`property_id`) USING BTREE COMMENT '设备属性唯一健'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备属性上报记录表 ';

-- ----------------------------
-- Table structure for t_forward_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_forward_rule`;
CREATE TABLE `t_forward_rule` (
                                  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                  `rule_label` varchar(255) NOT NULL COMMENT '规则名称',
                                  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
                                  `subject_resource` varchar(30) NOT NULL COMMENT '数据来源',
                                  `subject_event` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '触发事件',
                                  `rule_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '规则状态',
                                  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                  `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                  `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='转发规则';

-- ----------------------------
-- Table structure for t_forward_target
-- ----------------------------
DROP TABLE IF EXISTS `t_forward_target`;
CREATE TABLE `t_forward_target` (
                                    `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                    `forward_rule_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '转发规则id',
                                    `rule_resource_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '转发资源id',
                                    `configuration` json NOT NULL COMMENT '转发参数',
                                    `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                    `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                    `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                    `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='转发目标';

-- ----------------------------
-- Table structure for t_message_trace
-- ----------------------------
DROP TABLE IF EXISTS `t_message_trace`;
CREATE TABLE `t_message_trace` (
                                   `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                   `device_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备id',
                                   `business_type` varchar(20) NOT NULL COMMENT '业务类型',
                                   `business_step` varchar(255) NOT NULL COMMENT '业务步骤',
                                   `business_details` varchar(255) DEFAULT NULL COMMENT '业务详情',
                                   `message_status` tinyint(1) DEFAULT NULL COMMENT '消息状态',
                                   `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                   `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                   `creator` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                   `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   KEY `idx_device_trace_deviceid` (`device_id`) COMMENT '设备id索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息追踪';
-- ----------------------------
-- Table structure for t_model_command
-- ----------------------------
DROP TABLE IF EXISTS `t_model_command`;
CREATE TABLE `t_model_command` (
                                   `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                   `product_model_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品模型id',
                                   `command_label` varchar(20) NOT NULL COMMENT '命令名称，单个模型不唯一',
                                   `params` json DEFAULT NULL COMMENT '参数',
                                   `responses` json DEFAULT NULL COMMENT '返回结果',
                                   `description` varchar(50) DEFAULT NULL COMMENT '描述',
                                   `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                   `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                   `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                   `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='模型命令';

-- ----------------------------
-- Table structure for t_model_property
-- ----------------------------
DROP TABLE IF EXISTS `t_model_property`;
CREATE TABLE `t_model_property` (
                                    `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                    `product_model_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品模型id',
                                    `property_label` varchar(30) NOT NULL COMMENT '属性名称,在单个模型内唯一',
                                    `data_type` varchar(20) NOT NULL COMMENT '数据类型',
                                    `scope` varchar(4) NOT NULL COMMENT '访问权限',
                                    `max_value` decimal(10,2) DEFAULT NULL COMMENT '最大',
                                    `min_value` decimal(10,2) DEFAULT NULL COMMENT '最小值',
                                    `property_length` int DEFAULT NULL COMMENT '属性长度',
                                    `description` varchar(50) DEFAULT NULL COMMENT '描述',
                                    `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                    `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                    `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                    `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='模型属性';

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
                             `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品id',
                             `product_label` varchar(64) NOT NULL COMMENT '产品名称',
                             `protocol_type` varchar(20) NOT NULL COMMENT '协议类型',
                             `data_format` varchar(20) DEFAULT NULL COMMENT '数据格式',
                             `description` varchar(200) DEFAULT NULL COMMENT '描述',
                             `device_type` varchar(32) DEFAULT NULL COMMENT '设备类型',
                             `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                             `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                             `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                             `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                             `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品表';

-- ----------------------------
-- Table structure for t_product_model
-- ----------------------------
DROP TABLE IF EXISTS `t_product_model`;
CREATE TABLE `t_product_model` (
                                   `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品模型id',
                                   `product_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品id',
                                   `model_label` varchar(30) NOT NULL COMMENT '模型名称 默认使用产品id 方便后期扩展成多个模型',
                                   `model_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '模型类型',
                                   `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                   `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
                                   `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                   `creator` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                   `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                   `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品模型';

-- ----------------------------
-- Table structure for t_product_plugin_js
-- ----------------------------
DROP TABLE IF EXISTS `t_product_plugin_js`;
CREATE TABLE `t_product_plugin_js` (
                                       `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                       `product_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品id',
                                       `js_script_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT 'js脚本',
                                       `deployment` varchar(20) NOT NULL COMMENT '发布类型',
                                       `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                       `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                       `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                       `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品插件-js插件';

-- ----------------------------
-- Table structure for t_rule_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_rule_resource`;
CREATE TABLE `t_rule_resource` (
                                   `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                                   `resource_label` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源名称',
                                   `resource_type` varchar(20) NOT NULL COMMENT '资源类型',
                                   `configuration` json NOT NULL COMMENT '资源参数',
                                   `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                   `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                   `resource_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '资源状态',
                                   `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                   `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                   `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='规则资源';

-- ----------------------------
-- Table structure for t_device_config_send
-- ----------------------------
DROP TABLE IF EXISTS `t_device_config_send`;
CREATE TABLE `t_device_config_send` (
                                        `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                                        `device_id` varchar(32) NOT NULL COMMENT '设备id',
                                        `payload` text COMMENT '发送配置',
                                        `send_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发送状态',
                                        `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                        `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                        `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                        `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                        PRIMARY KEY (`id`,`device_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='配置发送表';


-- ----------------------------
-- Table structure for t_ota_package
-- ----------------------------
DROP TABLE IF EXISTS `t_ota_package`;
CREATE TABLE `t_ota_package` (
                                 `id` varchar(32) NOT NULL COMMENT 'id',
                                 `ota_package_type` varchar(20) NOT NULL COMMENT 'ota包类型',
                                 `ota_package_label` varchar(255) NOT NULL COMMENT 'ota包名称',
                                 `ota_package_version` varchar(50) NOT NULL COMMENT 'ota包版本',
                                 `file_url` varchar(255) DEFAULT NULL,
                                 `file_name` varchar(255) DEFAULT NULL,
                                 `content_type` varchar(255) DEFAULT NULL,
                                 `data` varbinary(500) DEFAULT NULL,
                                 `check_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                 `data_size` int DEFAULT NULL,
                                 `create_time` bigint DEFAULT NULL COMMENT '创建时间',
                                 `update_time` bigint DEFAULT NULL COMMENT '更新时间',
                                 `creator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                 `tenant_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '租户id',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='OTA package';


-- ----------------------------
-- event structure for timer_2clear_message_trace
-- ----------------------------
CREATE EVENT `iotdm`.`timer_2clear_message_trace`
  ON SCHEDULE
  EVERY '7' DAY
  COMMENT '定时清除 消息追踪表信息'
  DO TRUNCATE t_message_trace;
  ALTER EVENT `iotdm`.`timer_2clear_message_trace` ENABLE;

SET FOREIGN_KEY_CHECKS = 1;
