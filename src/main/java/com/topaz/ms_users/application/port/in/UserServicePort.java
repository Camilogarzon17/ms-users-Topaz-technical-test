package com.topaz.ms_users.application.port.in;

import java.util.List;
import java.util.Optional;

import com.topaz.ms_users.domain.model.User;

public interface UserServicePort {

    User createUser(User user);
    Optional<User> getUserById(Long id);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    List<User> getAllUsers();
    
}