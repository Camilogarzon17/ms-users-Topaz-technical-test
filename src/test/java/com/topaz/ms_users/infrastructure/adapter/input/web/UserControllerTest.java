package com.topaz.ms_users.infrastructure.adapter.input.web;

import com.topaz.ms_users.application.port.in.UserServicePort;
import com.topaz.ms_users.domain.exception.UserNotFoundException;
import com.topaz.ms_users.domain.model.User;
import com.topaz.ms_users.domain.model.dto.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    void toDTO_shouldReturnUserDTO_whenUserIsValid() {
        UserDTO dto = userController.toDTO(user);

        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
    }

    @Test
    void toDTO_shouldReturnNull_whenUserIsNull() {
        UserDTO dto = userController.toDTO(null);

        assertNull(dto);
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        when(userServicePort.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        UserDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(user.getId(), responseBody.getId());
        assertEquals(user.getUsername(), responseBody.getUsername());
        verify(userServicePort, times(1)).createUser(user);
    }

    @Test
    void getUserById_shouldReturnUser_whenFound() {
        when(userServicePort.getUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(user.getId(), responseBody.getId());
        assertEquals(user.getUsername(), responseBody.getUsername());
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

        ResponseEntity<UserDTO> response = userController.updateUser(1L, updated);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(updated.getId(), responseBody.getId());
        assertEquals(updated.getUsername(), responseBody.getUsername());
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

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

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
