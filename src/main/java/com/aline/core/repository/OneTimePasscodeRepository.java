package com.aline.core.repository;

import com.aline.core.model.OneTimePasscode;
import com.aline.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OneTimePasscodeRepository extends JpaRepository<OneTimePasscode, Integer> {

    Optional<OneTimePasscode> findByOtp(String otp);
    Optional<OneTimePasscode> findByUser(User user);

}
