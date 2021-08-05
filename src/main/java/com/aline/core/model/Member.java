package com.aline.core.model;

import com.aline.core.model.account.Account;
import com.aline.core.validation.annotations.MembershipId;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j(topic = "Member Entity: ")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_generator")
    @SequenceGenerator(name = "member_generator", sequenceName = "member_sequence")
    private Long id;

    @ManyToOne(optional = false)
    private Branch branch;

    @MembershipId
    @Column(unique = true)
    @Size(min = 10, max = 10)
    private String membershipId;

    @OneToOne(optional = false)
    private Applicant applicant;

    @ManyToMany
    @JoinTable(
            name = "account_holder",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    @JsonManagedReference
    @ToString.Exclude
    private Set<Account> accounts;


    @PostPersist
    public void setMembershipId() {
        setMembershipId(generateMembershipId(getBranch().getId(), getId()));
    }

    private String generateMembershipId(long branchId, long memberId) {
        log.info("Generating membership id from branch {} and member {}...", branchId, memberId);
        String branchSegment = StringUtils.leftPad(String.valueOf(branchId), 4, "0");
        String memberIdSegment = StringUtils.leftPad(String.valueOf(memberId), 6, "0");
        return StringUtils.abbreviate(branchSegment, 4) + StringUtils.abbreviate(memberIdSegment, 6);
    }

}
