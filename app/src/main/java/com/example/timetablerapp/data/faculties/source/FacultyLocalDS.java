package com.example.timetablerapp.data.faculties.source;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;

/**
 * 08/05/19 -bernard
 */
public class FacultyLocalDS implements FacultyDS {
    private static final String TAG = FacultyLocalDS.class.getSimpleName();

    private SQLiteDatabase database;

    public FacultyLocalDS() {
        this.database = MainApplication.getWritableDatabase();
    }

    @Override
    public void getAllFromRemote(LoadFacultiesCallBack callBack, String name) {

    }

    @Override
    public void getAllFromRemote(LoadFacultiesCallBack callBack) {

    }

    @Override
    public void addFaculty(Faculty faculty, SuccessFulCallback callback) {

    }

    @Override
    public void update(Faculty item) {

    }

    @Override
    public void delete(Faculty item) {

    }

    @Override
    public void save(Faculty item) {
        ContentValues values = new ContentValues();

        values.put(TimetablerContract.Faculty.FACULTY_ID, item.getFacultyId());
        values.put(TimetablerContract.Faculty.FACULTY_NAME, item.getFacultyName());
        values.put(TimetablerContract.Faculty.CAMPUS_ID, item.getCampusId());

        long countRow = database.insert(TimetablerContract.Faculty.TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: # of columns affected: " + countRow);
        }
    }
}
