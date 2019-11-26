package com.example.timetablerapp.data.room.source.local;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.room.model.Room;
import com.example.timetablerapp.data.room.source.RoomDS;
import com.example.timetablerapp.util.SuccessfulCallback;

import static com.example.timetablerapp.data.db.TimetablerContract.Room.AVAILABILITY;
import static com.example.timetablerapp.data.db.TimetablerContract.Room.FACULTY_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Room.HALL_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Room.IS_LAB;
import static com.example.timetablerapp.data.db.TimetablerContract.Room.ROOM_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Room.TABLE_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Room.VOLUME;

/**
 * 26/11/19
 *
 * @author bernard
 */
public class RoomLocalDS implements RoomDS {

    private final SQLiteDatabase database;

    public RoomLocalDS() {
        database = MainApplication.getWritableDatabase();
    }

    @Override
    public void update(Room item, SuccessfulCallback callback) {
        ContentValues values = new ContentValues();

        values.put(HALL_ID, item.getHall_id());
        values.put(FACULTY_ID, item.getFacultyId());
        values.put(VOLUME, item.getVolume());
        values.put(AVAILABILITY, item.isAvailability());
        values.put(IS_LAB, item.isLab());

        database.update(TABLE_NAME, values, ROOM_ID + "=?", new String[]{item.getId()});
    }

    @Override
    public void delete(Room item, SuccessfulCallback callback) {
        database.delete(TABLE_NAME, ROOM_ID + "=?",
                new String[]{item.getId()});
    }

    @Override
    public void save(Room item, SuccessfulCallback callback) {

    }
}
