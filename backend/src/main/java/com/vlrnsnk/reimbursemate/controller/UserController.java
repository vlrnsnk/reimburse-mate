package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.aop.RequiresRole;
import com.vlrnsnk.reimbursemate.dto.ReimbursementDTO;
import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.dto.UserProfileUpdateDTO;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.service.ReimbursementService;
import com.vlrnsnk.reimbursemate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "Endpoints for managing users and their reimbursements")
public class UserController {

    private final UserService userService;
    private final ReimbursementService reimbursementService;

    public UserController(UserService userService, ReimbursementService reimbursementService) {
        this.userService = userService;
        this.reimbursementService = reimbursementService;
    }

    /**
     * Get all users
     *
     * @return List of all users
     */
    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all users. Requires MANAGER role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content)
    })
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
    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a user by their ID. Accessible by the user themselves or a MANAGER."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true, example = "1")
            @PathVariable Long userId,
            HttpSession session
    ) {
        UserDTO userDTO = userService.getUserById(userId, session);

        return ResponseEntity.ok(userDTO);
    }

    /**
     * Create a new user
     *
     * @param user User to be created
     * @return Created user
     */
    @Operation(
            summary = "Create a new user",
            description = "Registers a new user in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class))
            )
            @RequestBody User user
    ) {
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
    @Operation(
            summary = "Update user role",
            description = "Updates the role of a user. Requires MANAGER role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid role provided",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @RequiresRole(User.Role.MANAGER)
    @PatchMapping("/{userId}/role")
    public ResponseEntity<UserDTO> updateUserRole(
            @Parameter(description = "ID of the user to update", required = true, example = "1")
            @PathVariable Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New role information",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"role\": \"MANAGER\"}"))
            )
            @RequestBody Map<String, String> request
    ) {
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
    @Operation(
            summary = "Delete a user",
            description = "Deletes a user by their ID. Requires MANAGER role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @RequiresRole(User.Role.MANAGER)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true, example = "1")
            @PathVariable Long userId
    ) {
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
    @Operation(
            summary = "Get user's reimbursements",
            description = "Retrieves all reimbursements for a specific user, optionally filtered by status."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reimbursements",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReimbursementDTO.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @GetMapping("/{userId}/reimbursements")
    public ResponseEntity<List<ReimbursementDTO>> getUserReimbursements(
            @Parameter(description = "ID of the user", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "Status to filter reimbursements", example = "PENDING")
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
    @Operation(
            summary = "Create a reimbursement",
            description = "Creates a new reimbursement request for a user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reimbursement created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReimbursementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid reimbursement data",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PostMapping("/{userId}/reimbursements")
    public ResponseEntity<ReimbursementDTO> createUserReimbursement(
            @Parameter(description = "ID of the user", required = true, example = "1")
            @PathVariable Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Reimbursement details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReimbursementDTO.class))
            )
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
    @Operation(
            summary = "Update a reimbursement",
            description = "Updates an existing reimbursement for a user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reimbursement updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReimbursementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid update data",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User or reimbursement not found",
                    content = @Content)
    })
    @PatchMapping("/{userId}/reimbursements/{reimbursementId}")
    public ResponseEntity<ReimbursementDTO> updateReimbursement(
            @Parameter(description = "ID of the user", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "ID of the reimbursement to update", required = true, example = "1")
            @PathVariable Long reimbursementId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update fields",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"amount\": 100.50, \"description\": \"Updated expense description\"}")
                    )
            )
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
    @Operation(
            summary = "Delete a reimbursement",
            description = "Deletes a specific reimbursement for a user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reimbursement deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User or reimbursement not found",
                    content = @Content)
    })
    @DeleteMapping("/{userId}/reimbursements/{reimbursementId}")
    public ResponseEntity<Void> deleteReimbursement(
            @Parameter(description = "ID of the user", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "ID of the reimbursement to delete", required = true, example = "1")
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
    @Operation(
            summary = "Update user profile",
            description = "Updates profile information for a user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid profile data",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized access",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PatchMapping("/{userId}/profile")
    public ResponseEntity<UserDTO> updateUserProfile(
            @Parameter(description = "ID of the user", required = true, example = "1")
            @PathVariable Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Profile update data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserProfileUpdateDTO.class))
            )
            @RequestBody UserProfileUpdateDTO userProfileUpdateDTO,
            HttpSession session
    ) {
        UserDTO updatedUser = userService.updateUserProfile(userId, userProfileUpdateDTO, session);

        return ResponseEntity.ok(updatedUser);
    }

}
