package com.aline.core.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * DTO used to apply for an Account
 * @apiNote There must be at least 1 applicant in the collection of applicants.
 */
@Data
@Builder
public class ApplyRequest {

    @NotBlank(message = "Application type is required.")
    private String applicationType;
    
    @NotNull(message = "Applicants list is required")
    @Size(min = 1, max = 3, message = "There must be at least 1 applicant and at most ${max} applicants.")
    private Collection<CreateApplicant> applicants;
}
