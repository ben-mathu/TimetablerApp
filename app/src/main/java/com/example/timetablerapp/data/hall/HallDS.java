package com.example.timetablerapp.data.hall;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.room.model.Room;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public interface HallDS extends DataSource<Hall> {
    void getRooms(RoomsLoadedCallback callback);
    void getHall(String hall_id, LoadHallCallback callback);

    void getHalls(HallLoadedCallback hallLoadedCallback);

    void addHall(Hall hall, SuccessfulCallback callback);

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

    interface LoadHallCallback {
        void loadHall(Hall hall);
        void unsuccessful(String message);
    }
}
