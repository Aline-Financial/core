package com.aline.core.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    private String secretkey;
    private String tokenprefix;
    private Integer tokenexpirationafterdays;


    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
