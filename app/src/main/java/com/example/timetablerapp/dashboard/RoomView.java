package com.example.timetablerapp.dashboard;

import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.room.Room;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public interface RoomView {
    void setRooms(List<Room> rooms);

    void showMessage(String message);

    void setHalls(List<Hall> halls);

    void setFaculties(List<Faculty> faculties);
}
