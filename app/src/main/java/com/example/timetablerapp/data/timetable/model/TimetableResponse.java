package com.example.timetablerapp.data.timetable.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 23/05/19 -bernard
 */
public class TimetableResponse {
    @SerializedName("timetable")
    private List<Timetable> timetableList;

    public List<Timetable> getTimetableList() {
        return timetableList;
    }

    public void setTimetableList(List<Timetable> timetableList) {
        this.timetableList = timetableList;
    }
}
