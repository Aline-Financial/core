package com.aline.core.security.service;

import com.aline.core.exception.UnauthorizedException;
import com.aline.core.model.user.User;
import com.aline.core.repository.UserRepository;
import com.aline.core.security.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Security User Service
 */
@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found.", username)));

        String role = Optional.of(user.getRole()).orElseThrow(
                () -> new UnauthorizedException(String.format("User '%s' does not have the right permissions.", username)));

        Set<SimpleGrantedAuthority> grantedAuthorities = Collections.singleton(new SimpleGrantedAuthority(role));

        return SecurityUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .grantedAuthorities(grantedAuthorities)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(user.isEnabled())
                .build();
    }
}
