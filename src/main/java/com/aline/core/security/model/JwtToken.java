package com.aline.core.security.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

/**
 * Parsed JWT Token
 */
@Data
@Builder
@Slf4j(topic = "JWT Token")
public class JwtToken {
    /**
     * The principal of the JWT Token
     */
    private String username;

    /**
     * The granted authority of the JWT Token
     */
    private GrantedAuthority authority;

    /**
     * JWT Token issue date
     */
    private Date issuedAt;

    /**
     * JWT Token expiration date
     */
    private Date expiration;

    /**
     * Parse a JWT token with a secret key.
     * @param token The token string
     * @param secretKey The secret key the token is signed with
     * @return A parsed JwtToken object.
     */
    public static JwtToken from(String token, SecretKey secretKey) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        Claims body = claimsJws.getBody();

        String username = body.getSubject();
        val authority = body.get("authority", String.class);
        val grantedAuthority = new SimpleGrantedAuthority(authority);

        val iat = body.get("iat", Long.class);
        val exp = body.get("exp", Long.class);

        return JwtToken.builder()
                .username(username)
                .authority(grantedAuthority)
                .issuedAt(Date.from(Instant.ofEpochSecond(iat)))
                .expiration(Date.from(Instant.ofEpochSecond(exp)))
                .build();
    }
}
