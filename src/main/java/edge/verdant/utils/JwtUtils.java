package edge.verdant.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Data
@Component
public class JwtUtils {
    private static String secretKey;
    private static long expirationTime;
    private static SignatureAlgorithm signatureAlgorithm;

    // 从配置文件中注入值
    @Value("${jwt.secret-key}") private String secretKeyValue;
    @Value("${jwt.expiration-time}") private long expirationTimeValue;
    @Value("${jwt.signature-algorithm}") private String signatureAlgorithmValue;

    @PostConstruct
    public void init() {
        secretKey = secretKeyValue;
        expirationTime = expirationTimeValue;
        signatureAlgorithm = SignatureAlgorithm.forName(signatureAlgorithmValue);
    }


    /**
     * 生成JWT令牌
     * @param claims 自定义声明内容
     * @return JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                   .signWith(signatureAlgorithm, secretKey) // 指定签名算法和秘钥
                   .addClaims(claims) // 添加自定义信息
                   .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 设置过期时间
                   .compact(); // 生成令牌
    }

    /**
     * 解析JWT令牌
     * @param token JWT令牌字符串
     * @return 声明内容
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                   .setSigningKey(secretKey) // 使用签名钥匙
                    .build()
                   .parseClaimsJws(token) // 解析声明令牌
                   .getBody(); // 获取令牌本体
    }

    /**
     * 获取令牌所属 用户ID
     * @param claims JWT令牌字符串
     * @param key 声明内容的键
     * @return 用户ID
     */
    public static Object getTokenInfo(Claims claims, String key) {
        return claims.get(key);
    }
}
