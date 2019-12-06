package com.example.timetablerapp.data.user.lecturer.model;

import com.google.gson.annotations.SerializedName;

/**
 * 08/05/19 -bernard
 */
public class LecturerRequest {
    @SerializedName("lecturer")
    private Lecturer lecturer;
    @SerializedName("password")
    private String pass;

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }
}
