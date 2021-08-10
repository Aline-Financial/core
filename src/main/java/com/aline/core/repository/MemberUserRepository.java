package com.aline.core.repository;

import com.aline.core.model.user.MemberUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Optional;

/**
 * User repository specifically for member users.
 * @apiNote This is mostly used for retrieval.
 * @see UserRepository See UserRepository for saving.
 */
@Repository
public interface MemberUserRepository extends IUserRepository<MemberUser> {

    @Query("SELECT u FROM MemberUser u WHERE u.member.applicant.email = ?1")
    Optional<MemberUser> findByEmail(@Email String email);

    @Query("SELECT u FROM MemberUser u WHERE u.member.applicant.lastName = ?1" +
            " AND u.member.applicant.dateOfBirth = ?2" +
            " AND u.member.applicant.socialSecurity = ?3")
    Optional<MemberUser> findByPII(String lastName, LocalDate dateOfBirth, String socialSecurity);

}
