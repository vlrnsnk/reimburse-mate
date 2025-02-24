package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.dto.UserProfileUpdateDTO;
import com.vlrnsnk.reimbursemate.exception.AuthorizationException;
import com.vlrnsnk.reimbursemate.exception.InvalidUserRoleException;
import com.vlrnsnk.reimbursemate.exception.UserCreationException;
import com.vlrnsnk.reimbursemate.exception.UserNotFoundException;
import com.vlrnsnk.reimbursemate.mapper.UserMapper;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.repository.ReimbursementRepository;
import com.vlrnsnk.reimbursemate.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, ReimbursementRepository reimbursementRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Get all users
     *
     * @return List of all users
     */
    public List<UserDTO> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userRepository.findAll();
        logger.info("Found {} users", users.size());
        return userMapper.toDTOList(users);
    }

    /**
     * Get user by id
     *
     * @param userId User id
     * @return User with the given id
     */
    public UserDTO getUserById(Long userId, HttpSession session) {
        logger.info("Fetching user by ID: {}", userId);
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            logger.warn("User with ID {} is not authorized to view user with ID: {}", sessionUserId, userId);
            throw new AuthorizationException("User is not authorized to view this user!");
        }

        return userRepository.findById(userId)
                .map(userMapper::toDTO)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return new UserNotFoundException("User with id " + userId + " not found");
                });
    }

    /**
     * Get user entity by id
     *
     * @param userId User id
     * @return User entity with the given id
     */
    public User getUserEntityById(Long userId) {
        logger.info("Fetching user entity by ID: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return new UserNotFoundException("User with id " + userId + " not found");
                });
    }

    /**
     * Create a new user
     *
     * @param user User to be created
     * @return Created user
     */
    public UserDTO createUser(User user) {
        logger.info("Creating new user with username: {}", user.getUsername());
        try {
            User createdUser = userRepository.save(user);
            logger.info("User created successfully with ID: {}", createdUser.getId());
            return userMapper.toDTO(createdUser);
        } catch (Exception e) {
            logger.error("Failed to create user: {}", e.getMessage(), e);
            throw new UserCreationException("Failed to create user: " + e.getMessage());
        }
    }

    /**
     * Update user role
     *
     * @param userId User id
     * @param newRole New role
     * @return Updated user
     */
    public UserDTO updateUserRole(Long userId, String newRole) {
        logger.info("Updating role for user ID: {} to {}", userId, newRole);
        User.Role role;

        try {
            role = User.Role.valueOf(newRole);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid role provided: {}", newRole, e);
            throw new InvalidUserRoleException("Invalid role: " + newRole);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return new UserNotFoundException("User with id " + userId + " not found");
                });

        user.setRole(role);
        userRepository.save(user);
        logger.info("User role updated successfully for user ID: {}", userId);
        
        return userMapper.toDTO(user);
    }

    /**
     * Delete user
     *
     * @param userId User id
     */
    @Transactional
    public void deleteUser(Long userId) {
        logger.info("Deleting user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return new UserNotFoundException("User with id " + userId + " not found");
                });

        userRepository.delete(user);
        logger.info("User deleted successfully with ID: {}", userId);
    }

    /**
     * Update user profile
     *
     * @param userId User id
     * @param userProfileUpdateDTO Request body with updated user profile
     * @param session HttpSession
     * @return Updated user
     */
    public UserDTO updateUserProfile(
            Long userId,
            UserProfileUpdateDTO userProfileUpdateDTO,
            HttpSession session
    ) {
        logger.info("Updating profile for user ID: {}", userId);
        Long sessionUserId = (Long) session.getAttribute("userId");

        if (!Objects.equals(sessionUserId, userId)) {
            logger.warn("User with ID {} is not authorized to update user with ID: {}", sessionUserId, userId);
            throw new AuthorizationException("User is not authorized to update this user!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return new UserNotFoundException("User with id " + userId + " not found");
                });

        user.setFirstName(userProfileUpdateDTO.getFirstName());
        user.setLastName(userProfileUpdateDTO.getLastName());
        user.setUsername(userProfileUpdateDTO.getUsername());
        userRepository.save(user);
        logger.info("User profile updated successfully for user ID: {}", userId);

        return userMapper.toDTO(user);
    }

}