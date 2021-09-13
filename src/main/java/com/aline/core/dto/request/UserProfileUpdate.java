package com.aline.core.dto.request;

import com.aline.core.validation.annotation.PhoneNumber;
import com.aline.core.validation.annotation.Username;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

/**
 * DTO used to update profile information.
 * <br><strong><em>
 *     THis DTO only includes information the API will allow a member to update.
 * </em></strong>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdate {

    @Username
    private String username;
    @Email
    private String email;
    @PhoneNumber
    private String phone;
    private String driversLicense;
    private int income;
    private AddressChangeRequest billingAddress;
    private AddressChangeRequest mailingAddress;

}
