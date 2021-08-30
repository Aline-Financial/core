package com.aline.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * This DTO is the model for account
 * resource payload that is returned by
 * the API.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {

    private long id;
    private String accountNumber;
    private String status;
    private int availableBalance;
    private float apy;

}
