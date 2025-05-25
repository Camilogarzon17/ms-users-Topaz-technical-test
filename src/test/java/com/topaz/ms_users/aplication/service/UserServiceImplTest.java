package com.topaz.ms_users.aplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.topaz.ms_users.application.port.out.UserPersistencePort;
import com.topaz.ms_users.application.service.UserServiceImpl;
import com.topaz.ms_users.domain.exception.UserNotFoundException;
import com.topaz.ms_users.domain.model.User;

import java.util.Arrays;
import java.util.Optional;

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

    @Test
    void createUser_shouldThrowException_whenUsernameAlreadyExists() {

        when(userPersistencePort.existsByUsername(testUser.getUsername())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(testUser));
        verify(userPersistencePort, times(1)).existsByUsername(testUser.getUsername());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getUsername(), foundUser.get().getUsername());
        verify(userPersistencePort, times(1)).findById(1L);
    }

    @Test
    void getUserById_shouldReturnEmpty_whenUserDoesNotExist() {
        when(userPersistencePort.findById(2L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(2L);

        assertFalse(foundUser.isPresent());
        verify(userPersistencePort, times(1)).findById(2L);
    }

    @Test
    void updateUser_shouldUpdateUser_whenUserExists() {
        User updatedDetails = User.builder().username("updateduser").password("newpassword").build();
        User existingUser = User.builder().id(1L).username("olduser").password("oldencoded").build();

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newpassword")).thenReturn("newencoded");
        when(userPersistencePort.save(any(User.class))).thenReturn(existingUser); // Mock the save operation

        User result = userService.updateUser(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("updateduser", result.getUsername());
        assertEquals("newencoded", result.getPassword());
        verify(userPersistencePort, times(1)).findById(1L);
        verify(passwordEncoder, times(1)).encode("newpassword");
        verify(userPersistencePort, times(1)).save(existingUser);
    }

    @Test
    void updateUser_shouldNotUpdatePassword_whenNewPasswordIsNull() {
        User updatedDetails = User.builder().username("updateduser").password(null).build();
        User existingUser = User.builder().id(1L).username("olduser").password("oldencoded").build();

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userPersistencePort.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("updateduser", result.getUsername());
        assertEquals("oldencoded", result.getPassword()); // Password should remain unchanged
        verify(userPersistencePort, times(1)).findById(1L);
        verify(passwordEncoder, never()).encode(anyString()); // passwordEncoder should not be called
        verify(userPersistencePort, times(1)).save(existingUser);
    }

    @Test
    void updateUser_shouldNotUpdatePassword_whenNewPasswordIsEmpty() {
        User updatedDetails = User.builder().username("updateduser").password("").build();
        User existingUser = User.builder().id(1L).username("olduser").password("oldencoded").build();

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userPersistencePort.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("updateduser", result.getUsername());
        assertEquals("oldencoded", result.getPassword());
        verify(userPersistencePort, times(1)).findById(1L);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userPersistencePort, times(1)).save(existingUser);
    }

    @Test
    void updateUser_shouldThrowException_whenUserDoesNotExist() {
        User updatedDetails = User.builder().username("updateduser").build();
        when(userPersistencePort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, updatedDetails));
        verify(userPersistencePort, times(1)).findById(1L);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExists() {
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userPersistencePort).deleteById(1L);

        userService.deleteUser(1L);

        verify(userPersistencePort, times(1)).findById(1L);
        verify(userPersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowException_whenUserDoesNotExist() {
        when(userPersistencePort.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(2L));
        verify(userPersistencePort, times(1)).findById(2L);
        verify(userPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        List<User> users = Arrays.asList(testUser, User.builder().id(2L).username("another").password("pass").build());
        when(userPersistencePort.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userPersistencePort, times(1)).findAll();
    }
}