package com.aline.core.security;

import com.aline.core.config.AppConfig;
import com.aline.core.dto.request.AuthenticationRequest;
import com.aline.core.exception.ForbiddenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.val;
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
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AppConfig appConfig;
    private final SecretKey jwtSecretKey;
    private final ObjectMapper objectMapper;

    private AppConfig.Security.JWT jwtConfig;

    @PostConstruct
    public void init() {
        jwtConfig = appConfig.getSecurity().getJwt();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            val authRequest = objectMapper.readValue(request.getInputStream(),
                    AuthenticationRequest.class);

            val authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

            return authenticationManager.authenticate(authentication);

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

        response.setHeader(jwtConfig.getAuthorizationHeader(), tokenStr);

    }
}
