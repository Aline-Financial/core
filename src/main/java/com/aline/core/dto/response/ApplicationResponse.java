package com.aline.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationResponse {

    private long id;

    /**
     * Name of the application type:
     * <ul>
     *     <li>Checking</li>
     *     <li>Savings</li>
     *     <li>Checking and Savings</li>
     *     <li>Etc...</li>
     * </ul>
     */
    private String type;

    /**
     * The primary applicant of the application
     */
    private ApplicantResponse primaryApplicant;

    /**
     * All applicants that have applied under the referenced application
     */
    private Set<ApplicantResponse> applicants;

    /**
     * Application Status
     * <p>
     *     Could be: <em>approved, denied, pending</em>
     * </p>
     */
    private String status;

    /**
     * The reason why and application was denied or pending.
     * <p>
     *     <em>This property does not exist if status is approved.</em>
     * </p>
     */
    private String reason;

}
