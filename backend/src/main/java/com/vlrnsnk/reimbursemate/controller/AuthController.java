package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.dto.LoginRequestDTO;
import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication API", description = "Handles user registration, login, and session management")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Register a new user
     *
     * @param user User to be registered
     * @return Created user
     */
    @Operation(
            summary = "Register a new user",
            description = "Creates a user account with email and password",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully",
                            content = @Content(
                                    schema = @Schema(implementation = UserDTO.class),
                                    examples = @ExampleObject(
                                            value = "{\"id\": 1, \"email\": \"user@example.com\", \"role\": \"EMPLOYEE\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input (e.g., duplicate email)"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        UserDTO createdUser = authService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Login a user
     *
     * @param loginRequestDTO the login request
     * @param session the session
     * @return the logged in user
     */
    @Operation(
            summary = "Authenticate user",
            description = "Logs in a user and creates a session",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful",
                            content = @Content(
                                    schema = @Schema(implementation = UserDTO.class),
                                    examples = @ExampleObject(
                                            value = "{\"id\": 1, \"email\": \"user@example.com\", \"role\": \"EMPLOYEE\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid credentials"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) {
        UserDTO loggedInUser = authService.loginUser(loginRequestDTO, session);

        return ResponseEntity.ok(loggedInUser);
    }

    /**
     * Logout a user
     *
     * @param session the session
     * @return the logged out message
     */
    @Operation(
            summary = "Terminate session",
            description = "Logs out the current user and invalidates the session",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Logout successful",
                            content = @Content(
                                    schema = @Schema(implementation = Map.class),
                                    examples = @ExampleObject(
                                            value = "{\"message\": \"Logged out successfully\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No active session"
                    )
            }
    )
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logoutUser(HttpSession session) {
        authService.logoutUser(session);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");

        return ResponseEntity.ok(response);
    }

}
