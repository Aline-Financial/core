package com.aline.core.security.config;

import com.aline.core.config.AppConfig;
import com.aline.core.security.AuthenticationFilter;
import com.aline.core.security.JwtTokenVerifier;
import com.aline.core.security.service.SecurityUserService;
import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

/**
 * The abstract Web Security configuration class
 * is used to create a preconfigured security
 * configuration that can be customized to fit
 * a microservice's security needs.
 */
@Getter
@EnableGlobalMethodSecurity(prePostEnabled = true)
public abstract class AbstractWebSecurityConfig extends WebSecurityConfigurerAdapter {

    // This is the only time I will ever use field injection.
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SecurityUserService service;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private AuthenticationFilter authenticationFilter;
    @Autowired
    private JwtTokenVerifier verifier;

    /**
     * Return a string array of all ant matchers
     * that may be permitted to be accessed.
     * @return A string array of ant matcher patterns.
     */
    public abstract String[] publicAntMatchers();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(getDataSource())
                .usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, role, enabled FROM user WHERE username = ?")
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(getAuthenticationFilter())
                .addFilterAfter(getVerifier(), AuthenticationFilter.class)
                .authorizeRequests()
                // Default ant matchers
                .antMatchers("/v3/api-docs/**",
                        "/health",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "**/swagger-resources/**").permitAll()
                // Set by the abstract class
                .antMatchers(publicAntMatchers()).permitAll()
                .anyRequest()
                .authenticated();
    }

    /* Beans used for this configuration */

    /**
     * Provides the authentication filter with an
     * authentication manager bean.
     * @return Authentication Manager bean
     * @throws Exception thrown by super class authentication manager bean.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(getService());
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                Arrays.asList(
                        getAppConfig().getAdminPortal(),
                        getAppConfig().getLandingPortal(),
                        getAppConfig().getMemberDashboard()));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(
                Arrays.asList(HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.CONTENT_LENGTH,
                        HttpHeaders.ACCEPT,
                        HttpHeaders.ORIGIN,
                        HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,
                        HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS,
                        HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
        configuration.setExposedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));

        val source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
