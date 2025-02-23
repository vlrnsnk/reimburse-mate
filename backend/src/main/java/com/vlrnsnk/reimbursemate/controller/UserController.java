package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.aop.RequiresRole;
import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.dto.UserProfileUpdateDTO;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.service.ReimbursementService;
import com.vlrnsnk.reimbursemate.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    @RequiresRole(User.Role.MANAGER)
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
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId, HttpSession session) {
        UserDTO userDTO = userService.getUserById(userId, session);

        return ResponseEntity.ok(userDTO);
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
    @RequiresRole(User.Role.MANAGER)
    @PatchMapping("/{userId}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        String newRole = request.get("role");
        UserDTO updatedUser = userService.updateUserRole(userId, newRole);

        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user
     *
     * @param userId User id
     * @return No content if user is deleted, not found otherwise
     */
    @Transactional
    @RequiresRole(User.Role.MANAGER)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Get all reimbursements of a user
     *
     * @param userId User id
     * @param status Reimbursement status
     * @return List of reimbursements
     */
    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<List<ReimbursementDTO>> getUserReimbursements(
            @PathVariable Long userId,
            @RequestParam(required = false) String status,
            HttpSession session
    ) {
        List<ReimbursementDTO> reimbursements =
                (status == null)
                        ? reimbursementService.getReimbursementsByUserId(userId, session)
                        : reimbursementService.getReimbursementsByUserIdAndStatus(userId, status, session);

        return ResponseEntity.ok(reimbursements);
    }

    /**
     * Create a new reimbursement
     *
     * @param userId User id
     * @param reimbursementDTO Reimbursement to be created
     * @return Created reimbursement
     */
    @PostMapping("/{userId}/reimbursements")
    public ResponseEntity<ReimbursementDTO> createUserReimbursement(
            @PathVariable Long userId,
            @RequestBody ReimbursementDTO reimbursementDTO,
            HttpSession session
    ) {
        ReimbursementDTO createdReimbursement = reimbursementService.createReimbursement(userId, reimbursementDTO, session);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdReimbursement);
    }

    /**
     * Update a reimbursement
     *
     * @param userId User id
     * @param reimbursementId Reimbursement id
     * @param request Request body with updates (amount and description)
     * @return Updated reimbursement
     */
    @PatchMapping("/{userId}/reimbursements/{reimbursementId}")
    public ResponseEntity<ReimbursementDTO> updateReimbursement(
            @PathVariable Long userId,
            @PathVariable Long reimbursementId,
            @RequestBody Map<String, String> request,
            HttpSession session
    ) {
        ReimbursementDTO updatedReimbursement = reimbursementService.updateReimbursement(userId, reimbursementId, request, session);

        return ResponseEntity.ok(updatedReimbursement);
    }

    /**
     * Delete a reimbursement
     *
     * @param userId User id
     * @param reimbursementId Reimbursement id
     * @return No content if reimbursement is deleted, not found otherwise
     */
    @DeleteMapping("/{userId}/reimbursements/{reimbursementId}")
    public ResponseEntity<Void> deleteReimbursement(
            @PathVariable Long userId,
            @PathVariable Long reimbursementId,
            HttpSession session
    ) {
        reimbursementService.deleteReimbursementByUserIdAndReimbursementId(userId, reimbursementId, session);

        return ResponseEntity.noContent().build();
    }

    /**
     * Update user profile
     *
     * @param userId User id
     * @param userProfileUpdateDTO User profile update data
     * @param session HttpSession
     * @return Updated user
     */
    @PatchMapping("/{userId}/profile")
    public ResponseEntity<UserDTO> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileUpdateDTO userProfileUpdateDTO,
            HttpSession session
    ) {
        UserDTO updatedUser = userService.updateUserProfile(userId, userProfileUpdateDTO, session);

        return ResponseEntity.ok(updatedUser);
    }

}
