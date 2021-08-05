package com.aline.core.security.auth;

import com.aline.core.model.user.User;
import com.aline.core.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Security User Service
 */
@AllArgsConstructor
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository repository;

    /**
     * retrieves user authorities by querying the database with the username
     *
     * @param s username
     * @return SecurityUser
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = repository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", s)));

        String role = user.getRole();

        if (role == null || role.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s does not have the right permissions.", s));
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>(Collections.singleton(authority));

        return new SecurityUser(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities,
                true,
                true,
                true,
                user.isEnabled());
    }
}
