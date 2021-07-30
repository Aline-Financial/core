package com.aline.core.model.user;

import com.aline.core.validation.annotations.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue(UserRole.Roles.ADMINISTRATOR)
public class AdminUser extends User {

    @Name
    @NotNull
    private String firstName;

    @Name
    @NotNull
    private String lastName;

    @NotNull
    @Email(message = "'${validatedValue}' is not a valid email.")
    private String email;

    public AdminUser() {
        setEnabled(true);
    }
}
