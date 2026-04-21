package edge.verdant.config;

import edge.verdant.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 应用初始化配置
 * 在项目启动时自动执行初始化任务
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationInitializer implements CommandLineRunner {

    private final EmployeeServiceImpl employeeService;

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(15000);
        log.info("开始执行应用初始化任务...");
        employeeService.initAdminAccount();
        log.info("应用初始化任务完成");
    }
}
