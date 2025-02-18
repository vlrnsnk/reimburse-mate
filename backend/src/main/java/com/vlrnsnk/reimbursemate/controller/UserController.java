package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.mapper.UserMapper;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users
     *
     * @return List of all users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(UserMapper.toDTOList(users));
    }

    /**
     * Get user by id
     *
     * @param id User id
     * @return User with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        return user.map(value -> ResponseEntity.ok(UserMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new user
     *
     * @param user User to be created
     * @return Created user
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDTO(createdUser));
    }

    /**
     * Update user role
     *
     * @param id User id
     * @param request Request body with new role
     * @return Updated user
     */
    @PatchMapping("/{id}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newRole = request.get("role");

        return userService.updateUserRole(id, newRole)
                .map(updatedUser -> ResponseEntity.ok(UserMapper.toDTO(updatedUser)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete user
     *
     * @param id User id
     * @return No content if user is deleted, not found otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isUserDeleted = userService.deleteUser(id);

        if (isUserDeleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
