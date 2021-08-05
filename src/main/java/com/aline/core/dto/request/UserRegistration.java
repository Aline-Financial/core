package com.aline.core.dto.request;

import com.aline.core.validation.annotations.Password;
import com.aline.core.validation.annotations.Username;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserRegistration {

    @NotBlank(message = "Username is required.")
    @Username
    private String username;

    @NotBlank(message = "Password is required.")
    @Password
    private String password;
}
