package com.aline.core.security;

import com.aline.core.config.DisableSecurityConfig;
import com.aline.core.exception.notfound.UserNotFoundException;
import com.aline.core.model.user.User;
import com.aline.core.repository.UserRepository;
import com.aline.core.security.config.JwtConfig;
import com.aline.core.security.model.JwtToken;
import com.aline.core.security.model.UserAuthenticationToken;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpHeaders;
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
import java.util.Optional;

@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(DisableSecurityConfig.class)
@Slf4j(topic = "JWT Token Verifier")
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Verifying JWT Token...");
        // Get token from header
        String authorizationHeader = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .orElse("");

        log.info(authorizationHeader);

        if (authorizationHeader.equals("") || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromHeader(authorizationHeader);

        try {
            log.info("Parsing JWT Token...");
            // Parse the token
            JwtToken jwtToken = JwtToken.from(token, secretKey);
            String username = jwtToken.getUsername();

            User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

            log.info(username);

            GrantedAuthority authority = jwtToken.getAuthority();

            log.info(jwtToken.getAuthority().getAuthority());

            Authentication authentication = new UserAuthenticationToken(user,authority);

            log.info("Authentication: {}", authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            e.printStackTrace();
            throw new IllegalStateException("Token cannot be trusted.");
        }

        filterChain.doFilter(request, response);
    }

    public String getTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.substring(jwtConfig.getTokenPrefix().length());
    }
}
