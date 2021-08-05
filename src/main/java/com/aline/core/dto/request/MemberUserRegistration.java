package com.aline.core.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class MemberUserRegistration extends UserRegistration {

    @NotNull(message = "Member ID is required.")
    private Long memberId;
}
