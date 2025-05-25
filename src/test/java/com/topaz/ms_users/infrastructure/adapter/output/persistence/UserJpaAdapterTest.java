package com.topaz.ms_users.infrastructure.adapter.output.persistence;

import com.topaz.ms_users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserJpaAdapterTest {

    private UserJpaRepository userJpaRepository;
    private UserJpaAdapter userJpaAdapter;

    @BeforeEach
    void setUp() {
        userJpaRepository = mock(UserJpaRepository.class);
        userJpaAdapter = new UserJpaAdapter(userJpaRepository);
    }

    @Test
    void save_shouldReturnSavedUser() {
        User user = new User();
        when(userJpaRepository.save(user)).thenReturn(user);

        User result = userJpaAdapter.save(user);

        assertEquals(user, result);
        verify(userJpaRepository).save(user);
    }

    @Test
    void findById_shouldReturnUserOptional() {
        User user = new User();
        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userJpaAdapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userJpaRepository).findById(1L);
    }

    @Test
    void deleteById_shouldCallRepository() {
        userJpaAdapter.deleteById(1L);

        verify(userJpaRepository).deleteById(1L);
    }

    @Test
    void findAll_shouldReturnUserList() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userJpaRepository.findAll()).thenReturn(users);

        List<User> result = userJpaAdapter.findAll();

        assertEquals(users, result);
        verify(userJpaRepository).findAll();
    }

    @Test
    void existsByUsername_shouldReturnTrueIfExists() {
        when(userJpaRepository.existsByUsername("test")).thenReturn(true);

        boolean exists = userJpaAdapter.existsByUsername("test");

        assertTrue(exists);
        verify(userJpaRepository).existsByUsername("test");
    }

    @Test
    void existsByUsername_shouldReturnFalseIfNotExists() {
        when(userJpaRepository.existsByUsername("notfound")).thenReturn(false);

        boolean exists = userJpaAdapter.existsByUsername("notfound");

        assertFalse(exists);
        verify(userJpaRepository).existsByUsername("notfound");
    }
}
