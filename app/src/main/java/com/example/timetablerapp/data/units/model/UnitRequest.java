package com.example.timetablerapp.data.units.model;

import com.google.gson.annotations.SerializedName;

/**
 * 19/05/19 -bernard
 */
public class UnitRequest {
    @SerializedName("lecturer_id")
    private String lecturerId;

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}
