package com.example.timetablerapp.data.hall;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.room.Room;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public interface HallDS extends DataSource<Hall> {
    void getRooms(RoomsLoadedCallback callback);

    interface HallLoadedCallback {
        void loadingHallsSuccessful(List<Hall> halls);
        void dataNotAvailable(String message);
    }

    interface RoomsLoadedCallback {
        void loadingRoomsSuccessful(List<Room> rooms);
        void dataNotAvailable(String message);
    }

    interface Success {
        void success(String message);
        void unsuccess(String message);
    }
}
