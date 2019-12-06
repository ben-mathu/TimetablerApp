package com.example.timetablerapp.data.units.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class UnitResponse {
    @SerializedName("units")
    private List<Unit> unitList;

    public List<Unit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<Unit> unitList) {
        this.unitList = unitList;
    }
}
