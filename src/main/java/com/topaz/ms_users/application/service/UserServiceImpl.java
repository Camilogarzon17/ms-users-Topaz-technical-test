package com.topaz.ms_users.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.topaz.ms_users.application.port.in.UserServicePort;
import com.topaz.ms_users.application.port.out.UserPersistencePort;
import com.topaz.ms_users.domain.exception.UserNotFoundException;
import com.topaz.ms_users.domain.model.User;

@Service
public class UserServiceImpl implements UserServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserPersistencePort userPersistencePort, PasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        if (userPersistencePort.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userPersistencePort.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User existingUser = userPersistencePort.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        existingUser.setUsername(userDetails.getUsername());
        // Solo actualizar contraseña si se provee una nueva
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userPersistencePort.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userPersistencePort.findById(id).isPresent()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userPersistencePort.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userPersistencePort.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.findAll();
    }
}
