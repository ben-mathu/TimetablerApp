package com.example.timetablerapp.data.user.lecturer.model;

import com.example.timetablerapp.data.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * 02/09/19 -bernard
 */
public class LecRequest {
    @SerializedName("email")
    private String email;
    @SerializedName(Constants.F_NAME)
    private String fname;
    @SerializedName(Constants.M_NAME)
    private String mname;
    @SerializedName(Constants.L_NAME)
    private String lname;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLname() {
        return lname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMname() {
        return mname;
    }
}
