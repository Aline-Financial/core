package com.aline.core.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserRegistrationToken {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @NotNull(message = "A token is required.")
    @Column(updatable = false, nullable = false)
    private UUID token;

    @NotNull(message = "A user is required.")
    @OneToOne
    private User user;

    @NotNull(message = "Created date is required.")
    @CreationTimestamp
    private LocalDateTime created;

    @Transient
    private LocalDateTime expiration;

    @PrePersist
    public void setExpirationDate() {
        setExpiration(calculateExpirationDate(created));
    }

    @Transient
    public LocalDateTime calculateExpirationDate(LocalDateTime created) {
        // Set expiration date to 24 hours plus the created date.
        return created.plusHours(24);
    }

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
