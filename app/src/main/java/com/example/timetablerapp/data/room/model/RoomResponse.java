package com.example.timetablerapp.data.room.model;

import com.google.gson.annotations.SerializedName;

/**
 * 26/11/19
 *
 * @author bernard
 */
public class RoomResponse {
    @SerializedName("room")
    private Room room;

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}
