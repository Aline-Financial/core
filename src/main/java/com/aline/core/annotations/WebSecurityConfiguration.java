package com.aline.core.annotations;

import com.aline.core.config.DisableSecurityConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableGlobalMethodSecurity
@ConditionalOnMissingBean(DisableSecurityConfig.class)
public @interface WebSecurityConfiguration {
    @AliasFor(annotation = EnableGlobalMethodSecurity.class, attribute = "prePostEnabled")
    boolean prePostEnabled() default true;
    @AliasFor(annotation = EnableGlobalMethodSecurity.class, attribute = "jsr250Enabled")
    boolean jsr50Enabled() default false;
    @AliasFor(annotation = EnableGlobalMethodSecurity.class, attribute = "proxyTargetClass")
    boolean proxyTargetClass() default false;
    @AliasFor(annotation = EnableGlobalMethodSecurity.class, attribute = "securedEnabled")
    boolean securedEnabled() default false;
}
