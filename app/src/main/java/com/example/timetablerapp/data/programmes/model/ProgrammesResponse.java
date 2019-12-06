package com.example.timetablerapp.data.programmes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 21/05/19 -bernard
 */
public class ProgrammesResponse {
    @SerializedName("programmes")
    private List<Programme> programmes;

    public List<Programme> getProgrammes() {
        return programmes;
    }

    public void setProgrammes(List<Programme> programmes) {
        this.programmes = programmes;
    }
}
