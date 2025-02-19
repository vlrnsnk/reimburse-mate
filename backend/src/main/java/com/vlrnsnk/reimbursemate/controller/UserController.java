package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.model.Reimbursement;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.service.ReimbursementService;
import com.vlrnsnk.reimbursemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final ReimbursementService reimbursementService;

    @Autowired
    public UserController(UserService userService, ReimbursementService reimbursementService) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
    }

    /**
     * Get all users
     *
     * @return List of all users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    /**
     * Get user by id
     *
     * @param userId User id
     * @return User with the given id
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
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
        UserDTO createdUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Update user role
     *
     * @param userId User id
     * @param request Request body with new role
     * @return Updated user
     */
    @PatchMapping("/{userId}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        String newRole = request.get("role");

        return userService.updateUserRole(userId, newRole)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete user
     *
     * @param userId User id
     * @return No content if user is deleted, not found otherwise
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean isUserDeleted = userService.deleteUser(userId);

        if (isUserDeleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Get all reimbursements of a user
     *
     * @param userId User id
     * @param status Reimbursement status
     * @return List of reimbursements
     */
    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<List<ReimbursementDTO>> getUserReimbursements(@PathVariable Long userId, @RequestParam(required = false) String status) {
        if (status == null) {
            List<ReimbursementDTO> reimbursements = reimbursementService.getReimbursementsByUserId(userId);

            return ResponseEntity.ok(reimbursements);
        }

        try {
            Reimbursement.Status reimbursementStatus = Reimbursement.Status.valueOf(status.toUpperCase());
            List<ReimbursementDTO> reimbursements = reimbursementService.getReimbursementsByUserIdAndStatus(userId, reimbursementStatus);

            return ResponseEntity.ok(reimbursements);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Create a new reimbursement
     *
     * @param userId User id
     * @param reimbursementDTO Reimbursement to be created
     * @return Created reimbursement
     */
    @PostMapping("/{userId}/reimbursements")
    public ResponseEntity<ReimbursementDTO> createReimbursement(@PathVariable Long userId, @RequestBody ReimbursementDTO reimbursementDTO) {
        User user = userService.getUserEntityById(userId).orElse(null);
        ReimbursementDTO createdReimbursement = reimbursementService.createReimbursement(user, reimbursementDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdReimbursement);
    }

}
