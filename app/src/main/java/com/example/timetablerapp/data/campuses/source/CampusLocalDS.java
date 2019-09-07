package com.example.timetablerapp.data.campuses.source;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.db.TimetablerContract;

/**
 * 08/05/19 -bernard
 */
public class CampusLocalDS implements CampusesDS {
    private static final String TAG = CampusLocalDS.class.getSimpleName();

    private SQLiteDatabase database;

    public CampusLocalDS() {
        database = MainApplication.getWritableDatabase();
    }

    @Override
    public void getAllFromRemote(LoadCampusesCallBack callBack) {

    }

    @Override
    public void addCampus(Campus campus, SuccessFullySavedCallback callback) {

    }

    @Override
    public void update(Campus item) {

    }

    @Override
    public void delete(Campus item) {

    }

    @Override
    public void save(Campus item) {
        ContentValues values = new ContentValues();

        values.put(TimetablerContract.Campus.CAMPUS_ID, item.getCampusId());
        values.put(TimetablerContract.Campus.CAMPUS_NAME, item.getCampusName());

        long countRow = database.insert(TimetablerContract.Campus.TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: # of columns affected: " + countRow);
        }
    }
}
