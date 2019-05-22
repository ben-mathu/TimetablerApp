package com.example.timetablerapp.data.user.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * 22/05/19 -bernard
 */
public class AdminRequest {
    @SerializedName("admin")
    private Admin admin;
    @SerializedName("password")
    private String dbPassword;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
