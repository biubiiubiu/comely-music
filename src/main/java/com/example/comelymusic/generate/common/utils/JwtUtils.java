package com.example.comelymusic.generate.common.utils;

/**
 * description: jwt工具类
 *
 * @author: zhangtian
 * @since: 2022-04-16 16:37
 */

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * jwt工具类
 */
@Slf4j
@Component
public class JwtUtils {
    private final String SECRET = "comely-music";
    private final String HEADER="Authorization";
    private final int EXPIRATION = 86400;

    //创建token
    //传入userid
    public String createToken(String username) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, EXPIRATION);
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256, SECRET);
        return builder.compact();
    }

    //校验jwt
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("jwt match error:{}", e);
            return null;
        }
    }

    //判断token是否过期
    public boolean judgeTokenExpiration(Date expiration) {
        return expiration.before(new Date());
    }

    //判断token是否过期
    public boolean judgeTokenExpiration(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}
