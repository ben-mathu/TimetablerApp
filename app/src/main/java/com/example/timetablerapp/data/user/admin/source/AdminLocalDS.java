package com.example.timetablerapp.data.user.admin.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;

import static com.example.timetablerapp.data.db.TimetablerContract.Admin.ADMIN_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Admin.EMAIL;
import static com.example.timetablerapp.data.db.TimetablerContract.Admin.FIRST_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Admin.LAST_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Admin.MIDDLE_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Admin.PASSWORD;
import static com.example.timetablerapp.data.db.TimetablerContract.Admin.TABLE_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Admin.USERNAME;

/**
 * 22/05/19 -bernard
 */
public class AdminLocalDS implements UserDataSource<Admin, Admin> {
    private static final String TAG = AdminLocalDS.class.getSimpleName();
    private final SQLiteDatabase database;

    public AdminLocalDS() {
        database = MainApplication.getWritableDatabase();
    }

    @Override
    public void userSignUp(SuccessfulCallback callBack, Admin obj, String pass) {

    }

    @Override
    public void authUser(SuccessfulCallback callBack, Admin obj) {

    }

    @Override
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.USERNAME, name);

        long countRow = database.update(TABLE_NAME,
                contentValues,
                Constants.ADMIN_ID + "=?",
                new String[]{userId});

        if (countRow > 0)
            callback.successful("Successfully updated username to " + name);
    }

    @Override
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback<Admin> callback) {
        String[] arrCol = new String[]{ADMIN_ID,
                FIRST_NAME,
                MIDDLE_NAME,
                LAST_NAME,
                USERNAME,
                PASSWORD};
        Cursor cursor = database.query(TABLE_NAME,
                arrCol,
                ADMIN_ID,
                new String[]{userId}, null, null, null);

        if (cursor.moveToFirst()) {
            Admin admin = new Admin();
            admin.setAdminId(cursor.getString(0));
            admin.setfName(cursor.getString(1));
            admin.setlName(cursor.getString(2));
            admin.setmName(cursor.getString(3));
            admin.setUsername(cursor.getString(4));
            admin.setPassword(cursor.getString(5));
            callback.loadData(admin);
        }

        if (cursor.isNull(0)) {
            callback.unsuccessful("Could not get your details, please logout, then log back in.");
        }
        cursor.close();
    }

    @Override
    public void changePassword(String userId, String role, LecturerDS.SuccessCallback callback, String hashedNewPasswd) {
        ContentValues values = new ContentValues();
        values.put(PASSWORD, hashedNewPasswd);

        int count = database.update(TABLE_NAME, values, ADMIN_ID + "=?", new String[]{userId});

        Log.d(TAG, "changePassword: table update output: " + count);
    }

    @Override
    public void updateUserDetails(Admin obj, SuccessfulCallback callback) {
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, obj.getfName());
        values.put(LAST_NAME, obj.getlName());
        values.put(MIDDLE_NAME, obj.getmName());
        values.put(EMAIL, obj.getEmail());

        int count = database.update(TABLE_NAME, values, ADMIN_ID + "=?", new String[]{obj.getAdminId()});

        Log.d(TAG, "updateUserDetails: records changed: " + count);
    }

    @Override
    public void deleteAccount(String userRole, String userId, SuccessfulCallback callback) {
        int count = database.delete(TABLE_NAME, ADMIN_ID + "=?", new String[]{userId});

        Log.d(TAG, "deleteAccount: record count: " + count);

        callback.successful(Constants.DELETED_ACCOUNT_MESSAGE);
    }

    @Override
    public void validateUser(String role, String username, String password, String userId, SuccessfulCallback callback) {

    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {

    }

    @Override
    public void fetchSettingsFromRemote(FetchSettingsCallback callback) {

    }

    @Override
    public void update(Admin item, SuccessfulCallback callback) {

    }

    @Override
    public void delete(Admin item, SuccessfulCallback callback) {

    }

    @Override
    public void save(Admin item, SuccessfulCallback callback) {
        ContentValues values = new ContentValues();

        // Map items or data to contentvalues
        values.put(FIRST_NAME, item.getfName());
        values.put(LAST_NAME, item.getlName());
        values.put(MIDDLE_NAME, item.getmName());
        values.put(USERNAME, item.getUsername());
        values.put(PASSWORD, item.getPassword());
        values.put(ADMIN_ID, item.getAdminId());

        long countRow = database.insert(TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: Successfully recorded..." + countRow + "record(s)");
        }
    }
}
