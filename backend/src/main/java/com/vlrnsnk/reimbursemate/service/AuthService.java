package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.LoginRequestDTO;
import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.exception.MissingRequiredFieldsException;
import com.vlrnsnk.reimbursemate.exception.UserCreationException;
import com.vlrnsnk.reimbursemate.exception.UserNotFoundException;
import com.vlrnsnk.reimbursemate.mapper.UserMapper;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Register a new user
     *
     * @param user User to be registered
     * @return Created user
     */
    public UserDTO registerUser(User user) {
        logger.info("Attempting to register user with username: {}", user.getUsername());

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            logger.warn("Username already exists: {}", user.getUsername());

            throw new UserCreationException("Username already exists");
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User createdUser = userRepository.save(user);
            logger.info("User registered successfully with ID: {}", createdUser.getId());

            return userMapper.toDTO(createdUser);
        } catch (Exception e) {
            logger.error("Failed to create user: {}", e.getMessage(), e);

            throw new UserCreationException("Failed to create user: " + e.getMessage());
        }
    }

    /**
     * Login a user
     *
     * @param loginRequestDTO the login request
     * @param session the session
     * @return the logged-in user
     */
    public UserDTO loginUser(LoginRequestDTO loginRequestDTO, HttpSession session) {
        logger.info("Attempting to login user with username: {}", loginRequestDTO.getUsername());

        if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().isBlank()) {
            logger.warn("Username is required for login");
            throw new MissingRequiredFieldsException("Username is required");
        }

        if (loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().isBlank()) {
            logger.warn("Password is required for login");
            throw new MissingRequiredFieldsException("Password is required");
        }

        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", loginRequestDTO.getUsername());
                    throw new UserNotFoundException("Invalid username or password");
                });

        System.out.println("i'm hrere");
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            logger.warn("Invalid password for user: {}", loginRequestDTO.getUsername());
            throw new UserNotFoundException("Invalid username or password");
        }
        System.out.println("and threre");
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());
        session.setAttribute("role", user.getRole());

        logger.info("User logged in successfully with ID: {}", user.getId());

        return userMapper.toDTO(user);
    }

    /**
     * Logout a user
     *
     * @param session the session
     */
    public void logoutUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        logger.info("Attempting to logout user with username: {}", username);

        session.invalidate();
        logger.info("User logged out successfully: {}", username);
    }
}