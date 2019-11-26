package com.example.timetablerapp.data.room.model;

import com.google.gson.annotations.SerializedName;

/**
 * 26/11/19
 *
 * @author bernard
 */
public class RoomRequest {
    @SerializedName("room")
    private final Room room;
    @SerializedName("passcode")
    private final String passcode;

    public RoomRequest(Room room, String passcode) {
        this.room = room;
        this.passcode = passcode;
    }
}
