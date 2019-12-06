package com.example.timetablerapp.data.faculties.model;

import com.google.gson.annotations.SerializedName;

/**
 * 07/09/19 -bernard
 */
public class FacultyRequest {
    @SerializedName("faculty")
    private Faculty faculty;

    public FacultyRequest(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }
}
