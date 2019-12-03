package com.example.timetablerapp.data.faculties.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.util.SuccessfulCallback;

import static com.example.timetablerapp.data.db.TimetablerContract.Faculty.CAMPUS_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Faculty.FACULTY_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Faculty.FACULTY_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Faculty.TABLE_NAME;

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
    public void getFacultyById(String facultyId, LoadFacultyCallback callback) {
        String[] arrCol = new String[]{
                TimetablerContract.Faculty.FACULTY_ID,
                TimetablerContract.Faculty.FACULTY_NAME,
                TimetablerContract.Faculty.CAMPUS_ID};

        Cursor cursor = database.query(TimetablerContract.Faculty.TABLE_NAME,
                arrCol,
                TimetablerContract.Faculty.FACULTY_ID + "=?",
                new String[]{facultyId}, null, null, null);

        // Object to store faculty properties
        Faculty faculty = new Faculty();
        if (cursor.moveToFirst()) {
            faculty.setFacultyId(cursor.getString(0));
            faculty.setFacultyName(cursor.getString(1));
            faculty.setCampusId(cursor.getString(2));

            callback.successfullyLoadedFaculty(faculty);
        } else {
            callback.unsuccessful("No records, please log out then log back in.");
        }
    }

    @Override
    public void update(Faculty item, SuccessfulCallback callback) {
        ContentValues values = new ContentValues();
        values.put(FACULTY_NAME, item.getFacultyName());
        values.put(CAMPUS_ID, item.getCampusId());

        database.update(TABLE_NAME, values, FACULTY_ID + "=?", new String[]{item.getFacultyId()});
    }

    @Override
    public void delete(Faculty item, SuccessfulCallback callback) {
        database.delete(TABLE_NAME, FACULTY_ID + "=?", new String[]{item.getFacultyId()});
    }

    @Override
    public void save(Faculty item, SuccessfulCallback callback) {
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
