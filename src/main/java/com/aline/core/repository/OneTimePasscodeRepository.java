package com.aline.core.repository;

import com.aline.core.model.OneTimePasscode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OneTimePasscodeRepository extends JpaRepository<OneTimePasscode, Integer> {

    Optional<OneTimePasscode> findByUserId(long id);

}
