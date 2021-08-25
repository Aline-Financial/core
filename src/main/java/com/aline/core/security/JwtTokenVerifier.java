package com.aline.core.security;

import com.aline.core.config.DisableSecurityConfig;
import com.aline.core.security.config.JwtConfig;
import com.aline.core.security.model.JwtToken;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(DisableSecurityConfig.class)
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get token from header
        String authorizationHeader = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .orElse("");

        if (Strings.isBlank(authorizationHeader) || !authorizationHeader.startsWith(HttpHeaders.AUTHORIZATION)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromHeader(authorizationHeader);

        try {
            // Parse the token
            JwtToken jwtToken = JwtToken.from(token, secretKey);
            String username = jwtToken.getUsername();
            GrantedAuthority authority = jwtToken.getAuthority();

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singleton(authority)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            e.printStackTrace();
            throw new IllegalStateException(String.format("Token %s cannot be trusted.", token));
        }

        filterChain.doFilter(request, response);
    }

    public String getTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.substring(jwtConfig.getTokenPrefix().length());
    }
}
