package com.aline.core.security;

import com.aline.core.config.AppConfig;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class JwtSecretKey {

    private final AppConfig appConfig;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(appConfig
                .getSecurity()
                .getJwt()
                .getSecretKey()
                .getBytes(StandardCharsets.UTF_8));
    }

}
