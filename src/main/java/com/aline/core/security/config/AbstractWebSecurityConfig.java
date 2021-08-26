package com.aline.core.security.config;

import com.aline.core.repository.UserRepository;
import com.aline.core.security.filter.JwtTokenFilter;
import com.aline.core.security.filter.JwtTokenProvider;
import com.aline.core.security.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public abstract class AbstractWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected abstract String[] publicAntMatchers();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Configure UserDetailsService
        auth.userDetailsService(securityUserService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        // Enable CORS and disable CSRF
                .cors()
                .and()
                .csrf().disable()

        // Set session management to stateless
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

        // Set endpoint permissions
                .authorizeRequests()
                // Default ant matchers
                .antMatchers(
                        "/v3/api-docs/**",
                        "/health",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "**/swagger-resources/**"
                ).permitAll()
                .antMatchers(publicAntMatchers()).permitAll()
                // Private endpoints
                .anyRequest().authenticated()

                .and()
                .addFilter(jwtTokenProvider)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Authentication Manager Bean exposed for use
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
