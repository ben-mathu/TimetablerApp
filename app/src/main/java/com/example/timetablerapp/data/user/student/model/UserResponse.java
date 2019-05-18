package com.example.timetablerapp.data.user.student.model;

import com.google.gson.annotations.SerializedName;

/**
 * 07/05/19 -bernard
 */
public class UserResponse {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String role;

    public UserResponse(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
