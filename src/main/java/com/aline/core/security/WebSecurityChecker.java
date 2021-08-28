package com.aline.core.security;

import com.aline.core.annotation.ConditionalOnMissingWebSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@ConditionalOnMissingWebSecurity
@Slf4j(topic = "Web Security Checker")
@Component
public class WebSecurityChecker implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.warn("Web security is ENABLED but no implementation of the AbstractWebSecurityConfig was found.");
    }
}
