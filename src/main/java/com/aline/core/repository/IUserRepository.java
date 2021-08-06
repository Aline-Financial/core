package com.aline.core.repository;

import com.aline.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * IUserRepository is to be inherited by
 * other user repository interfaces.
 * @param <T> User model
 */
@NoRepositoryBean
public interface IUserRepository<T extends User> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    boolean existsByUsername(String username);

    Optional<T> findByUsername(String username);

}
