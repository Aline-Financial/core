package com.aline.core.model;

import com.aline.core.validation.annotations.Address;
import com.aline.core.validation.annotations.DateOfBirth;
import com.aline.core.validation.annotations.Gender;
import com.aline.core.validation.annotations.Name;
import com.aline.core.validation.annotations.PhoneNumber;
import com.aline.core.validation.annotations.SocialSecurity;
import com.aline.core.validation.annotations.Zipcode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Applicant Model
 * <p>
 *     JPA Entity that represents an applicant.
 * </p>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * First name
     * <p>Uses custom name validator.</p>
     *
     * @see Name
     */
    @Name(message = "'${validatedValue}' is not a valid name.")
    @NotBlank(message = "First name is required.")
    @NonNull
    private String firstName;

    /**
     * Middle name
     * <p><em>Not required.</em></p>
     * <p>Uses custom name validator.</p>
     *
     * @see Name
     */
    @Name(message = "'${validatedValue}' is not a valid name.")
    private String middleName;

    /**
     * Last name
     * <p>Uses custom name validator.</p>
     *
     * @see Name
     */
    @Name(message = "'${validatedValue}' is not a valid name.")
    @NotBlank(message = "Last name is required.")
    @NonNull
    private String lastName;

    /**
     * Date of birth
     * <p>Stored as {@link LocalDate}.</p>
     * <p>Uses custom date of birth validator.</p>
     *
     * @see DateOfBirth
     * @see LocalDate
     */
    @DateOfBirth(minAge = 18, message = "Age must be at least 18.")
    @NotNull(message = "Date of birth is required.")
    @NonNull
    private LocalDate dateOfBirth;

    /**
     * Gender
     * <p>Can be one of the following values (case-insensitive):</p>
     * <p><em>Valid values are determined for database uniformity.</em></p>
     * <ul>
     *     <li>Male</li>
     *     <li>Female</li>
     *     <li>Other</li>
     *     <li>Not Specified</li>
     * </ul>
     */
    @Gender(message = "'${validatedValue}' is not an allowed value.")
    @NotBlank(message = "Gender is required.")
    @NonNull
    private String gender;

    /**
     * Email address
     * <p>Must be unique.</p>
     * @see Email
     */
    @Column(unique = true)
    @Email(message = "'${validatedValue}' is not a valid email.")
    @NotBlank(message = "Email is required.")
    @NonNull
    private String email;

    /**
     * Phone number
     * <p>Must be unique.</p>
     * <p>
     *     <em>Ex. (123) 456-7890, +1 321-432-1234</em>
     * </p>
     */
    @Column(unique = true)
    @NotBlank(message = "Phone number is required.")
    @PhoneNumber
    @NonNull
    private String phone;

    /**
     * Social Security number
     * <p>Must be unique.</p>
     * <p>
     *     <em>Ex. 123-45-6789</em>
     * </p>
     */
    @Column(unique = true)
    @NotBlank(message = "Social Security is required.")
    @SocialSecurity
    @NonNull
    private String socialSecurity;

    /**
     * Drivers license number (can vary per state)
     * <p>Must be unique.</p>
     */
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Driver's license is invalid.")
    @NonNull
    private String driversLicense;

    /**
     * Income (in cents)
     * <p>
     *     <em>Using int for precision.</em>
     * </p>
     * <p>
     *     Income cannot be less than 0.
     * </p>
     */
    @Min(value = 0, message = "You cannot have a negative income.")
    private int income;

    /**
     * Billing address
     * <p>
     *     Must be in street address format.
     * </p>
     * <p>
     *     Uses custom address validator.
     * </p>
     * <p>
     *     <em>Ex. 1234 Address St.</em>
     *     <br>or<br>
     *     <em>1234 Street Ln. Apt. 123</em>
     * </p>
     * @see Address
     */
    @NotBlank(message = "Address is required.")
    @Address(message = "'${validatedValue}' is not a valid address.")
    @NonNull
    private String address;

    /**
     * Billing City
     */
    @NotBlank(message = "City is required.")
    @NonNull
    private String city;

    /**
     * Billing State (USA)
     */
    @NotBlank(message = "State is required.")
    @NonNull
    private String state;

    /**
     * Billing ZIP code
     * <p>Can use 5 digit zip code or ZIP +4 format.</p>
     * <em>
     *     Ex. <code>12345</code> or <code>12345-1234</code>
     * </em>
     */
    @NotBlank(message = "Zipcode is required.")
    @Zipcode(message = "'${validatedValue}' is not in a valid zipcode format.")
    @NonNull
    private String zipcode;


    /**
     * Mailing address
     * <p>
     *     Must be in street address format.
     * </p>
     * <p>
     *     Uses custom address validator.
     * </p>
     * <p>
     *     <em>Ex. 1234 Address St.</em>
     *     <br>or<br>
     *     <em>1234 Street Ln. Apt. 123</em>
     * </p>
     * <em>Address Type: MAILING</em>
     * @see Address
     * @see Address.Type
     */
    @NotBlank(message = "Mailing address is required.")
    @Address(message = "'${validatedValue}' is not a valid address.", type = Address.Type.MAILING)
    @NonNull
    private String mailingAddress;

    /**
     * Mailing City
     */
    @NotBlank(message = "Mailing city is required.")
    @NonNull
    private String mailingCity;

    /**
     * Mailing State (USA)
     */
    @NotBlank(message = "Mailing state is required.")
    @NonNull
    private String mailingState;

    /**
     * Mailing ZIP code
     * <p>Can use 5 digit zip code or ZIP +4 format.</p>
     * <em>
     *     Ex. <code>12345</code> or <code>12345-1234</code>
     * </em>
     */
    @NotBlank(message = "Mailing zipcode is required.")
    @Zipcode(message = "'${validatedValue}' is not in a valid zipcode format.")
    @NonNull
    private String mailingZipcode;

    /**
     * Timestamp for the last time this entity was modified.
     */
    @UpdateTimestamp
    private LocalDateTime lastModifiedAt;

    /**
     * Timestamp for when the entity was first created.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Applicant applicant = (Applicant) o;
        return firstName.equals(applicant.firstName) && lastName.equals(applicant.lastName) && dateOfBirth.equals(applicant.dateOfBirth) && email.equals(applicant.email) && phone.equals(applicant.phone) && socialSecurity.equals(applicant.socialSecurity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, email, phone, socialSecurity);
    }
}
