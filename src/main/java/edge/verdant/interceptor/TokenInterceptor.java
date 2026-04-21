package edge.verdant.interceptor;

import edge.verdant.mapper.EmployeeMapper;
import edge.verdant.domain.entity.Employee;
import edge.verdant.utils.CurrentHolder;
import edge.verdant.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private EmployeeMapper employeeMapper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader("token");

        // 2 判断token是否存在，如果不存在，说明用户没有登录，返回错误信息(响应401状态码)
        if (token == null || token.isEmpty()) {
            log.info("Token不存在, 请先登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// 401
            response.getWriter().write("Token not exist!Please login first.");
            return false;
        }

        // 3 如果token存在，校验令牌，如果校验失败 -> 返回错误信息(响应401状态码)
        try {
            Claims claims = JwtUtils.parseToken(token);
            System.out.println(claims.getSubject());
            Long id = Long.parseLong(JwtUtils.getTokenInfo(claims, "id").toString());
            System.out.println(id);
            Employee employee = employeeMapper.selectById(id);
            CurrentHolder.setCurrent(employee);// 把当前登录的用户对象设置到当前线程中
        } catch (Exception e) {
            log.info("Token校验失败, 请先登录", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// 401
            response.getWriter().write("Token error! Please login first.");
            return false;
        }

        // 4 校验通过，放行
        log.info("Token校验通过，放行");
        return true;
    }
}