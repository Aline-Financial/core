package com.aline.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationResponse {

    /**
     * Application ID
     */
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
     * All applicants that have applied under the referenced application
     */
    @JsonManagedReference
    private LinkedHashSet<ApplicantResponse> applicants;

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

    /**
     * Is true if an account was successfully created in
     * conjunction with the approving the application. It is
     * false otherwise.
     */
    private boolean accountCreated;

}
