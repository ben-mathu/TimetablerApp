package com.example.timetablerapp.data.faculties.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 09/05/19 -bernard
 */
public class FacultyResponse {
    @SerializedName("faculties")
    private List<Faculty> list;

    public void setList(List<Faculty> list) {
        this.list = list;
    }

    public List<Faculty> getList() {
        return list;
    }
}
