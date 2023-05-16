package com.example.onjeong.user.Auth;

import com.example.onjeong.user.domain.User;
import com.example.onjeong.user.redis.RefreshToken;
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {
    private static final String secretKey = "ThisIsA_SecretKeyForJwt";

    public static String generateJwtToken(User user) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getUserNickname())
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setExpiration(createExpireHourForOneYear())
                .signWith(SignatureAlgorithm.HS256, createSigningKey());
        return builder.compact();
    }

    public static RefreshToken generateJwtRefreshToken(User user) {
        return new RefreshToken(UUID.randomUUID().toString(), user.getUserId());
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime :" + claims.getExpiration());
            log.info("userNickname :" + claims.get("userNickname"));
            log.info("role :" + claims.get("role"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private static Date createExpireHourForOneYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 2);
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userNickname", user.getUserNickname());
        claims.put("userName", user.getUserName());
        claims.put("role", user.getRole());
        return claims;
    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    public static String getUserNicknameFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("userNickname");
    }
}
