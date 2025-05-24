package com.topaz.ms_users.application.port.out;

import java.util.List;
import java.util.Optional;

import com.topaz.ms_users.domain.model.User;

public interface UserPersistencePort {

   User save(User user);
    Optional<User> findById(Long id);
    void deleteById(Long id);
    List<User> findAll();
    boolean existsByUsername(String username);
    
}