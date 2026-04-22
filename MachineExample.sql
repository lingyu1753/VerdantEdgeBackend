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
INSERT INTO `machine` (`id`, `employee_id`, `name`, `number`, `plant_name`, `plant_type`)
VALUES (1, 1, '嵌入式设备A01', 1001, '番茄', '茄科'),
       (2, 1, '嵌入式设备A02', 1002, '黄瓜', '葫芦科'),
       (3, 1, '嵌入式设备A03', 1003, '玫瑰', '蔷薇科'),
       (4, 1, '嵌入式设备A04', 1004, '辣椒', '茄科'),
       (5, 1, '嵌入式设备A05', 1005, '辣椒', '茄科'),
       (6, 1, '嵌入式设备A06', 1006, '草莓', '蔷薇科'),
       (7, 1, '嵌入式设备A07', 1007, '生菜', '菊科'),
       (8, 1, '嵌入式设备A08', 1008, '小麦', '禾本科'),
       (9, 1, '嵌入式设备A09', 1009, '小麦', '禾本科'),
       (10, 1, '嵌入式设备A10', 1010, '多肉植物', '景天科'),
       (11, 1, '一体式设备B01', 1011, '蓝莓', '杜鹃花科'),
       (12, 1, '一体式设备B02', 1012, '番茄', '茄科'),
       (13, 1, '一体式设备B03', 1013, '玉米', '禾本科'),
       (14, 1, '一体式设备B04', 1014, '苹果', '蔷薇科'),
       (15, 1, '一体式设备B05', 1015, '苹果', '蔷薇科'),
       (16, 1, '一体式设备B06', 1016, '水稻', '禾本科'),
       (17, 1, '一体式设备B07', 1017, '葡萄', '葡萄科'),
       (18, 1, '一体式设备B08', 1018, '蘑菇', '真菌'),
       (19, 1, '一体式设备B09', 1019, '兰花', '兰科'),
       (20, 1, '一体式设备B1B0', 1020, '土豆', '茄科');

-- 插入设备记录表
-- =====================================================
-- 一共20条，数据处于合理范围
-- =====================================================
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
    GREATEST(0, LEAST(online_count,
                      online_count - (HOUR(time) % 2)
                )) AS normal_plant_count,
    avg_temp AS average_atmospheric_temperature,
    avg_hum AS average_atmospheric_humidity,
    avg_soil AS average_soil_humidity,
    avg_illu AS average_illuminance
FROM hour_base;

-- 30个每日报告
-- =====================================================
-- 2. 生成30条每日报告 (type=1)
-- 时间范围：今天 往前推29天（共30条）
-- 数据基于日序正弦波动，体现缓慢的季节性变化
-- =====================================================
WITH RECURSIVE day_offset AS (
    SELECT 0 AS offset
    UNION ALL
    SELECT offset + 1 FROM day_offset WHERE offset < 29
),
               day_base AS (
                   SELECT
                       offset,   -- 关键：保留 offset 列，供外层计算 normal_plant_count 使用
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
    GREATEST(0, LEAST(online_count,
                      online_count - (offset % 2)
                )) AS normal_plant_count,
    avg_temp AS average_atmospheric_temperature,
    avg_hum AS average_atmospheric_humidity,
    avg_soil AS average_soil_humidity,
    avg_illu AS average_illuminance
FROM day_base;