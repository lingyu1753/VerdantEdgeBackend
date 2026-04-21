/*
 Navicat Premium Dump SQL

 Source Server         : Tea-MYSQL95
 Source Server Type    : MySQL
 Source Server Version : 90500 (9.5.0)
 Source Host           : localhost:3306
 Source Schema         : verdant_edge

 Target Server Type    : MySQL
 Target Server Version : 90500 (9.5.0)
 File Encoding         : 65001

 Date: 16/04/2026 17:30:37
*/
USE `VerdantEdge`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
                             `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
                             `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
                             `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户手机号',
                             `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
                             `number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证号',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for machine
-- ----------------------------
DROP TABLE IF EXISTS `machine`;
CREATE TABLE `machine`  (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `employee_id` bigint NOT NULL COMMENT '员工id',
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备名称',
                            `number` int NOT NULL COMMENT '设备编号',
                            `plant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '植物名称',
                            `plant_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '植物类型',
                            `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `online` int DEFAULT 0 COMMENT '是否在线',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for machine_camera
-- ----------------------------
DROP TABLE IF EXISTS `machine_camera`;
CREATE TABLE `machine_camera`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `machine_id` bigint NULL DEFAULT NULL COMMENT '设备id',
                                   `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '摄影图像',
                                   `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '警告内容',
                                   `status` tinyint NOT NULL DEFAULT 0, -- 0正常 1病变
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '设备摄影表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for machine_record
-- ----------------------------
DROP TABLE IF EXISTS `machine_record`;
CREATE TABLE `machine_record` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `machine_id` bigint NOT NULL,
                                  `soil_humidity` decimal(5,2) NULL,
                                  `atmospheric_temperature` decimal(5,2) NULL,
                                  `atmospheric_humidity` decimal(5,2) NULL,
                                  `illuminance` decimal(10,2) NULL,
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
                          `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          `employee_id` BIGINT NOT NULL COMMENT '员工ID（所属用户）',
                          `time` DATETIME NOT NULL COMMENT '统计时间',
                          `type` TINYINT NOT NULL COMMENT '统计类型：0-小时，1-天',
                          `online_count` BIGINT NOT NULL DEFAULT 0 COMMENT '在线设备数量',
                          `normal_plant_count` BIGINT NULL COMMENT '正常植物数量',
                          `total_count` BIGINT NOT NULL DEFAULT 0 COMMENT '总设备数量',
                          `average_atmospheric_temperature` DECIMAL(5,2) NULL COMMENT '平均大气温度（℃）',
                          `average_atmospheric_humidity` DECIMAL(5,2) NULL COMMENT '平均大气湿度（%）',
                          `average_soil_humidity` DECIMAL(5,2) NULL COMMENT '平均土壤湿度（%）',
                          `average_illuminance` BIGINT NULL COMMENT '平均光照度（lux）',
                          PRIMARY KEY (`id`),
                          KEY `idx_employee_time` (`employee_id`, `time`),
                          KEY `idx_time` (`time`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '报表统计表';

SET FOREIGN_KEY_CHECKS = 1;