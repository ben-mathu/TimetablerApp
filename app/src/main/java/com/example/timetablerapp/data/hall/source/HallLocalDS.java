package com.example.timetablerapp.data.hall.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.hall.HallDS;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.util.SuccessfulCallback;

import static com.example.timetablerapp.data.Constants.TABLE_HALLS;
import static com.example.timetablerapp.data.db.TimetablerContract.Hall.FACULTY_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Hall.HALL_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Hall.HALL_NAME;

/**
 * 03/09/19 -bernard
 */
public class HallLocalDS implements HallDS {
    private final SQLiteDatabase database;

    public HallLocalDS() {
        database = MainApplication.getWritableDatabase();
    }

    @Override
    public void getRooms(RoomsLoadedCallback callback) {

    }

    @Override
    public void getHall(String hall_id, HallDS.LoadHallCallback callback) {
        String[] arrCol = new String[]{HALL_ID, HALL_NAME, FACULTY_ID};
        Cursor cursor = database.query(TABLE_HALLS,
                arrCol,
                HALL_ID + "=?", new String[]{hall_id},
                null, null, null);

        if (cursor.moveToFirst()) {
            Hall hall = new Hall();
            hall.setHallId(cursor.getString(0));
            hall.setHallName(cursor.getString(1));
            hall.setFacultyId(cursor.getString(2));
            callback.loadHall(hall);
        } else {
            callback.unsuccessful("Hall ID : " + hall_id + " not available.");
        }

        cursor.close();
    }

    @Override
    public void update(Hall item, SuccessfulCallback callback) {

    }

    @Override
    public void delete(Hall item, SuccessfulCallback callback) {

    }

    @Override
    public void save(Hall item, SuccessfulCallback callback) {

    }
}
