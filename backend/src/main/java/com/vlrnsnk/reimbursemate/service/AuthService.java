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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
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
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserCreationException("Username already exists");
        }

        try {
            User createdUser = userRepository.save(user);

            return userMapper.toDTO(createdUser);
        } catch (Exception e) {
            throw new UserCreationException("Failed to create user: " + e.getMessage());
        }
    }

    /**
     * Login a user
     *
     * @param loginRequestDTO the login request
     * @param session the session
     * @return the logged in user
     */
    public UserDTO loginUser(LoginRequestDTO loginRequestDTO, HttpSession session) {
        if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().isBlank()) {
            throw new MissingRequiredFieldsException("Username is required");
        }

        if (loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().isBlank()) {
            throw new MissingRequiredFieldsException("Password is required");
        }

        User user = userRepository.findByUsernameAndPassword(
                    loginRequestDTO.getUsername(),
                    loginRequestDTO.getPassword()
                )
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));

        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());
        session.setAttribute("role", user.getRole());

        return userMapper.toDTO(user);
    }

    /**
     * Logout a user
     *
     * @param session the session
     */
    public void logoutUser(HttpSession session) {
        session.invalidate();
    }

}
