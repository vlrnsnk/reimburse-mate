package com.vlrnsnk.reimbursemate.mapper;

import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    /**
     * Maps a UserDTO to a User entity
     *
     * @param userDTO the UserDTO to be mapped
     * @return the User entity
     */
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setRole(User.Role.valueOf(userDTO.getRole().toString().toUpperCase()));

        return user;
    }

    /**
     * Maps a User entity to a UserDTO
     *
     * @param user the User entity to be mapped
     * @return the UserDTO
     */
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRole().name(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
        );
    }

    /**
     * Maps a list of User entities to a list of UserDTOs
     *
     * @param users the list of User entities to be mapped
     * @return the list of UserDTOs
     */
    public List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
