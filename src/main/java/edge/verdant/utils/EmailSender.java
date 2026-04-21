package edge.verdant.utils;

import edge.verdant.constant.MessageConstant;
import edge.verdant.exception.BaseException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void send(String email, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setFrom(fromEmail, "翠绿边缘"); // 添加发件人名称

            helper.setSubject(subject);
            helper.setText(text, true); // true表示支持HTML

            mailSender.send(message);
            log.info("验证码发送成功，邮箱：{}", email);

        } catch (Exception e) {
            log.error("发送验证码失败，邮箱：{}, 错误原因: {}", email, e.getMessage());
            // 打印详细错误信息以便调试
            e.printStackTrace();
            throw new BaseException(MessageConstant.EMAIL_SEND_ERROR);
        }
    }
}