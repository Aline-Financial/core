package com.aline.core.security.filter;

import com.aline.core.config.DisableSecurityConfig;
import com.aline.core.exception.UnauthorizedException;
import com.aline.core.security.config.JwtConfig;
import com.aline.core.security.model.JwtToken;
import com.aline.core.security.model.UserAuthToken;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(DisableSecurityConfig.class)
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final SecretKey jwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).orElse("");

        // Validate authorization header
        if (authHeader.isEmpty() || !authHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(jwtConfig.getTokenPrefix().length()).trim();

        try {

            JwtToken jwtToken = JwtToken.from(token, jwtSecretKey);

            // Check token expiration
            if (jwtToken.isExpired()) {
                filterChain.doFilter(request, response);
                return;
            }

            UserAuthToken authenticationToken = new UserAuthToken(jwtToken.getUsername(), jwtToken.getAuthority());

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            val securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticationToken);

        } catch (JwtException exception) {
            exception.printStackTrace();
            throw new UnauthorizedException("The provided token cannot be trusted.");
        }

        filterChain.doFilter(request, response);

    }
}
