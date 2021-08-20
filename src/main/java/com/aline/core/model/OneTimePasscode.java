package com.aline.core.model;

import com.aline.core.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class OneTimePasscode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "otp_generator")
    @SequenceGenerator(name = "otp_generator", sequenceName = "otp_sequence", allocationSize = 1)
    private int id;

    private String otp;

    @OneToOne(optional = false)
    private User user;

}
