package com.aline.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 *  User Entity
 *  <p> User class for authentication </p>
 */
@Data
@Entity
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean enabled;

    public User() {
        super();
        this.enabled = false;
    }

}
