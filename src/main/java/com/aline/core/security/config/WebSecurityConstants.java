package com.aline.core.security.config;

import com.aline.core.model.user.UserRole;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * This class provides constants for SpEL
 * that pertains to web security.
 */
@Getter
@Component
public class WebSecurityConstants {

    Roles authorities = new Roles();

    @Getter
    public static final class Roles {
        private final String member = UserRole.MEMBER.getRole();
        private final String admin = UserRole.ADMINISTRATOR.getRole();
        private final String employee = UserRole.EMPLOYEE.getRole();
    }
}
