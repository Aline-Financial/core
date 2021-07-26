package com.aline.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(AccountType.Values.CHECKING)
public class CheckingAccount extends Account {

    /**
     * Available balance of the checking account.
     */
    private int availableBalance;
}
