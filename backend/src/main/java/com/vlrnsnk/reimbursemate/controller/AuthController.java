package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.dto.LoginRequestDTO;
import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Register a new user
     *
     * @param user User to be registered
     * @return Created user
     */
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
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logoutUser(HttpSession session) {
        authService.logoutUser(session);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");

        return ResponseEntity.ok(response);
    }

}
