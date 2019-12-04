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
    @SerializedName(Constants.IS_REMOVED)
    private boolean isRemoved;

    public Campus(String campusId, String campusName) {
        this.campusId = campusId;
        this.campusName = campusName;
        isRemoved = false;
    }

    public Campus() {
        isRemoved = false;
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

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}
