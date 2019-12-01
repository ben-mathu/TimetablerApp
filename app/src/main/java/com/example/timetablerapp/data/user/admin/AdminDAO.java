package com.example.timetablerapp.data.user.admin;

import com.example.timetablerapp.data.user.admin.model.Admin;
import com.google.gson.annotations.SerializedName;

/**
 * 22/11/19
 *
 * @author bernard
 */
public class AdminDAO {
    @SerializedName("admin")
    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
