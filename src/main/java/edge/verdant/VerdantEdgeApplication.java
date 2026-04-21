package edge.verdant;

import edge.verdant.domain.dto.EmployeeRegisterDTO;
import edge.verdant.domain.entity.Employee;
import edge.verdant.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
@EnableCaching
@EnableScheduling
@MapperScan("edge.verdant.mapper")
public class VerdantEdgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(VerdantEdgeApplication.class, args);
    }

}
