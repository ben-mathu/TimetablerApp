package com.example.timetablerapp.data.timetable.model;

import com.google.gson.annotations.SerializedName;

/**
 * 23/05/19 -bernard
 */
public class Timeslot {
    @SerializedName("day")
    private String day;
    @SerializedName("time")
    private String time;

    public Timeslot(String day, String time) {
        this.day = day;
        this.time = time;
    }

    public Timeslot() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
