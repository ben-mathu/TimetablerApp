package com.example.timetablerapp.data.user.lecturer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 02/09/19 -bernard
 */
public class LecturerResponseList {
    @SerializedName("lecturers")
    private List<Lecturer> responseList;

    public List<Lecturer> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Lecturer> responseList) {
        this.responseList = responseList;
    }
}
