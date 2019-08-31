package com.example.timetablerapp.data.timetable.model;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.room.Room;
import com.example.timetablerapp.data.units.model.Unit;
import com.google.gson.annotations.SerializedName;

/**
 * 23/05/19 -bernard
 */
public class Timetable {
    @SerializedName(Constants.TIMESLOT)
    private Timeslot timeslot;
    @SerializedName(Constants.UNIT)
    private Unit unit;
    @SerializedName("class")
    private Room room;

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
