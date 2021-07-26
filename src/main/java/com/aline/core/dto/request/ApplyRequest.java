package com.aline.core.dto.request;

import com.aline.core.model.ApplicationType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;

/**
 * DTO used to apply for an Account
 * @apiNote There must be at least 1 applicant in the collection of applicants.
 */
@Data
@Builder
public class ApplyRequest {

    /**
     * Application Type
     * <p>
     *     <em>ie. Checking, Savings, Checking & Savings, etc...</em>
     * </p>
     */
    @NotNull(message = "Application type is required.")
    private ApplicationType applicationType;

    /**
     * Applicants applying under this application.
     * @apiNote Applicants must also include the the primary applicant.
     *          The primary applicant is always the first one in the list.
     */
    @NotNull(message = "Applicants list is required")
    @Size(min = 1, max = 3, message = "There must be at least 1 applicant and at most ${max} applicants.")
    private LinkedHashSet<CreateApplicant> applicants;
}
