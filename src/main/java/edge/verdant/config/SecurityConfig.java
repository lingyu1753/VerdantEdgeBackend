package edge.verdant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Value("${verdant.password.strength}")
    private int PASSWORD_STRENGTH;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(PASSWORD_STRENGTH);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                               .requestMatchers(
                                                       "/machine/machineRecord",
                                                       "/machine/machineCamera",
                                                       "/admin/employee/login",
                                                       "/admin/employee/register",
                                                       "/doc.html",
                                                       "/webjars/**",
                                                       "/swagger-resources/**",
                                                       "/v3/api-docs/**",
                                                       "/favicon.ico"
                                                               ).permitAll()   // 放行注册接口
                                               .anyRequest().permitAll()                // 其他接口需要认证
                                      )
                .sessionManagement(session -> session
                                           .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 无状态
                                  );
        return http.build();
    }
}
