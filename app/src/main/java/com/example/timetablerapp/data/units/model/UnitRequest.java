package com.example.timetablerapp.data.units.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class UnitRequest {
    @SerializedName("lecturer_id")
    private String lecturerId;
    @SerializedName("units")
    private List<Unit> unitList;

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void setUnits(List<Unit> unitList) {
        this.unitList = unitList;
    }

    public List<Unit> getUnitList() {
        return unitList;
    }
}
