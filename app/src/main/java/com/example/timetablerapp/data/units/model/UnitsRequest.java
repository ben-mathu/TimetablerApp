package com.example.timetablerapp.data.units.model;

import com.example.timetablerapp.data.Constants;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 12/06/19 -bernard
 */
public class UnitsRequest {
    @SerializedName("units")
    private List<Unit> unitList;

    public List<Unit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<Unit> unitList) {
        this.unitList = unitList;
    }
}
