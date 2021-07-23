package com.aline.core.model;

import com.aline.core.validation.annotations.Address;
import com.aline.core.validation.annotations.Zipcode;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Bank Model
 * <p>
 *     JPA Entity that represents a Bank.
 * </p>
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Routing Id;
     */
    @NotNull
    private Integer routingId;

    /**
     * Physical Address for Bank
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
    @NotNull
    @NotBlank(message = "Address is required.")
    @Address(message = "'${validatedValue}' is not a valid address.")
    private String address;

    /**
     * City of bank
     */
    @NotNull
    @NotBlank(message = "City is required.")
    private String city;

    /**
     * State of Bank
     */
    @NotNull
    @NotBlank(message = "State is required.")
    private String state;

    /**
     * Zipcode
     * <p>Can use 5 digit zip code or ZIP +4 format.</p>
     * <em>
     *     Ex. <code>12345</code> or <code>12345-1234</code>
     * </em>
     * @see Zipcode
     */
    @NotBlank(message = "Zipcode is required.")
    @Zipcode(message = "'${validatedValue}' is not in a valid zipcode format.")
    @NonNull
    private String zipcode;

}
