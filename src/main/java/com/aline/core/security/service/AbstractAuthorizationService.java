package com.aline.core.security.service;

import com.aline.core.model.user.UserRole;
import com.aline.core.security.model.SecurityUser;
import com.aline.core.security.model.UserRoleAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

public abstract class AbstractAuthorizationService<T> {

    protected SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    protected Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }

    protected String getUsername() {
        return getAuthentication().getName();
    }

    protected UserRoleAuthority getAuthority() {
        return (UserRoleAuthority) new ArrayList<>(getAuthentication()
                .getAuthorities())
                .get(0);
    }

    protected UserRole getRole() {
        return getAuthority().getUserRole();
    }

    public abstract boolean canAccess(T responseBody);
}
