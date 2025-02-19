package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.dto.UserDTO;
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
    public Optional<UserDTO> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        return user.map(userMapper::toDTO);
    }

    /**
     * Create a new user
     *
     * @param user User to be created
     * @return Created user
     */
    public UserDTO createUser(User user) {
        User createdUser = userRepository.save(user);

        return userMapper.toDTO(createdUser);
    }

    /**
     * Update user role
     *
     * @param userId User id
     * @param newRole New role
     * @return Updated user
     */
    public Optional<UserDTO> updateUserRole(Long userId, String newRole) {
        User.Role role;

        try {
            role = User.Role.valueOf(newRole);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(role);
            userRepository.save(user);

            return Optional.of(userMapper.toDTO(user));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Delete user
     *
     * @param userId User id
     * @return True if user is deleted, false otherwise
     */
    public boolean deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);

            return true;
        }

        return false;
    }
}
