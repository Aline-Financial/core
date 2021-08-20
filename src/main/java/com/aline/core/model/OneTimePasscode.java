package com.aline.core.model;

import com.aline.core.model.user.User;
import com.aline.core.util.RandomNumberGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import java.security.SecureRandom;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OneTimePasscode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "otp_generator")
    @SequenceGenerator(name = "otp_generator", sequenceName = "otp_sequence", allocationSize = 1)
    private int id;

    private String otp;

    @OneToOne(optional = false)
    private User user;

    @PrePersist
    public void generateOtp() {
        if (otp == null) {
            SecureRandom secureRandom = new SecureRandom();
            val rng = new RandomNumberGenerator(secureRandom);
            otp = rng.generateRandomNumberString(6);
        }
    }

}
