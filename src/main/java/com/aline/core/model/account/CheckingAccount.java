package com.aline.core.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Checking Account class.
 * <p>
 *     Derived from {@link Account}.
 * </p>
 * <p>
 *     This class includes the available balance as well
 *     as this is the main account for a member to spend from.
 * </p>
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue(AccountType.Values.CHECKING)
public class CheckingAccount extends Account {

    /**
     * Available balance of the checking account.
     */
    private int availableBalance;
}
