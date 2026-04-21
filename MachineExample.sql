-- 员工信息（不变）
INSERT INTO `employee` (`id`, `name`, `password`, `email`, `phone`, `real_name`, `number`)
VALUES (1, 'admin', '123456', '2975194966@qq.com', '18025447710', '予梦玲羽', '1');

-- 插入20条设备示例数据（不变）
INSERT INTO `machine` (`id`, `employee_id`, `name`, `number`, `plant_name`, `plant_type`)
VALUES (1, 1, '温室传感器A1', 1001, '番茄', '茄科'),
       (2, 1, '智能灌溉器B2', 1002, '黄瓜', '葫芦科'),
       (3, 1, '光照监测仪C3', 1003, '玫瑰', '蔷薇科'),
       (4, 1, '土壤湿度计D4', 1004, '辣椒', '茄科'),
       (5, 1, 'CO2浓度计E5', 1005, '辣椒', '茄科'),
       (6, 1, '自动卷帘机F6', 1006, '草莓', '蔷薇科'),
       (7, 1, '水肥一体机G7', 1007, '生菜', '菊科'),
       (8, 1, '生长摄像头H8', 1008, '小麦', '禾本科'),
       (9, 1, '温控风扇I9', 1009, '小麦', '禾本科'),
       (10, 1, '补光灯J10', 1010, '多肉植物', '景天科'),
       (11, 1, 'PH值检测仪K11', 1011, '蓝莓', '杜鹃花科'),
       (12, 1, 'EC值传感器L12', 1012, '番茄', '茄科'),
       (13, 1, '自动播种机M13', 1013, '玉米', '禾本科'),
       (14, 1, '采摘机器人N14', 1014, '苹果', '蔷薇科'),
       (15, 1, '气象站O15', 1015, '苹果', '蔷薇科'),
       (16, 1, '虫情测报灯P16', 1016, '水稻', '禾本科'),
       (17, 1, '孢子捕捉仪Q17', 1017, '葡萄', '葡萄科'),
       (18, 1, '臭氧发生器R18', 1018, '蘑菇', '真菌'),
       (19, 1, '智能控制器S19', 1019, '兰花', '兰科'),
       (20, 1, '环境监测终端T20', 1020, '土豆', '茄科');

-- 插入 machine_record 数据
INSERT INTO `machine_record` (`id`, `machine_id`,
                              `soil_humidity`,
                              `atmospheric_temperature`,
                              `atmospheric_humidity`,
                              `illuminance`)
VALUES (1, 1, 65.50, 25.10, 60.00, 3200.50),
       (2, 2, 70.20, 26.50, 55.00, 2800.00),
       (3, 3, 55.80, 24.00, 65.00, 4500.00),
       (4, 4, 62.30, 26.00, 58.00, 3000.00),
       (5, 5, 58.00, 25.30, 58.00, 3000.00),
       (6, 6, 58.50, 27.20, 52.00, 2200.00),
       (7, 7, 68.00, 24.50, 62.00, 2700.00),
       (8, 8, 72.50, 25.80, 68.00, 3100.00),
       (9, 9, 58.00, 23.50, 55.00, 5000.00),
       (10, 10, 55.00, 28.00, 50.00, 2500.00),
       (11, 11, 60.00, 24.80, 60.00, 3300.00),
       (12, 12, 55.00, 23.00, 58.00, 2900.00),
       (13, 13, 63.50, 25.50, 62.00, 3600.00),
       (14, 14, 59.20, 24.20, 57.00, 2800.00),
       (15, 15, 62.00, 26.00, 54.00, 2300.00),
       (16, 16, 66.80, 27.00, 63.00, 3400.00),
       (17, 17, 57.50, 22.80, 56.00, 2600.00),
       (18, 18, 61.00, 25.00, 59.00, 3100.00),
       (19, 19, 54.50, 22.50, 53.00, 2400.00),
       (20, 20, 64.00, 26.20, 61.00, 3500.00);

-- 清理旧摄影记录（不变）
DELETE
FROM `machine_camera`
WHERE `machine_id` BETWEEN 4 AND 23;

