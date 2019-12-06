package com.example.timetablerapp.data.campuses.model;

import com.google.gson.annotations.SerializedName;

/**
 * 07/09/19 -bernard
 */
public class CampusRequest {
    @SerializedName("campus")
    private Campus campus;

    public CampusRequest(Campus campus) {
        this.campus = campus;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }
}
