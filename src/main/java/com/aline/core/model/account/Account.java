package com.aline.core.model.account;

import com.aline.core.model.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Account class
 * <p>
 *     Other account types will inherit from this class.
 * </p>
 */
@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "account_sequence")
    private Long id;



    private String accountNumber;

    /**
     * Current account status.
     * <p>
     *     Specifies whether the account is active, not active, or another state.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    /**
     * The primary account holder.
     */
    @ManyToOne(optional = false)
    @NotNull(message = "Primary account holder is required.")
    private Member primaryAccountHolder;

    /**
     * Balance of the account.
     */
    private int balance;

    /**
     * Members that hold this account.
     */
    @ManyToMany(mappedBy = "accounts")
    @JsonBackReference
    private Set<Member> members;

}
