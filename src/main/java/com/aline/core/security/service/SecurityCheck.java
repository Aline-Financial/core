package com.aline.core.security.service;

import com.aline.core.dto.response.UserResponse;
import org.springframework.security.core.Authentication;

public interface SecurityCheck {
    boolean check(UserResponse response, Authentication authentication);
}
