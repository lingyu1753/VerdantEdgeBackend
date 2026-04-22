-- 插入员工表
-- =====================================================
-- 一共1条，数据处于合理范围
-- =====================================================
INSERT INTO `employee` (`id`, `name`, `password`, `email`, `phone`, `real_name`, `number`)
VALUES (1, 'admin', '123456', '2975194966@qq.com', '18025447710', '予梦玲羽', '1');

-- 插入设备表
-- =====================================================
-- 一共20条，数据处于合理范围
-- 全部指向员工 id = 1
-- =====================================================
INSERT INTO `machine` (`id`, `employee_id`, `name`, `number`, `plant_name`, `plant_type`, `online`)
VALUES (1, 1, '嵌入式设备A01', 1001, '空心菜', '旋花科', 1),
       (2, 1, '嵌入式设备A02', 1002, '黄瓜', '葫芦科', 1),
       (3, 1, '嵌入式设备A03', 1003, '玫瑰', '蔷薇科', 1),
       (4, 1, '嵌入式设备A04', 1004, '辣椒', '茄科', 1),
       (5, 1, '嵌入式设备A05', 1005, '辣椒', '茄科', 1),
       (6, 1, '嵌入式设备A06', 1006, '草莓', '蔷薇科', 0),
       (7, 1, '嵌入式设备A07', 1007, '生菜', '菊科', 1),
       (8, 1, '嵌入式设备A08', 1008, '小麦', '禾本科', 0),
       (9, 1, '嵌入式设备A09', 1009, '小麦', '禾本科', 1),
       (10, 1, '嵌入式设备A10', 1010, '多肉植物', '景天科', 1),
       (11, 1, '一体式设备B01', 1011, '蓝莓', '杜鹃花科', 1),
       (12, 1, '一体式设备B02', 1012, '番茄', '茄科', 1),
       (13, 1, '一体式设备B03', 1013, '玉米', '禾本科', 1),
       (14, 1, '一体式设备B04', 1014, '苹果', '蔷薇科', 0),
       (15, 1, '一体式设备B05', 1015, '苹果', '蔷薇科', 1),
       (16, 1, '一体式设备B06', 1016, '水稻', '禾本科', 0),
       (17, 1, '一体式设备B07', 1017, '葡萄', '葡萄科', 1),
       (18, 1, '一体式设备B08', 1018, '蘑菇', '真菌', 1),
       (19, 1, '一体式设备B09', 1019, '兰花', '兰科', 1),
       (20, 1, '一体式设备B1B0', 1020, '土豆', '茄科', 1);

-- 插入设备记录表
-- =====================================================
-- 一共20条，数据处于合理范围
-- =====================================================
INSERT INTO `machine_record` (`id`, `machine_id`,
                              `soil_humidity`,
                              `atmospheric_temperature`,
                              `atmospheric_humidity`,
                              `illuminance`)
VALUES
-- 正确株（15条，数值均在适宜范围内）
(1, 1, 70.00, 25.00, 70.00, 4000.00),   -- 空心菜
(2, 2, 75.00, 25.00, 65.00, 6000.00),   -- 黄瓜
(3, 3, 60.00, 20.00, 60.00, 4500.00),   -- 玫瑰
(4, 4, 70.00, 25.00, 60.00, 7000.00),   -- 辣椒
(5, 5, 70.00, 25.00, 60.00, 7000.00),   -- 辣椒
(7, 7, 70.00, 18.00, 65.00, 3000.00),   -- 生菜
(8, 8, 65.00, 20.00, 55.00, 6000.00),   -- 小麦
(9, 9, 65.00, 20.00, 55.00, 6000.00),   -- 小麦
(10, 10, 40.00, 23.00, 45.00, 7000.00), -- 多肉植物
(11, 11, 65.00, 20.00, 65.00, 3000.00), -- 蓝莓
(12, 12, 70.00, 25.00, 60.00, 6000.00), -- 番茄
(13, 13, 70.00, 25.00, 65.00, 7000.00), -- 玉米
(14, 14, 60.00, 20.00, 55.00, 4000.00), -- 苹果
(15, 15, 60.00, 20.00, 55.00, 4000.00), -- 苹果
(17, 17, 60.00, 25.00, 55.00, 6000.00), -- 葡萄
-- 偏差株（5条，各有一个字段偏离适宜范围）
(6, 6, 70.00, 30.00, 70.00, 3000.00),   -- 草莓：温度偏高（适宜15~25）
(16, 16, 90.00, 30.00, 50.00, 8000.00), -- 水稻：空气湿度偏低（适宜70~80）
(18, 18, 85.00, 18.00, 85.00, 2000.00), -- 蘑菇：光照偏高（适宜<500）
(19, 19, 70.00, 32.00, 70.00, 1500.00), -- 兰花：温度偏高（适宜20~28）
(20, 20, 40.00, 20.00, 65.00, 3000.00); -- 土豆：土壤湿度偏低（适宜60~80）

