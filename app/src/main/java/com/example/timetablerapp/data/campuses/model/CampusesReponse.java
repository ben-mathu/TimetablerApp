package com.example.timetablerapp.data.campuses.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 20/05/19 -bernard
 */
public class CampusesReponse {
    @SerializedName("campuses")
    private List<Campus> campuses;

    public List<Campus> getCampuses() {
        return campuses;
    }

    public void setCampuses(List<Campus> campuses) {
        this.campuses = campuses;
    }
}
