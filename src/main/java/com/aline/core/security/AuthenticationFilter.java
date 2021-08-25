package com.aline.core.security;

import com.aline.core.config.AppConfig;
import com.aline.core.dto.request.AuthenticationRequest;
import com.aline.core.exception.ForbiddenException;
import com.aline.core.security.config.JwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

@Component
@Slf4j(topic = "Authentication Filter")
@RequiredArgsConstructor
@ConditionalOnBean(AuthenticationManager.class)
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtConfig jwtConfig;
    private final SecretKey jwtSecretKey;
    private final ObjectMapper objectMapper;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            val authRequest = objectMapper.readValue(request.getInputStream(),
                    AuthenticationRequest.class);

            log.info("Attempting to authenticate user '{}' with password '{}'", authRequest.getUsername(), authRequest.getPassword());

            val authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

            return getAuthenticationManager().authenticate(authentication);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ForbiddenException("Unable to authenticate user.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        int expireAfterDays = jwtConfig.getTokenExpirationAfterDays();

        GrantedAuthority authority = new ArrayList<>(authResult.getAuthorities()).get(0);

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authority", authority)
                .setIssuedAt(Date.from(LocalDate.now()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()))
                .setExpiration(Date.from(LocalDate.now()
                        .plusDays(expireAfterDays)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();

        String tokenStr = jwtConfig.getTokenPrefix() + token;

        response.setHeader(HttpHeaders.AUTHORIZATION, tokenStr);

    }
}
