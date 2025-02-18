package com.vlrnsnk.reimbursemate.dto;

import com.vlrnsnk.reimbursemate.model.User;

public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private User.Role role;

    public UserDTO(
            Long id,
            String firstName,
            String lastName,
            String username,
            String role
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = User.Role.valueOf(role);
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
}
