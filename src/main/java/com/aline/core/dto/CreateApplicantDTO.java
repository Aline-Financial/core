package com.aline.core.dto;

import com.aline.core.validation.annotations.Name;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * DTO to create an applicant
 * <p>Uses custom validators:</p>
 * <ul>
 *     <li>{@link Name}</li>
 *     <li>{@link }</li>
 * </ul>
 */
@Data
public class CreateApplicantDTO {

    @Name(message = "'${validatedValue}' is not a valid name.")
    @NotNull(message = "First name is required.")
    private String firstName;

    @Name(message = "'${validatedValue}' is not a valid name.")
    private String middleName;

    @Name(message = "'${validatedValue}' is not a valid name.")
    @NotNull(message = "Last name is required.")
    private String lastName;

}
