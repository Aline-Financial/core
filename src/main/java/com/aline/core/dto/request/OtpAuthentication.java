package com.aline.core.dto.request;

import com.aline.core.validation.annotations.Username;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

/**
 * One-Time Password authentication
 */
@Data
@Builder
public class OtpAuthentication {
    @Username
    private String username;

    @Size(min = 6, max = 6)
    private String otp;
}
