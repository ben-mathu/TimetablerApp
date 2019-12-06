package com.example.timetablerapp.dashboard.dialog.room;

import com.example.timetablerapp.data.room.model.Room;
import com.example.timetablerapp.data.room.source.RoomDS;
import com.example.timetablerapp.data.room.source.local.RoomLocalDS;
import com.example.timetablerapp.data.room.source.remote.RoomRemoteDS;
import com.example.timetablerapp.util.SuccessfulCallback;

/**
 * 26/11/19
 *
 * @author bernard
 */
public class RoomRepo implements RoomDS {
    private static RoomRepo INSTANCE;

    private final RoomRemoteDS roomRemoteDS;
    private final RoomLocalDS roomLocalDS;

    private RoomRepo(RoomLocalDS roomLocalDS, RoomRemoteDS roomRemoteDS) {
        this.roomLocalDS = roomLocalDS;
        this.roomRemoteDS = roomRemoteDS;
    }

    public static RoomRepo newInstance(RoomLocalDS roomLocalDS, RoomRemoteDS roomRemoteDS) {
        if (INSTANCE == null) {
            INSTANCE = new RoomRepo(roomLocalDS, roomRemoteDS);
        }
        return INSTANCE;
    }

    @Override
    public void update(Room item, SuccessfulCallback callback) {
        roomRemoteDS.update(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                updateLocal(item, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
                updateLocal(item, callback);
            }
        });
    }

    private void updateLocal(Room item, SuccessfulCallback callback) {
        roomLocalDS.update(item, new SuccessfulCallback() {
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
    public void delete(Room item, SuccessfulCallback callback) {
        roomRemoteDS.delete(item, new SuccessfulCallback() {
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

    private void deleteFromLocal(Room item, SuccessfulCallback callback) {
        roomLocalDS.delete(item, new SuccessfulCallback() {
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
    public void save(Room item, SuccessfulCallback callback) {

    }
}
