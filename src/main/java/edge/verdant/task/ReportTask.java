package edge.verdant.task;

import edge.verdant.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReportTask {
    @Autowired
    private ReportService reportService;

    @Scheduled(cron = "0 0 * * * ?")
    public void generateHourlyReport() {
        log.info("开始生成小时报告...");
        try {
            reportService.generate(0);
            log.info("小时报告生成完成");
        } catch (Exception e) {
            log.error("小时报告生成失败", e);
        }
    }

    @Scheduled(cron = "0 0 6 * * ?")
    public void generateDailyReport() {
        log.info("开始生成每日报告...");
        try {
            reportService.generate(1);
            log.info("每日报告生成完成");
        } catch (Exception e) {
            log.error("每日报告生成失败", e);
        }
    }
}
