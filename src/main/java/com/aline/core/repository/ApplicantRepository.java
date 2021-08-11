package com.aline.core.repository;

import com.aline.core.model.Applicant;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepositoryWithSpecification<Applicant, Long> {

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByDriversLicense(String driversLicense);
    boolean existsBySocialSecurity(String socialSecurity);
}
