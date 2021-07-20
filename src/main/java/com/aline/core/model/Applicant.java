package com.aline.core.model;

import com.aline.core.validation.annotations.Name;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Name
    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    /**
     * Date of birth
     * Stored as {@link LocalDate}.
     *
     * @see LocalDate
     */
    @NotNull
    private LocalDate dateOfBirth;

    /**
     * Email address
     * <p>Must be unique.</p>
     * @see Email
     */
    @Column(unique = true, nullable = false)
    @Email(message = "${validatedValue} is not a valid email.")
    private String email;

    /**
     * Phone number
     * <p>Must be unique.</p>
     * <p>
     *     <em>Ex. (123) 456-7890, +1 321-432-1234</em>
     * </p>
     */
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^(\\+\\d[-\\s.])?\\(?\\d{3}\\)?[-\\s.]\\d{3}[-\\s.]\\d{4}$", message = "${validatedValue} is not a valid phone number.")
    private String phone;

    /**
     * Social Security number
     * <p>Must be unique.</p>
     * <p>
     *     <em>Ex. 123-45-6789</em>
     * </p>
     */
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{4}$", message = "${validatedValue} is not formatted as a Social Security number.")
    private String socialSecurity;

    /**
     * Drivers license number (can vary per state)
     * <p>Must be unique.</p>
     */
    @Column(unique = true, nullable = false)
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
    @NotNull
    @Min(value = 0, message = "You cannot have a negative income.")
    private int income;

    /**
     * Billing address
     * <p>
     *     Must be in street address format.
     * </p>
     * <p>
     *     <em>Ex. 1234 Address St.</em>
     *     <br>or<br>
     *     <em>1234 Street Ln. Apt. 123</em>
     * </p>
     */
    @NotNull
    @Pattern(regexp = "^([0-9]+([a-zA-Z]+)?)\\s(.*)(\\s)([a-zA-Z]+)(\\.)?(\\s(#?(\\w+))|([A-Za-z]+\\.?(\\w+)))?$", message = "${validatedValue} is not a valid address.")
    private String address;

    /**
     * Billing City
     */
    @NotNull
    private String city;

    /**
     * Billing State (USA)
     */
    @NotNull
    private String state;

    /**
     * Billing ZIP code
     * <p>Can use 5 digit zip code or ZIP +4 format.</p>
     * <em>
     *     Ex. <code>12345</code> or <code>12345-1234</code>
     * </em>
     */
    @NotNull
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$")
    private String zipcode;

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

}
