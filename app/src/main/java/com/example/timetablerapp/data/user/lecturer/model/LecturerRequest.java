package com.example.timetablerapp.data.user.lecturer.model;

import com.google.gson.annotations.SerializedName;

/**
 * 08/05/19 -bernard
 */
public class LecturerRequest {
    @SerializedName("lecturer")
    private Lecturer lecturer;

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }
}
