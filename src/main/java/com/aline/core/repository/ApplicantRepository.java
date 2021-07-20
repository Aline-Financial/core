package com.aline.core.repository;

import com.aline.core.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByDriversLicense(String driversLicense);
    boolean existsBySocialSecurity(String socialSecurity);
}