-- 插入设备摄影表
-- =====================================================
-- 一共20条，数据处于合理范围
-- =====================================================
INSERT INTO `machine_camera` (`machine_id`, `image_url`, `result`, `status`)
VALUES (1, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '没有问题', 0),
       (2, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '叶片正常', 0),
       (3, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '发现少量病斑', 1),
       (4, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '生长良好', 0),
       (5, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '叶片黄化', 1),
       (6, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (7, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '病变', 1),
       (8, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '健康', 0),
       (9, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '发现少量病斑', 1),
       (10, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (11, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (12, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '缺氮症状', 1),
       (13, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '健康', 0),
       (14, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '健康', 0),
       (15, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (16, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '略微缺乏光照', 0),
       (17, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (18, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '正常', 0),
       (19, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '白粉病', 1),
       (20, 'https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png', '健康', 0);

-- 24个小时报告
-- =====================================================
-- 1. 生成24条小时报告 (type=0)
-- 时间范围：当前整点小时 往前推23个小时（共24条）
-- 设备总数固定为20，online_count和normal_plant_count根据小时波动
-- =====================================================
INSERT INTO report (employee_id, time, type, online_count, total_count, normal_plant_count,
                    average_atmospheric_temperature, average_atmospheric_humidity,
                    average_soil_humidity, average_illuminance)
WITH RECURSIVE hour_offset AS (
    SELECT 0 AS offset
    UNION ALL
    SELECT offset + 1 FROM hour_offset WHERE offset < 23
),
               current_hour_start AS (
                   SELECT DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00') AS start_time
               ),
               hour_base AS (
                   SELECT
                       1 AS employee_id,
                       (SELECT start_time FROM current_hour_start) - INTERVAL offset HOUR AS time,
                       0 AS type,
                       LEAST(20, GREATEST(15,
                                          ROUND(17 + 2.5 * (1 + SIN((HOUR((SELECT start_time FROM current_hour_start) - INTERVAL offset HOUR) - 9) * PI() / 10)))
                                 )) AS online_count,
                       20 AS total_count,
                       ROUND(20 + 4 * SIN((HOUR((SELECT start_time FROM current_hour_start) - INTERVAL offset HOUR) - 14) * PI() / 12), 1) AS avg_temp,
                       ROUND(65 - 8 * SIN((HOUR((SELECT start_time FROM current_hour_start) - INTERVAL offset HOUR) - 14) * PI() / 12), 1) AS avg_hum,
                       ROUND(55 + 2 * SIN((HOUR((SELECT start_time FROM current_hour_start) - INTERVAL offset HOUR) - 14) * PI() / 12), 1) AS avg_soil,
                       ROUND(GREATEST(20, LEAST(800,
                                                20 + 780 * (1 + SIN((HOUR((SELECT start_time FROM current_hour_start) - INTERVAL offset HOUR) - 9) * PI() / 12)) / 2
                                          )), 1) AS avg_illu
                   FROM hour_offset
               )
SELECT
    employee_id,
    time,
    type,
    online_count,
    total_count,
    GREATEST(0, LEAST(online_count, online_count - (HOUR(time) % 2))) AS normal_plant_count,
    avg_temp,
    avg_hum,
    avg_soil,
    avg_illu
FROM hour_base;

-- 30天报告
-- =====================================================
-- 2. 插入 30 条每日报告 (type=1)
-- 时间范围：今天 往前推 29 天（共 30 条）
-- 数据基于日序正弦波动，设备总数固定为 20
-- =====================================================
INSERT INTO report (employee_id, time, type, online_count, total_count, normal_plant_count,
                    average_atmospheric_temperature, average_atmospheric_humidity,
                    average_soil_humidity, average_illuminance)
WITH RECURSIVE day_offset AS (
    SELECT 0 AS offset
    UNION ALL
    SELECT offset + 1 FROM day_offset WHERE offset < 29
),
               day_base AS (
                   SELECT
                       offset,
                       1 AS employee_id,
                       (CURDATE() - INTERVAL offset DAY) AS time,
                       1 AS type,
                       ROUND(18 + 1.5 * SIN(offset * 2 * PI() / 30)) AS online_count,
                       20 AS total_count,
                       ROUND(20 + 3 * SIN(offset * 2 * PI() / 30), 1) AS avg_temp,
                       ROUND(65 - 4 * SIN(offset * 2 * PI() / 30), 1) AS avg_hum,
                       ROUND(55 + 2 * SIN(offset * 2 * PI() / 30), 1) AS avg_soil,
                       ROUND(380 + 50 * SIN(offset * 2 * PI() / 30), 1) AS avg_illu
                   FROM day_offset
               )
SELECT
    employee_id,
    time,
    type,
    online_count,
    total_count,
    GREATEST(0, LEAST(online_count, online_count - (offset % 2))) AS normal_plant_count,
    avg_temp,
    avg_hum,
    avg_soil,
    avg_illu
FROM day_base;