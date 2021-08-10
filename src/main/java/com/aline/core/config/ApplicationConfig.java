package com.aline.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Application config will allow
 * for POJO access to any property defined in
 * the application.properties/yml as long as it is
 * prefixed with <code>application</code>.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {
    /**
     * Secret Key for encryption and decryption.
     */
    private String secretKey;
}
