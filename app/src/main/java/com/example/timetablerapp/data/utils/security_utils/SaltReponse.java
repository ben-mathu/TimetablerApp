package com.example.timetablerapp.data.utils.security_utils;

import com.google.gson.annotations.SerializedName;

/**
 * 07/05/19 -bernard
 */
public class SaltReponse {
    @SerializedName("salt")
    private String salt;


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
