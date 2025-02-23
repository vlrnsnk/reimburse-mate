package com.vlrnsnk.reimbursemate.dto;

/**
 * Data transfer object for updating user profile
 */
public class UserProfileUpdateDTO {

    private String firstName;
    private String lastName;
    private String username;

    public UserProfileUpdateDTO() {
    }

    public UserProfileUpdateDTO(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    @Override
    public String toString() {
        return "UserProfileUpdateDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
