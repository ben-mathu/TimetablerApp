package com.example.timetablerapp.data.faculties.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 20/05/19 -bernard
 */
public class FacultiesResponse {
    @SerializedName("faculties")
    private List<Faculty> faculties;

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }
}
