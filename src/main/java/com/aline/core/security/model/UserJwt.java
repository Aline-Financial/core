package com.aline.core.security.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.Data;
import lombok.val;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Data
@Builder
public class UserJwt {

    /**
     * User grabbed from the signed JWT
     */
    private String username;

    /**
     * Authority of the user
     */
    private GrantedAuthority authority;

    /**
     * The date the token was issued
     */
    private Date issuedAt;

    /**
     * The date the token expires
     */
    private Date expiration;

    public static UserJwt from(String jwt, SecretKey key) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(jwt);

        Claims body = claimsJws.getBody();

        String username = body.getSubject();

        String authStr = body.get("authority", String.class);

        GrantedAuthority authority = new SimpleGrantedAuthority(authStr);

        val iat = body.get("iat", Long.class);
        val exp = body.get("exp", Long.class);

        return UserJwt.builder()
                .username(username)
                .authority(authority)
                .issuedAt(Date.from(Instant.ofEpochSecond(iat)))
                .expiration(Date.from(Instant.ofEpochSecond(exp)))
                .build();
    }

}
