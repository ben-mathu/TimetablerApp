package com.example.timetablerapp.data.user;

import com.google.gson.annotations.SerializedName;

/**
 * 20/11/19
 *
 * @author bernard
 */
public class RequestParams {
    @SerializedName("name")
    private final String name;
    @SerializedName("userId")
    private final String userId;
    @SerializedName("role")
    private final String role;

    public RequestParams(String name, String userId, String role) {
        this.name = name;
        this.userId = userId;
        this.role = role;
    }
}
