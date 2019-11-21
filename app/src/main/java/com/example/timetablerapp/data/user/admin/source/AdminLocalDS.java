package com.example.timetablerapp.data.user.admin.source;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.SuccessfulCallback;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.model.Admin;

/**
 * 22/05/19 -bernard
 */
public class AdminLocalDS implements UserDataSource<Admin> {
    private static final String TAG = AdminLocalDS.class.getSimpleName();
    private final SQLiteDatabase database;

    public AdminLocalDS() {
        database = MainApplication.getWritableDatabase();
    }

    @Override
    public void userSignUp(UserAuthCallback callBack, Admin obj, String pass) {

    }

    @Override
    public void authUser(UserAuthCallback callBack, Admin obj) {

    }

    @Override
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.USERNAME, name);

        long countRow = database.update(TimetablerContract.Admin.TABLE_NAME,
                contentValues,
                Constants.LECTURER_ID + "=?",
                new String[]{userId});

        if (countRow > 0)
            callback.successful("Successfully updated username to " + name);
    }

    @Override
    public void validateUser(String role, String username, String password, String userId, UserAuthCallback callback) {

    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {

    }

    @Override
    public void fetchSettingsFromRemote(FetchSettingsCallback callback) {

    }

    @Override
    public void update(Admin item) {

    }

    @Override
    public void delete(Admin item) {

    }

    @Override
    public void save(Admin item) {
        ContentValues values = new ContentValues();

        // Map items or data to contentvalues
        values.put(TimetablerContract.Admin.FIRST_NAME, item.getfName());
        values.put(TimetablerContract.Admin.LAST_NAME, item.getlName());
        values.put(TimetablerContract.Admin.MIDDLE_NAME, item.getmName());
        values.put(TimetablerContract.Admin.USERNAME, item.getUsername());
        values.put(TimetablerContract.Admin.PASSWORD, item.getPassword());
        values.put(TimetablerContract.Admin.LECTURER_ID, item.getAdminId());

        long countRow = database.insert(TimetablerContract.Admin.TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: Successfully recorded..." + countRow + "record(s)");
        }
    }
}
