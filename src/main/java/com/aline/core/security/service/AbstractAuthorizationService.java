package com.aline.core.security.service;

import com.aline.core.model.user.UserRole;
import com.aline.core.security.model.UserRoleAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class acts a sort of predicate to be used with
 * the pre/post authorize annotations. It provides methods
 * for easy access to the security context information.
 * @param <T> The parameter type of the predicate.
 */
public abstract class AbstractAuthorizationService<T> {

    /**
     * Get the current security context.
     * @return The current stateless security context.
     */
    protected SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    /**
     * Get the current authentication object.
     * @return Authentication object from the security context.
     */
    protected Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }

    /**
     * Get the current user's username.
     * @return A string of the current username.
     */
    protected String getUsername() {
        return getAuthentication().getName();
    }

    /**
     * Get the current user's role as an authority.
     * @return UserRoleAuthority representing the UserRole.
     * @see UserRole
     */
    protected UserRoleAuthority getAuthority() {
        GrantedAuthority grantedAuthority = new ArrayList<>(getAuthentication()
                .getAuthorities())
                .get(0);
        if (grantedAuthority instanceof SimpleGrantedAuthority) {
            return new UserRoleAuthority(grantedAuthority.getAuthority());
        }
        return (UserRoleAuthority) grantedAuthority ;
    }

    /**
     * Get the current user's role.
     * @return UserRole enum
     */
    protected UserRole getRole() {
        return getAuthority().getUserRole();
    }

    protected boolean userIsManagement() {
        List<UserRole> managementRoles = Arrays.asList(
                UserRole.EMPLOYEE,
                UserRole.ADMINISTRATOR
        );

        return managementRoles.contains(getRole());
    }

    public abstract boolean canAccess(T returnObject);
}
