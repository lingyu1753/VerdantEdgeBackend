package edge.verdant.utils;

import org.springframework.stereotype.Component;

/**
 * 邮箱工具类
 *
 * @author Ling_Yu175
 * @version 1.0
 * @since 2025-10-30
 */
@Component
public class EmailUtils {

    /**
     * 校验邮箱格式
     *
     * @param email 邮箱
     * @return 是否符合邮箱格式
     */
    public static boolean isValid(String email) {
        return email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    }

    public static String buildWarningMessage(String realName, Integer number, String content) {
        return "<!DOCTYPE html>" +
               "<html lang=\"zh-CN\">" +
               "<head>" +
               "<meta charset=\"UTF-8\">" +
               "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
               "<style>" +
               "  body { margin: 0; padding: 0; background-color: #F2F4F3; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; line-height: 1.5; }" +
               "  .container { max-width: 600px; margin: 30px auto; padding: 0 20px; }" +
               "  .card { background-color: #FFFFFF; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.03); border: 1px solid #E5E7EB; overflow: hidden; }" +
               "  .header { background-color: #2F55D4; padding: 24px 30px; }" +
               "  .header h1 { margin: 0; color: #FFFFFF; font-size: 1.5rem; font-weight: 700; letter-spacing: 0.5px; }" +
               "  .header p { margin: 8px 0 0; color: rgba(255,255,255,0.8); font-size: 0.875rem; }" +
               "  .meta-bar { background-color: #F9FAFB; padding: 16px 30px; border-bottom: 1px solid #E5E7EB; display: flex; flex-wrap: wrap; gap: 24px; }" +
               "  .meta-item { display: flex; align-items: baseline; }" +
               "  .meta-label { font-size: 0.75rem; text-transform: uppercase; letter-spacing: 0.5px; color: #6B7280; margin-right: 8px; }" +
               "  .meta-value { font-size: 1rem; font-weight: 600; color: #1F2937; }" +
               "  .content { padding: 30px; }" +
               "  .content-body { color: #1F2937; font-size: 0.95rem; }" +
               "  .content-body p { margin: 1em 0; }" +
               "  .content-body strong { color: #2F55D4; }" +
               "  .content-body ul, .content-body ol { padding-left: 24px; margin: 1em 0; }" +
               "  .content-body li { margin: 0.5em 0; }" +
               "  .footer { background-color: #F9FAFB; padding: 20px 30px; border-top: 1px solid #E5E7EB; color: #6B7280; font-size: 0.75rem; text-align: center; }" +
               "  .footer a { color: #2F55D4; text-decoration: none; }" +
               "  .accent-badge { display: inline-block; background-color: #FEF3C7; color: #B45309; font-size: 0.7rem; font-weight: 600; padding: 4px 10px; border-radius: 20px; margin-left: 8px; vertical-align: middle; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class=\"container\">" +
               "  <div class=\"card\">" +
               "    <!-- 头部 -->" +
               "    <div class=\"header\">" +
               "      <h1>🌿 翠绿边缘 · 报警信息</h1>" +
               "      <p>实时监控告警通知</p>" +
               "    </div>" +
               "    <!-- 员工/设备信息条 -->" +
               "    <div class=\"meta-bar\">" +
               "      <div class=\"meta-item\">" +
               "        <span class=\"meta-label\">员工姓名</span>" +
               "        <span class=\"meta-value\">" + realName + "</span>" +
               "      </div>" +
               "      <div class=\"meta-item\">" +
               "        <span class=\"meta-label\">设备编号</span>" +
               "        <span class=\"meta-value\">" + number + " <span class=\"accent-badge\">警告</span></span>" +
               "      </div>" +
               "    </div>" +
               "    <!-- 正文内容（AI 生成） -->" +
               "    <div class=\"content\">" +
               "      <div class=\"content-body\">" +
               content +
               "      </div>" +
               "    </div>" +
               "    <!-- 页脚 -->" +
               "    <div class=\"footer\">" +
               "      <p>此为系统自动发送的监控邮件，请勿直接回复。<br>如有疑问请联系管理员或访问 <a href=\"#\">翠绿边缘控制台</a>。</p>" +
               "      <p style=\"margin-top: 12px;\">© 2026 翠绿边缘 · 智慧农业</p>" +
               "    </div>" +
               "  </div>" +
               "</div>" +
               "</body>" +
               "</html>";
    }
}
