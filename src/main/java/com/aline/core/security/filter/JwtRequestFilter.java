package com.aline.core.security.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The JwtRequestFilter is an abstract class
 * that verifies the JWT token and provides and
 * filters based on the validity of the token.
 * Extra logic may be implemented that is based on the
 * token.
 */
public abstract class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }

}
