package com.example.timetablerapp.data.hall;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.hall.source.HallLocalDS;
import com.example.timetablerapp.data.hall.source.HallRemoteDS;
import com.example.timetablerapp.data.room.Room;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public class HallRepo implements HallDS {
    private static HallRepo INSTANCE = null;
    private final HallLocalDS hallLocalDS;
    private final HallRemoteDS hallRemoteDS;

    public HallRepo(HallLocalDS hallLocalDS, HallRemoteDS hallRemoteDS) {

        this.hallLocalDS = hallLocalDS;
        this.hallRemoteDS = hallRemoteDS;
    }

    public static HallRepo newInstance(HallLocalDS hallLocalDS, HallRemoteDS hallRemoteDS) {
        if (INSTANCE == null) {
            INSTANCE = new HallRepo(hallLocalDS, hallRemoteDS);
        }
        return INSTANCE;
    }

    @Override
    public void update(Hall item) {

    }

    @Override
    public void delete(Hall item) {

    }

    @Override
    public void save(Hall item) {

    }

    public void getHalls(String facultyId, HallLoadedCallback hallLoadedCallback) {
        hallRemoteDS.getHalls(facultyId, new HallLoadedCallback() {
            @Override
            public void loadingHallsSuccessful(List<Hall> halls) {
                hallLoadedCallback.loadingHallsSuccessful(halls);
            }

            @Override
            public void dataNotAvailable(String message) {
                hallLoadedCallback.dataNotAvailable(message);
            }
        });
    }

    @Override
    public void getRooms(RoomsLoadedCallback callback) {
        hallRemoteDS.getRooms(new RoomsLoadedCallback() {
            @Override
            public void loadingRoomsSuccessful(List<Room> rooms) {
                callback.loadingRoomsSuccessful(rooms);
            }

            @Override
            public void dataNotAvailable(String message) {
                callback.dataNotAvailable(message);
            }
        });
    }

    public void addRoom(Room room, String passcode, Success success) {
        hallRemoteDS.addRooms(room, passcode, new Success() {
            @Override
            public void success(String message) {
                success.success(message);
            }

            @Override
            public void unsuccess(String message) {
                success.unsuccess(message);
            }
        });
    }
}
