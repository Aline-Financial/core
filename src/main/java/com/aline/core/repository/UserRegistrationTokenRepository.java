package com.aline.core.repository;

import com.aline.core.model.user.User;
import com.aline.core.model.user.UserRegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRegistrationTokenRepository extends JpaRepository<UserRegistrationToken, Integer> {

    @Query("SELECT u FROM User u LEFT JOIN UserRegistrationToken ut " +
            "ON u.id = ut.user.id " +
            "WHERE ut.token = ?1")
    Optional<User> findUserByToken(String token);

}
