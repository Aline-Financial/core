package com.aline.core.dto.request;

import com.aline.core.model.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;

/**
 * DTO used to apply for an Account
 * @apiNote There must be at least 1 applicant in the collection of applicants.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
     * If <code>noApplicants</code> is true, the
     * applicants array will not be used to create
     * new applicants. This means that the applicants
     * have to be inserted manually for it to be submitted.
     */
    @Nullable
    private Boolean noApplicants;

    /**
     * If <code>noApplicants</code> is set to true. It will use
     * the <code>applicantIds</code> property to attach existing
     * applicants to the application.
     */
    @Nullable
    private LinkedHashSet<Long> applicantIds;

    /**
     * Applicants applying under this application.
     * @apiNote Applicants must also include the the primary applicant.
     *          The primary applicant is always the first one in the list.
     */
    @Size(max = 3, message = "There must be  at most ${max} applicants.")
    private LinkedHashSet<CreateApplicant> applicants;
}
