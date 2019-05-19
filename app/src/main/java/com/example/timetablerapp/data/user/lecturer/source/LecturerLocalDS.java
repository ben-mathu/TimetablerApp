package com.example.timetablerapp.data.user.lecturer.source;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

/**
 * 08/05/19 -bernard
 */
public class LecturerLocalDS implements LecturerDS {
    private static final String TAG = LecturerLocalDS.class.getSimpleName();
    private SQLiteDatabase database;

    public LecturerLocalDS(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void userSignUp(LecturerIsAuthCallBack callBack, Lecturer lecturer) {

    }

    @Override
    public void authUser(LecturerIsAuthCallBack callBack, Lecturer lecturer) {

    }

    @Override
    public void update(Lecturer item) {

    }

    @Override
    public void delete(Lecturer item) {

    }

    @Override
    public void save(Lecturer item) {
        ContentValues values = new ContentValues();

        // Map items or data to contentvalues
        values.put(TimetablerContract.Lecturer.FIRST_NAME, item.getFirstName());
        values.put(TimetablerContract.Lecturer.LAST_NAME, item.getLastName());
        values.put(TimetablerContract.Lecturer.MIDDLE_NAME, item.getMiddleName());
        values.put(TimetablerContract.Lecturer.USERNAME, item.getUsername());
        values.put(TimetablerContract.Lecturer.PASSWORD, item.getPassword());
        values.put(TimetablerContract.Lecturer.LECTURER_ID, item.getId());
        values.put(TimetablerContract.Lecturer.DEPARTMENT_ID, item.getDepartmentId());
        values.put(TimetablerContract.Lecturer.FACULTY_ID, item.getFacultyId());
        values.put(TimetablerContract.Lecturer.IN_SESSION, item.isInSesson());

        long countRow = database.insert(TimetablerContract.Lecturer.TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: Successfully recorded..." + countRow + "record(s)");
        }
    }
}