-- 插入摄影记录（不变）
INSERT INTO `machine_camera` (`machine_id`, `image_url`, `result`, `status`)
VALUES (1, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '没有问题', 0),
       (2, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '叶片正常', 0),
       (3, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '发现少量病斑', 1),
       (4, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '生长良好', 0),
       (5, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '叶片黄化', 1),
       (6, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (7, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', 'EC值异常', 1),
       (8, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '健康', 0),
       (9, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '高温灼伤', 1),
       (10, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (11, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (12, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '缺氮症状', 1),
       (13, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '健康', 0),
       (14, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '虫害', 1),
       (15, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (16, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '光照不足', 1),
       (17, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (18, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (19, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '白粉病', 1),
       (20, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '健康', 0);

-- 每小时报告
INSERT INTO report (employee_id, time, type, online_count, total_count, normal_plant_count,
                    average_atmospheric_temperature, average_atmospheric_humidity,
                    average_soil_humidity, average_illuminance)
VALUES (1, '2026-04-20 00:00:00', 0, 42, 50, 48, 18.2, 69.5, 55.2, 115.0),
       (1, '2026-04-20 01:00:00', 0, 41, 50, 48, 17.6, 71.0, 55.0, 100.0),
       (1, '2026-04-20 02:00:00', 0, 40, 50, 48, 17.0, 72.5, 54.8, 90.0),
       (1, '2026-04-20 03:00:00', 0, 39, 50, 47, 16.8, 73.0, 54.5, 85.0),
       (1, '2026-04-20 04:00:00', 0, 38, 50, 47, 16.5, 73.5, 54.3, 80.0),
       (1, '2026-04-20 05:00:00', 0, 37, 50, 46, 16.3, 74.0, 54.0, 85.0),
       (1, '2026-04-20 06:00:00', 0, 38, 50, 47, 16.8, 73.0, 54.2, 120.0),
       (1, '2026-04-20 07:00:00', 0, 40, 50, 48, 17.5, 71.5, 54.5, 200.0),
       (1, '2026-04-20 08:00:00', 0, 43, 50, 49, 18.5, 69.0, 55.0, 350.0),
       (1, '2026-04-20 09:00:00', 0, 45, 50, 49, 19.8, 66.5, 55.5, 500.0),
       (1, '2026-04-20 10:00:00', 0, 47, 50, 50, 21.0, 64.0, 56.0, 650.0),
       (1, '2026-04-20 11:00:00', 0, 48, 50, 50, 22.5, 61.5, 56.5, 780.0),
       (1, '2026-04-20 12:00:00', 0, 49, 50, 50, 23.5, 59.0, 57.0, 850.0),
       (1, '2026-04-20 13:00:00', 0, 48, 50, 50, 24.2, 58.0, 57.3, 820.0),
       (1, '2026-04-20 14:00:00', 0, 47, 50, 49, 24.5, 57.5, 57.5, 780.0),
       (1, '2026-04-20 15:00:00', 0, 46, 50, 49, 24.0, 58.5, 57.2, 700.0),
       (1, '2026-04-20 16:00:00', 0, 45, 50, 49, 23.0, 60.0, 56.8, 600.0),
       (1, '2026-04-20 17:00:00', 0, 44, 50, 48, 21.8, 62.5, 56.3, 480.0),
       (1, '2026-04-20 18:00:00', 0, 43, 50, 48, 20.5, 65.0, 55.8, 350.0),
       (1, '2026-04-20 19:00:00', 0, 42, 50, 48, 19.5, 67.5, 55.5, 250.0),
       (1, '2026-04-20 20:00:00', 0, 41, 50, 48, 18.8, 69.0, 55.2, 180.0),
       (1, '2026-04-20 21:00:00', 0, 40, 50, 47, 18.2, 70.0, 55.0, 140.0),
       (1, '2026-04-20 22:00:00', 0, 39, 50, 47, 17.8, 70.5, 54.8, 120.0),
       (1, '2026-04-20 23:00:00', 0, 38, 50, 47, 17.5, 71.0, 54.6, 110.0);

-- 每日报告（已删除 average_soil_temperature 和 average_co2_concentration）
INSERT INTO report (employee_id, time, type, online_count, total_count, normal_plant_count,
                    average_atmospheric_temperature, average_atmospheric_humidity,
                    average_soil_humidity, average_illuminance)
VALUES (1, '2026-04-20 00:00:00', 1, 43, 50, 48, 20.1, 67.8, 55.3, 380.2),
       (1, '2026-04-21 00:00:00', 1, 44, 50, 49, 20.3, 67.5, 55.4, 382.5);