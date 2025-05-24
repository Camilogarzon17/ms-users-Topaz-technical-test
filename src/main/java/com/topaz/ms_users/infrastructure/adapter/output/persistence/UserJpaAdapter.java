package com.topaz.ms_users.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.topaz.ms_users.application.port.out.UserPersistencePort;
import com.topaz.ms_users.domain.model.User;

@Component
public class UserJpaAdapter implements UserPersistencePort {

    private final UserJpaRepository userJpaRepository;

    public UserJpaAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
}
