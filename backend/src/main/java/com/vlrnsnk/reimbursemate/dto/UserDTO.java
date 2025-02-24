package com.vlrnsnk.reimbursemate.dto;

import com.vlrnsnk.reimbursemate.model.User;

public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private User.Role role;
    private String createdAt;
    private String updatedAt;

    public UserDTO() {
    }
    /**
     * Constructor for sending User response without password
     *
     * @param id        User ID
     * @param firstName User first name
     * @param lastName  User last name
     * @param username  User username
     * @param role      User role
     * @param createdAt User creation date
     * @param updatedAt User update date
     */
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
