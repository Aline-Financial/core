package com.aline.core.security.filter;

import com.aline.core.security.model.JwtToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourceAccessFilter extends JwtTokenFilter {

    @Override
    protected void doFilterWithJwt(JwtToken token, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException {

    }

}
