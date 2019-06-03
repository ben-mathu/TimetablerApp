package com.example.timetablerapp.data.timetable.model;

import com.example.timetablerapp.data.Constants;
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
}
