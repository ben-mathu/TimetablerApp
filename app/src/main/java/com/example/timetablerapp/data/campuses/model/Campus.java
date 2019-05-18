package com.example.timetablerapp.data.campuses.model;

import com.example.timetablerapp.data.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * 08/05/19 -bernard
 */
public class Campus {
    @SerializedName(Constants.CAMPUS_ID)
    private String campusId;
    @SerializedName(Constants.CAMPUS_NAME)
    private String campusName;

    public Campus(String campusId, String campusName) {
        this.campusId = campusId;
        this.campusName = campusName;
    }

    public Campus() {
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }
}
