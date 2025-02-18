package com.vlrnsnk.reimbursemate.dto;

import com.vlrnsnk.reimbursemate.model.User;

public class UserDTO {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final User.Role role;
    private final String createdAt;
    private final String updatedAt;

    public UserDTO(
            Long id,
            String firstName,
            String lastName,
            String username,
            String role,
            String createdAt,
            String updatedAt
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = User.Role.valueOf(role);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}
