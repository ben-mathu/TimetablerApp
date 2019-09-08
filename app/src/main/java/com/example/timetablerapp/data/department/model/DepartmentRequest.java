package com.example.timetablerapp.data.department.model;

import com.example.timetablerapp.data.department.model.Department;
import com.google.gson.annotations.SerializedName;

/**
 * 08/09/19 -bernard
 */
public class DepartmentRequest {
    @SerializedName("department")
    private Department department;

    public DepartmentRequest(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
