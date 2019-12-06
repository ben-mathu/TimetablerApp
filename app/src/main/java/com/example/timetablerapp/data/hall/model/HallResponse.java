package com.example.timetablerapp.data.hall.model;

import com.google.gson.annotations.SerializedName;

/**
 * 26/11/19
 *
 * @author bernard
 */
public class HallResponse {
    @SerializedName("hall")
    private Hall hall;

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Hall getHall() {
        return hall;
    }
}
