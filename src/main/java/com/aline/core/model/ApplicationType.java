package com.aline.core.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
public class ApplicationType extends ValueType {
    public ApplicationType(Integer id, @NotNull(message = "Type name is required.") String name) {
        super(id, name);
    }
}
