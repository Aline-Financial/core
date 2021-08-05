package com.aline.core.dto.request;

import com.aline.core.validation.annotations.DateOfBirth;
import com.aline.core.validation.annotations.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Member lookup dto.
 * <p>Used to provide a member id when PII is provided.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberLookUp {

    @Name
    @NotBlank(message = "Last name is required.")
    private String lastName;

    @DateOfBirth(minAge = 18, message = "Age must be at least 18.")
    @NotNull(message = "Date of birth is required.")
    private LocalDate dateOfBirth;

    @Size(min = 4, max = 4)
    @NotBlank(message = "Last four of Social Security is required.")
    private String lastFourOfSSN;

}
