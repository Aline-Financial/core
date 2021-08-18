package com.aline.core.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * The User Registration Token entity is used to
 * verify a user's registration. By default, the entity
 * is set to expire 24 hours after it is created.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserRegistrationToken {

    /**
     * The primary key is a UUID token as the
     * tokens do not need to be queried any other way
     * but with the UUID. This provides an extra layer
     * of difficulty to find users that aren't yet
     * registered.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @NotNull(message = "A token is required.")
    @Column(updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private UUID token;

    /**
     * The user that this token is associated with.
     * This relationship is defined as One-to-one.
     */
    @NotNull(message = "A user is required.")
    @OneToOne
    private User user;

    /**
     * How long it takes until this token expires
     * from its creation date in seconds.
     */
    private long expirationDelay;

    /**
     * The date and time this token was created.
     */
    @CreationTimestamp
    private LocalDateTime created;

    /**
     * The expiration date of the
     */
    @Transient
    private LocalDateTime expiration;

    /**
     * Set the expiration date of the token after
     * it is persisted and/or updated.
     */
    @PostPersist
    @PostUpdate
    public void setExpirationDate() {
        setExpiration(calculateExpirationDate(created));
    }

    /**
     * Calculates the expiration date from a passed
     * created date time.
     * @param created The created date time.
     * @return A local date time that represents the expiration date relative
     * to the created date.
     */
    @Transient
    public LocalDateTime calculateExpirationDate(LocalDateTime created) {
        // If expiration delay is 0, less than 0, or more than 24 hours, set the default to 24 hours.
        if (expirationDelay <= 0 || expirationDelay > 86400)
            setExpirationDelay(86400);
        // Set expiration date to 24 hours plus the created date.
        return created.plusSeconds(getExpirationDelay());
    }

    /**
     * A transient method to check if the token is expired.
     * @return  A boolean representing the expiration status of the token.
     */
    @Transient
    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(getExpiration());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationToken that = (UserRegistrationToken) o;
        return  token.equals(that.token) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, user);
    }
}
