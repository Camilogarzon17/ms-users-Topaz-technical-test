package com.topaz.ms_users.infrastructure.adapter.output.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.topaz.ms_users.domain.model.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}