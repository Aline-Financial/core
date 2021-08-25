package com.aline.core.security.model;

import com.aline.core.model.user.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final User user;
    private final GrantedAuthority authority;

    public UserAuthenticationToken(User user, GrantedAuthority authority) {
        super(Collections.singleton(authority));
        this.authority = authority;
        this.user = user;
    }

    public GrantedAuthority getAuthority() {
        return authority;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public User getPrincipal() {
        return user;
    }
}
