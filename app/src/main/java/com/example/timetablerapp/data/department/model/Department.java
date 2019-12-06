package com.example.timetablerapp.data.department.model;

import android.support.annotation.NonNull;

import com.example.timetablerapp.data.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * 08/05/19 -bernard
 */
public class Department {
    @SerializedName(Constants.DEPARTMENT_ID)
    private String departmentId;
    @SerializedName(Constants.DEPARTMENT_NAME)
    private String departmentName;
    @SerializedName(Constants.FACULTY_ID)
    private String facultyId;
    @SerializedName(Constants.IS_REMOVED)
    private boolean isRemoved;

    public Department(@NonNull String departmentId, String departmentName, String facultyId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.facultyId = facultyId;
        isRemoved = false;
    }

    public Department() {
        isRemoved = false;
    }

    @NonNull
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(@NonNull String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}
