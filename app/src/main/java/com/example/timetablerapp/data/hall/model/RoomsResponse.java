package com.example.timetablerapp.data.hall.model;

import com.example.timetablerapp.data.room.model.Room;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public class RoomsResponse {
    @SerializedName("rooms")
    private List<Room> list;

    public List<Room> getList() {
        return list;
    }

    public void setList(List<Room> list) {
        this.list = list;
    }
}
