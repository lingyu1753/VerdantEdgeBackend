package edge.verdant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edge.verdant.interceptor.TokenInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns(
                        "/admin/employee/login",
                        "/admin/employee/register",
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/favicon.ico"
                                    );
    }

    /**
     * 手动添加 Knife4j 静态资源映射（关键修复）
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("手动添加 Knife4j 静态资源映射...");
        // 映射 doc.html 和 webjars 等资源
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 全局 OpenAPI 文档信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                              .title("翠绿边缘接口文档")
                              .version("1.0")
                              .description("翠绿边缘接口文档")
                              .contact(new Contact().name("翠绿边缘团队"))
                     );
    }

    /**
     * 管理端接口分组
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                             .group("管理端接口")
                             .packagesToScan("edge.verdant.controller.admin")
                             .build();
    }

    /**
     * 设备接口分组
     */
    @Bean
    public GroupedOpenApi machineApi() {
        return GroupedOpenApi.builder()
                             .group("设备接口")
                             .packagesToScan("edge.verdant.controller.machine")
                             .build();
    }

    /**
     * 配置 Jackson ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        log.info("配置 ObjectMapper");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}