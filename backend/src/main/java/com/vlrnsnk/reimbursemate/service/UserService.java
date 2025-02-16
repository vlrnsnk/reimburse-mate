package com.vlrnsnk.reimbursemate.service;

import com.vlrnsnk.reimbursemate.model.User;
import com.vlrnsnk.reimbursemate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get all users
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user by id
     *
     * @param id User id
     * @return User with the given id
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Create a new user
     *
     * @param user User to be created
     * @return Created user
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUserRole(Long id, String newRole) {
        User.Role role;

        try {
            role = User.Role.valueOf(newRole);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(role);
            userRepository.save(user);

            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }
}
