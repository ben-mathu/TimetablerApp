package com.example.timetablerapp.data.hall.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public class HallResponse {
    @SerializedName("halls")
    private List<Hall> list;

    public List<Hall> getList() {
        return list;
    }

    public void setList(List<Hall> list) {
        this.list = list;
    }
}
