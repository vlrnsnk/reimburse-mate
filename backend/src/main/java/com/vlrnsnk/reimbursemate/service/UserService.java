package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.exception.InvalidUserRoleException;
import com.vlrnsnk.reimbursemate.exception.UserCreationException;
import com.vlrnsnk.reimbursemate.exception.UserNotFoundException;
import com.vlrnsnk.reimbursemate.mapper.UserMapper;
import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Get all users
     *
     * @return List of all users
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return userMapper.toDTOList(users);
    }

    /**
     * Get user by id
     *
     * @param userId User id
     * @return User with the given id
     */
    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
    }

    /**
     * Get user entity by id
     *
     * @param userId User id
     * @return User entity with the given id
     */
    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
    }

    /**
     * Create a new user
     *
     * @param user User to be created
     * @return Created user
     */
    public UserDTO createUser(User user) {
        try {
            User createdUser = userRepository.save(user);

            return userMapper.toDTO(createdUser);
        } catch (Exception e) {
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
        User.Role role;

        try {
            role = User.Role.valueOf(newRole);
        } catch (IllegalArgumentException e) {
            throw new InvalidUserRoleException("Invalid role: " + newRole);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        user.setRole(role);
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    /**
     * Delete user
     *
     * @param userId User id
     */
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        userRepository.delete(user);
    }

}
