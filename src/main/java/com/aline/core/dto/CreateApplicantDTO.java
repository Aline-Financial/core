package com.aline.core.dto;

import com.aline.core.validation.annotations.Address;
import com.aline.core.validation.annotations.DateOfBirth;
import com.aline.core.validation.annotations.Gender;
import com.aline.core.validation.annotations.Name;
import com.aline.core.validation.annotations.PhoneNumber;
import com.aline.core.validation.annotations.SocialSecurity;
import com.aline.core.validation.annotations.Zipcode;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO to create an applicant
 * <p>Uses custom validators:</p>
 * <ul>
 *     <li>{@link Name}</li>
 *     <li>{@link DateOfBirth}</li>
 *     <li>{@link Gender}</li>
 *     <li>{@link PhoneNumber}</li>
 *     <li>{@link SocialSecurity}</li>
 *     <li>{@link Address}</li>
 *     <li>{@link Zipcode}</li>
 * </ul>
 */
@Data
public class CreateApplicantDTO implements Serializable {

    /**
     * First name
     * <p>
     *     Validated by {@link Name}
     * </p>
     */
    @Name(message = "'${validatedValue}' is not a valid name.")
    @NotBlank(message = "First name must not be blank.")
    @NotNull(message = "First name is required.")
    private String firstName;

    /**
     * Middle name
     * <p>
     *     Validated by {@link Name}
     * </p>
     */
    @Name(message = "'${validatedValue}' is not a valid name.")
    @Nullable
    private String middleName;

    /**
     * Last name
     * <p>
     *     Validated by {@link Name}
     * </p>
     */
    @Name(message = "'${validatedValue}' is not a valid name.")
    @NotBlank(message = "Last name must not be blank.")
    @NotNull(message = "Last name is required.")
    private String lastName;


    /**
     * Date of birth
     * <p>
     *     Validated by {@link DateOfBirth}
     * </p>
     * <p>
     *     <em>Age must be 18 years or older.</em>
     * </p>
     */
    @DateOfBirth(minAge = 18, message = "Age must be at least 18 years.")
    private LocalDate dateOfBirth;

    /**
     * Gender
     * <p>
     *     Validated by {@link Gender}
     * </p>
     * <p>Must be one of the values:</p>
     * <ul>
     *     <li>Male</li>
     *     <li>Female</li>
     *     <li>Other</li>
     *     <li>Not Specified</li>
     * </ul>
     */
    @Gender(message = "'${validatedValue}' is not a valid value.")
    private String gender;

    /**
     * Email
     * <p>Validated by {@link Email}</p>
     */
    @Email(message = "'${validatedValue}' is not a valid email address.")
    @NotNull(message = "Email is required.")
    @NotBlank(message = "Email must not be blank.")
    private String email;

    /**
     * Phone number
     * <p>Validated by {@link PhoneNumber}</p>
     */
    @PhoneNumber
    @NotNull(message = "Phone number is required.")
    @NotBlank(message = "Phone number must not be blank.")
    private String phone;

    /**
     * Social Security number
     * <p>
     *     Validated by {@link SocialSecurity}
     * </p>
     */
    @SocialSecurity
    private String socialSecurity;

    /**
     * Driver's license number (can vary per state)
     */
    @NotNull(message = "Driver's license is not valid.")
    @NotBlank(message = "Driver's license must not be blank.")
    private String driversLicense;

    /**
     * Income (in cents)
     * <p>
     *     <em>Using int for precision.</em>
     * </p>
     * <p>Cannot be negative.</p>
     */
    @Min(value = 0, message = "You cannot have a negative income.")
    private int income;

    /**
     * Billing Address
     * <p>Validated by {@link Address}</p>
     */
    @Address(message = "'${validatedValue}' is not a valid address.")
    @NotNull(message = "Address is required.")
    @NotBlank(message = "Address must not be blank.")
    private String address;

    /**
     * Billing City
     */
    @NotNull(message = "City is required.")
    @NotBlank(message = "City must not be blank.")
    private String city;

    /**
     * Billing State
     */
    @NotNull(message = "State is required.")
    @NotBlank(message = "State must not be blank.")
    private String state;

    /**
     * Billing ZIP code
     * <p>Validated by {@link Zipcode}</p>
     */
    @Zipcode(message = "'${validatedValue}' is not in a valid zipcode format.")
    @NotNull(message = "Zipcode is required.")
    @NotBlank(message = "Zipcode must not be null.")
    private String zipcode;

    /**
     * Mailing Address
     * <p>Validated by {@link Address}</p>
     */
    @Address(message = "'${validatedValue}' is not a valid address.")
    @NotNull(message = "Address is required.")
    @NotBlank(message = "Address must not be blank.")
    private String mailingAddress;

    /**
     * Mailing City
     */
    @NotNull(message = "City is required.")
    @NotBlank(message = "City must not be blank.")
    private String mailingCity;

    /**
     * Mailing State
     */
    @NotNull(message = "State is required.")
    @NotBlank(message = "State must not be blank.")
    private String mailingState;

    /**
     * Mailing ZIP code
     * <p>Validated by {@link Zipcode}</p>
     */
    @Zipcode(message = "'${validatedValue}' is not in a valid zipcode format.")
    @NotNull(message = "Zipcode is required.")
    @NotBlank(message = "Zipcode must not be null.")
    private String mailingZipcode;

}
