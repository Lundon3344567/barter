package com.example.barter_authority.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类，实现token
 * @author walnut
 * @date 2021/11/5 7:52 下午
 * @version 1.0
 */
@Component
public class JwtUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 过期时间60分钟
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000 ;
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET="BARTERSICNUEDU";
    private static final String HEADER = "Authorization";

    @Value("{token-config.jwt.secret}")
    private String secret;

    /**
     * 生成签名60分钟后过期
     *
     * @param userName  用户名
     * @param userId   用户ID
     * @return 加密的token
     */
    public static String sign(Long userId, String userName){
        try {
            //过期时间
            Date date =new Date(System.currentTimeMillis()+EXPIRE_TIME);
            //私钥及加密算法
            Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header=new HashMap<>(2);
            header.put("typ","JWT");
            header.put("alg","hs256");

            //附带userName userId信息，生成签名
            String jwtString = JWT.create()
                    .withHeader(header)
                    .withClaim("userId", userId)
                    .withClaim("userName", userName)
                    .withExpiresAt(date)
                    .sign(algorithm);


            //返回前端的token
            return jwtString;

        }catch (Exception ex){
            return null;
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @return  是否正确
     */
    public static boolean verify(String token){
        try{

            Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier= JWT.require(algorithm)
                    .build();
            DecodedJWT JWT=verifier.verify(token);

//            if(!JWT.getExpiresAt().before(new Date())){
//                LOG.info( "LOG==> " + String.format("%-12s","JWT") + String.format("%-12s",JWT)  + " :: info: 过期的token（UTIL）");
//                throw new JwtException("token 已过期");
//            }

            return true;
        }catch (Exception ex){
            LOG.info( "LOG==> " + String.format("%-12s","") + String.format("%-12s","")  + " :: info: 不合法的token（UTIL）");
            throw new JwtException("token 不合法 或已过期");
        }
    }

    /**
     * 获取token中的信息无需secret解密也能获取
     *
     * @param token 密钥
     * @return  token中包含的用户名
     */
    public static String getUserName(String token){
        try {
            DecodedJWT jwt=JWT.decode(token);
            return  jwt.getClaim("userName").asString();
        }catch (JWTDecodeException ex){
            return null;
        }
    }

    /**
     * 获取token中的信息无需secret解密也能获取
     *
     * @param token 密钥
     * @return  token中包含的用户ID
     */
    public static Long getUserId(String token){
        try {
            DecodedJWT jwt=JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        }catch (JWTDecodeException ex){
            return null;
        }
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOG.info("JWT格式验证失败:{}",token);
        }
        return claims;
    }



    public static long getExpireTime() {
        return EXPIRE_TIME;
    }

    public static String getTokenSecret() {
        return TOKEN_SECRET;
    }

    public static String getHeader() {
        return HEADER;
    }
}
