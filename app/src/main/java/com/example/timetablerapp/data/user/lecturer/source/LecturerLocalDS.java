package com.example.timetablerapp.data.user.lecturer.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

/**
 * 08/05/19 -bernard
 */
public class LecturerLocalDS implements UserDataSource<Lecturer> {
    private static final String TAG = LecturerLocalDS.class.getSimpleName();
    private SQLiteDatabase database;

    public LecturerLocalDS() {
        this.database = MainApplication.getWritableDatabase();
    }

    @Override
    public void userSignUp(UserDataSource.UserAuthCallback callBack, Lecturer lecturer, String pass) {

    }

    @Override
    public void authUser(UserDataSource.UserAuthCallback callBack, Lecturer lecturer) {

    }

    @Override
    public void validateUser(String role, String username, String password, String userId, UserAuthCallback callback) {
        String passwd = getPassWd(role, username);
        if (passwd.equals(password)) {
            callback.userIsAuthSuccessful("Successfully logged in.");
        } else {
            callback.authNotSuccessful("Username or password is wrong, please try again.");
        }
    }

    public String getPassWd(String role, String username) {
        String tableName = "", colId = "";
        if (role.equalsIgnoreCase("lecturer")) {
            tableName = Constants.TABLE_LECTURERS;
            colId = Constants.LECTURER_ID;
        } else if (role.equalsIgnoreCase("student")) {
            tableName = Constants.TABLE_STUDENTS;
            colId = Constants.STUDENT_ID;
        } else if (role.equalsIgnoreCase("admin")) {
            tableName = Constants.TABLE_ADMIN;
            colId = Constants.ADMIN_ID;
        }

        String passWd = "";

//        Cursor cursor = database.rawQuery("select password from " + tableName + " where username='" + username + "'", null);
        Cursor cursor = database.query(tableName, new String[]{colId, Constants.PASSWORD},
                Constants.USERNAME + "=?", new String[]{username}, null, null, null);

        if (cursor.moveToFirst())  {
            passWd = cursor.getString(cursor.getColumnIndex(Constants.PASSWORD));
            MainApplication.getSharedPreferences().edit()
                    .putString(Constants.USER_ID,
                            cursor.getString(cursor.getColumnIndex(colId))).apply();
        }

        return passWd;
    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {

    }

    @Override
    public void fetchSettingsFromRemote(FetchSettingsCallback callback) {

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
