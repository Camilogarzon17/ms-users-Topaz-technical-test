package com.topaz.ms_users.infrastructure.adapter.input.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topaz.ms_users.application.port.in.UserServicePort;
import com.topaz.ms_users.domain.model.User;
import com.topaz.ms_users.domain.model.dto.UserDTO;
import com.topaz.ms_users.domain.exception.UserNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("topaz/v1/api/users")
@Tag(name = "User Management", description = "API for user management")
public class UserController {

    private final UserServicePort userServicePort;

    public UserDTO toDTO(User user) {
        if (user == null)
            return null;
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .build();
    }

    public UserController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    @Operation(summary = "Create a new user", responses = {
            @ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user) {
        User createdUser = userServicePort.createUser(user);
        return new ResponseEntity<>(toDTO(createdUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Get user by ID", responses = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userServicePort.getUserById(id)
                .map(user -> new ResponseEntity<>(toDTO(user), HttpStatus.OK))
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Operation(summary = "Update an existing user", responses = {
            @ApiResponse(responseCode = "200", description = "User successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        User updatedUser = userServicePort.updateUser(id, userDetails);
        return new ResponseEntity<>(toDTO(updatedUser), HttpStatus.OK);
    }

    @Operation(summary = "Delete a user by ID", responses = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userServicePort.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all users", responses = {
            @ApiResponse(responseCode = "200", description = "List of users", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    })

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userServicePort.getAllUsers();
        List<UserDTO> userDTOs = users.stream().map(this::toDTO).toList();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }
}
