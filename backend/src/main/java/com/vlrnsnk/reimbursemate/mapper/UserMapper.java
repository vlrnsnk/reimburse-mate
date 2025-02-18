package com.vlrnsnk.reimbursemate.mapper;

import com.vlrnsnk.reimbursemate.dto.UserDTO;
import com.vlrnsnk.reimbursemate.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    /**
     * Maps a User entity to a UserDTO
     *
     * @param user the User entity to be mapped
     * @return the UserDTO
     */
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRole().name()
        );
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
