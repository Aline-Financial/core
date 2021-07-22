package com.aline.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

/**
 * Application entity
 * <p>
 *     This class houses all the applicants that are applying for the same account.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Application Type
     * <p>Can be either:</p>
     * <ul>
     *     <li>Checking</li>
     *     <li>Savings</li>
     *     <li>Credit Card</li>
     *     <li>Loan</li>
     * </ul>
     */
    @ManyToOne(optional = false)
    private ApplicationType applicationType;

    /**
     * Applicants that have applied under this application.
     */
    @ManyToMany
    @JoinTable(
            name = "application_applicants",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "applicant_id")
    )
    @ToString.Exclude
    private Set<Applicant> applicants;
}
