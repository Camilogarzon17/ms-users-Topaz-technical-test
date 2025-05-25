package com.topaz.ms_users.infrastructure.adapter.input.web;

import com.topaz.ms_users.application.port.in.UserServicePort;
import com.topaz.ms_users.domain.exception.UserNotFoundException;
import com.topaz.ms_users.domain.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private AutoCloseable closeable;

    @Mock
    private UserServicePort userServicePort;

    @InjectMocks
    private UserController userController;


    private User user;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        user = User.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .build();
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        when(userServicePort.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userServicePort, times(1)).createUser(user);
    }

    @Test
    void getUserById_shouldReturnUser_whenFound() {
        when(userServicePort.getUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userServicePort, times(1)).getUserById(1L);
    }

    @Test
    void getUserById_shouldThrowException_whenNotFound() {
        when(userServicePort.getUserById(2L)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> userController.getUserById(2L));
        assertTrue(ex.getMessage().contains("User with id 2 not found"));
        verify(userServicePort, times(1)).getUserById(2L);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        User updated = User.builder().id(1L).username("updated").password("pass").build();
        when(userServicePort.updateUser(eq(1L), any(User.class))).thenReturn(updated);

        ResponseEntity<User> response = userController.updateUser(1L, updated);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
        verify(userServicePort, times(1)).updateUser(1L, updated);
    }

    @Test
    void deleteUser_shouldReturnNoContent() {
        doNothing().when(userServicePort).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(userServicePort, times(1)).deleteUser(1L);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        List<User> users = Arrays.asList(user, User.builder().id(2L).username("another").password("pass").build());
        when(userServicePort.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userServicePort, times(1)).getAllUsers();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }
}
