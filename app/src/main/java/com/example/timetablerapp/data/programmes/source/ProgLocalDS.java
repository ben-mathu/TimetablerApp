package com.example.timetablerapp.data.programmes.source;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.programmes.ProgrammeDS;
import com.example.timetablerapp.data.programmes.model.Programme;

/**
 * 08/05/19 -bernard
 */
public class ProgLocalDS implements ProgrammeDS {
    private static final String TAG = ProgLocalDS.class.getSimpleName();

    private SQLiteDatabase database;

    public ProgLocalDS() {
        database = MainApplication.getWritableDatabase();
    }

    @Override
    public void getAllFromRemote(LoadProgrammesCallBack callBack, String name) {

    }

    @Override
    public void getFromLocalDb(LoadProgrammesCallBack callBack) {

    }

    @Override
    public void getAllProgrammes(LoadProgrammesCallBack callBack) {

    }

    @Override
    public void addProgramme(Programme programme, SuccessfullySavedCallback callback) {

    }

    @Override
    public void update(Programme item) {

    }

    @Override
    public void delete(Programme item) {

    }

    @Override
    public void save(Programme item) {
        ContentValues values = new ContentValues();

        values.put(TimetablerContract.Programme.PROGRAMME_ID, item.getProgrammeId());
        values.put(TimetablerContract.Programme.PROGRAMME_NAME, item.getProgrammeName());
        values.put(TimetablerContract.Programme.DEPARTMENT_ID, item.getDepartmentId());
        values.put(TimetablerContract.Programme.FACULTY_ID, item.getFacultyId());

        long countRow = database.insert(TimetablerContract.Programme.TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: # of columns affected: " + countRow);
        }
    }
}
