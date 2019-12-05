package com.example.timetablerapp.data.programmes.source;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.programmes.ProgrammeDS;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.util.SuccessfulCallback;

import static com.example.timetablerapp.data.db.TimetablerContract.Programme.DEPARTMENT_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Programme.FACULTY_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Programme.PROGRAMME_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Programme.PROGRAMME_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Programme.TABLE_NAME;

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
    public void update(Programme item, SuccessfulCallback callback) {
        ContentValues values = new ContentValues();

        values.put(PROGRAMME_ID, item.getProgrammeId());
        values.put(PROGRAMME_NAME, item.getProgrammeName());
        values.put(DEPARTMENT_ID, item.getDepartmentId());
        values.put(FACULTY_ID, item.getFacultyId());

        long countRow = database.update(TABLE_NAME, values, PROGRAMME_ID, new String[]{item.getProgrammeId()});

        if (countRow > 0) {
            Log.d(TAG, "save: # of columns affected: " + countRow);
        }
    }

    @Override
    public void delete(Programme item, SuccessfulCallback callback) {
        int count = database.delete(TABLE_NAME, PROGRAMME_ID + "=?", new String[]{item.getProgrammeId()});

        if (count > 0)
            callback.successful("Deleted record: " + item.getProgrammeName() + ".");
        else
            callback.unsuccessful("Item not deleted.");
    }

    @Override
    public void save(Programme item, SuccessfulCallback callback) {
        ContentValues values = new ContentValues();

        values.put(PROGRAMME_ID, item.getProgrammeId());
        values.put(PROGRAMME_NAME, item.getProgrammeName());
        values.put(DEPARTMENT_ID, item.getDepartmentId());
        values.put(FACULTY_ID, item.getFacultyId());

        long countRow = database.insert(TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: # of columns affected: " + countRow);
        }
    }
}
