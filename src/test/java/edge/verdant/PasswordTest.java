package edge.verdant;

import edge.verdant.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testPassword() {
        String password = "123456";
        password = passwordEncoder.encode(password);
        System.out.println(password);
    }
}
