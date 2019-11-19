package com.example.timetablerapp.data.department.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 09/05/19 -bernard
 */
public class DepartmentsResponse {
    @SerializedName("departments")
    private List<Department> list;

    public DepartmentsResponse(List<Department> list) {
        this.list = list;
    }

    public List<Department> getList() {
        return list;
    }
}
