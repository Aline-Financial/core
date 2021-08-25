package com.aline.core.security.config;

import com.aline.core.config.DisableSecurityConfig;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.Optional;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.security.jwt")
@Configuration
@ConditionalOnMissingBean(DisableSecurityConfig.class)
public class JwtConfig {

    /**
     * Secret key for JWT Token
     */
    private String secretKey;

    /**
     * Token prefix for JWT Token
     */
    private String tokenPrefix;

    /**
     * Token expiration after days
     */
    private Integer tokenExpirationAfterDays;

    public String getTokenPrefix() {
        return returnOrDefault(tokenPrefix, "Bearer ");
    }

    public Integer getTokenExpirationAfterDays() {
        return returnOrDefault(tokenExpirationAfterDays, 14);
    }

    private String returnOrDefault(@Nullable String toBeReturned, @NonNull String defaultValue) {
        return Optional.ofNullable(toBeReturned).orElse(defaultValue);
    }

    private int returnOrDefault(@Nullable Integer toBeReturned, int defaultValue) {
        return Optional.ofNullable(toBeReturned).orElse(defaultValue);
    }
}
