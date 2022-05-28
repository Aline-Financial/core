package com.aline.core.model.card;

import com.aline.core.model.Member;
import com.aline.core.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member cardHolder;

    @ManyToOne(optional = false)
    private Account account;

    @NotNull
    private String cardNumber;

    @NotNull
    private Date expirationDate;

    @NotNull
    @Length(min = 3, max = 3)
    @Pattern(regexp = "\\d{3}")
    private String securityCode;
}
