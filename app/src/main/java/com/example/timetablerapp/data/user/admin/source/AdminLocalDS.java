package com.example.timetablerapp.data.user.admin.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.db.TimetablerContract;
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
                Constants.ADMIN_ID + "=?",
                new String[]{userId});

        if (countRow > 0)
            callback.successful("Successfully updated username to " + name);
    }

    @Override
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback callback) {
        String[] arrCol = new String[]{TimetablerContract.Admin.ADMIN_ID,
                TimetablerContract.Admin.FIRST_NAME,
                TimetablerContract.Admin.MIDDLE_NAME,
                TimetablerContract.Admin.LAST_NAME,
                TimetablerContract.Admin.USERNAME,
                TimetablerContract.Admin.PASSWORD};
        Cursor cursor = database.query(TimetablerContract.Admin.TABLE_NAME,
                arrCol,
                TimetablerContract.Admin.ADMIN_ID,
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
        values.put(TimetablerContract.Admin.ADMIN_ID, item.getAdminId());

        long countRow = database.insert(TimetablerContract.Admin.TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: Successfully recorded..." + countRow + "record(s)");
        }
    }
}
