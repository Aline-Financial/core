package com.aline.core.dto.request;

import com.aline.core.validation.annotations.MembershipId;
import com.aline.core.validation.annotations.SocialSecurity;
import com.aline.core.validation.annotations.Username;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@Builder
public class ResetPasswordAuthentication {

    /**
     * Username of the user requesting
     * a password reset
     */
    @Username
    private String username;

    /**
     * The email of the user requesting
     * a password reset
     */
    @Email
    private String email;

}
