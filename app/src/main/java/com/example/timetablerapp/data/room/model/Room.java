package com.example.timetablerapp.data.room.model;

import com.example.timetablerapp.data.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * 23/08/19 -bernard
 */
public class Room {
    @SerializedName(Constants.CLASS_ID)
    private String id;
    @SerializedName(Constants.HALL_ID)
    private String hall_id;
    @SerializedName(Constants.FACULTY_ID)
    private String facultyId;
    @SerializedName(Constants.VOLUME)
    private String volume;
    @SerializedName(Constants.IS_LAB)
    private boolean isLab;
    @SerializedName(Constants.AVAILABILITY)
    private boolean availability;
    @SerializedName(Constants.IS_REMOVED)
    private boolean isRemoved;

    public Room(String id, String hall_id, String facultyId, String volume, boolean isLab, boolean availability) {
        this.id = id;
        this.hall_id = hall_id;
        this.facultyId = facultyId;
        this.volume = volume;
        this.isLab = isLab;
        this.availability = availability;
        isRemoved = false;
    }

    public Room() {
        isRemoved = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHall_id() {
        return hall_id;
    }

    public void setHall_id(String hall_id) {
        this.hall_id = hall_id;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public boolean isLab() {
        return isLab;
    }

    public void setLab(boolean lab) {
        isLab = lab;
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
