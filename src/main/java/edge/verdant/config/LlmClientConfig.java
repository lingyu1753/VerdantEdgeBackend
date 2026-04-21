package edge.verdant.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LlmClientConfig {

    private static final String DEFAULT_PROMPT = "你是一个AI问答助手，可以根据用户的问题进行回答。";

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem(DEFAULT_PROMPT)                // 系统初始设置语句
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()             // 日志
                                )
                .defaultOptions(                              // 设置
                                                              DashScopeChatOptions.builder()
                                                                                  .withTemperature(0.7)         // 温度
                                                                                  .withMaxToken(2000) // 最大 Token 数
                                                                                  .build()
                               )
                .build();
    }
}
