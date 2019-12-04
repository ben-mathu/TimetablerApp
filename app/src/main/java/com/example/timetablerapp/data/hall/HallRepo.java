package com.example.timetablerapp.data.hall;

import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.hall.source.HallLocalDS;
import com.example.timetablerapp.data.hall.source.HallRemoteDS;
import com.example.timetablerapp.data.room.model.Room;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public class HallRepo implements HallDS {
    private static HallRepo INSTANCE = null;
    private final HallLocalDS hallLocalDS;
    private final HallRemoteDS hallRemoteDS;

    private HallRepo(HallLocalDS hallLocalDS, HallRemoteDS hallRemoteDS) {

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
    public void update(Hall item, SuccessfulCallback callback) {
        hallRemoteDS.update(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                updateToLocalDS(item, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    private void updateToLocalDS(Hall item, SuccessfulCallback callback) {
        hallLocalDS.update(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void delete(Hall item, SuccessfulCallback callback) {
        hallRemoteDS.delete(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                deleteFromLocal(item, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
                deleteFromLocal(item, callback);
            }
        });
    }

    private void deleteFromLocal(Hall item, SuccessfulCallback callback) {
        hallLocalDS.delete(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void save(Hall item, SuccessfulCallback callback) {

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

    @Override
    public void getHall(String hall_id, LoadHallCallback callback) {
        hallRemoteDS.getHall(hall_id, new LoadHallCallback() {
            @Override
            public void loadHall(Hall hall) {
                callback.loadHall(hall);
            }

            @Override
            public void unsuccessful(String message) {
                getHallFromLocalDS(hall_id, callback);
            }
        });
    }

    @Override
    public void getHalls(HallLoadedCallback hallLoadedCallback) {
        hallRemoteDS.getHalls(new HallLoadedCallback() {
            @Override
            public void loadingHallsSuccessful(List<Hall> halls) {
                hallLoadedCallback.loadingHallsSuccessful(halls);
            }

            @Override
            public void dataNotAvailable(String message) {
                getFromLocal(hallLoadedCallback);
            }
        });
    }

    private void getFromLocal(HallLoadedCallback hallLoadedCallback) {
        hallLocalDS.getHalls(new HallLoadedCallback() {
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
    public void addHall(Hall hall, SuccessfulCallback callback) {
        hallRemoteDS.addHall(hall, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                addToLocalDS(hall, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
                addToLocalDS(hall, callback);
            }
        });
    }

    private void addToLocalDS(Hall hall, SuccessfulCallback callback) {
        hallLocalDS.addHall(hall, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.successful(message);
            }
        });
    }

    private void getHallFromLocalDS(String hall_id, LoadHallCallback callback) {
        hallLocalDS.getHall(hall_id, new LoadHallCallback() {
            @Override
            public void loadHall(Hall hall) {
                callback.loadHall(hall);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
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
