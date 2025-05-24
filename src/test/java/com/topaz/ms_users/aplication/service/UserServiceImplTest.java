package com.topaz.ms_users.aplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.topaz.ms_users.application.port.out.UserPersistencePort;
import com.topaz.ms_users.application.service.UserServiceImpl;
import com.topaz.ms_users.domain.model.User;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .build();
    }

    @Test
    void createUser_shoulReturnCreatedUser_whenUsernameDoesNotExist() {

        when(userPersistencePort.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(testUser.getPassword())).thenReturn("encodedPassword");
        when(userPersistencePort.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser);
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertEquals("encodedPassword", createdUser.getPassword());
        verify(userPersistencePort, times(1)).existsByUsername(testUser.getUsername());
        verify(passwordEncoder, times(1)).encode(testUser.getPassword());
        verify(userPersistencePort, times(1)).save(any(User.class));

    }
}
