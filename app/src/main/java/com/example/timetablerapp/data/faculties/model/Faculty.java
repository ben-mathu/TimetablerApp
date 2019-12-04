package com.example.timetablerapp.data.faculties.model;

import android.support.annotation.NonNull;

import com.example.timetablerapp.data.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * 08/05/19 -bernard
 */
public class Faculty {
    @SerializedName(Constants.FACULTY_ID)
    private String facultyId;
    @SerializedName(Constants.FACULTY_NAME)
    private String facultyName;
    @SerializedName(Constants.CAMPUS_ID)
    private String campusId;
    @SerializedName(Constants.IS_REMOVED)
    private boolean isRemoved;

    public Faculty(String facultyId, String facultyName, String campusId) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.campusId = campusId;
        isRemoved = false;
    }

    public Faculty() {
        isRemoved = false;
    }

    @NonNull
    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(@NonNull String facultyId) {
        this.facultyId = facultyId;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}
