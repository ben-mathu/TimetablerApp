package com.example.timetablerapp.data.user.lecturer.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.CAMPUS_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.DEPARTMENT_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.EMAIL;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.FACULTY_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.FIRST_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.IN_SESSION;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.LAST_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.LECTURER_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.MIDDLE_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.PASSWORD;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.TABLE_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Lecturer.USERNAME;

/**
 * 08/05/19 -bernard
 */
public class LecturerLocalDS implements UserDataSource<Lecturer>, LecturerDS {
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
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.USERNAME, name);

        long countRow = database.update(TimetablerContract.Lecturer.TABLE_NAME,
                contentValues,
                Constants.LECTURER_ID + "=?",
                new String[]{userId});

        if (countRow > 0)
            callback.successful("Successfully updated username to " + name);
        else callback.unsuccessful("Username " + name + " was not updated.");
    }

    @Override
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback callback) {
        String[] arrCol = new String[]{
                LECTURER_ID,
                FIRST_NAME,
                LAST_NAME,
                MIDDLE_NAME,
                USERNAME,
                PASSWORD,
                IN_SESSION,
                DEPARTMENT_ID,
                FACULTY_ID,
        };

        Cursor cursor = database.query(TimetablerContract.Lecturer.TABLE_NAME,
                arrCol,
                TimetablerContract.Lecturer.LECTURER_ID + "=?",
                new String[]{userId}, null, null, null);

        if (cursor.moveToFirst()) {
            Lecturer lecturer = new Lecturer();
            lecturer.setFirstName(cursor.getString(1));
            lecturer.setLastName(cursor.getString(2));
            lecturer.setMiddleName(cursor.getString(3));
            lecturer.setUsername(cursor.getString(4));
            lecturer.setPassword(cursor.getString(5));
            lecturer.setInSesson(cursor.getInt(6) == 1);
            lecturer.setDepartmentId(cursor.getString(7));
            lecturer.setFacultyId(cursor.getString(8));
            lecturer.setEmail(cursor.getString(9));
            callback.loadData(lecturer);
        }

        if (cursor.isNull(0)) {
            callback.unsuccessful("Could not get your details, please logout, then log back in.");
        }

        cursor.close();
    }

    @Override
    public void changePassword(String userId, String role, SuccessCallback callback, String hashedNewPasswd) {
        ContentValues values = new ContentValues();
        values.put(PASSWORD, hashedNewPasswd);

        int count = database.update(TABLE_NAME, values, LECTURER_ID + "=?", new String[]{userId});

        Log.d(TAG, "changePassword: table update output: " + count);
    }

    @Override
    public void updateUserDetails(Lecturer obj, SuccessfulCallback callback) {
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, obj.getFirstName());
        values.put(LAST_NAME, obj.getLastName());
        values.put(MIDDLE_NAME, obj.getMiddleName());
        values.put(CAMPUS_ID, obj.getCampusid());
        values.put(IN_SESSION, obj.isInSession());
        values.put(EMAIL, obj.getEmail());

        int count = database.update(TABLE_NAME, values, LECTURER_ID + "=?", new String[]{obj.getId()});

        Log.d(TAG, "updateUserDetails: records changed: " + count);
    }

    @Override
    public void deleteAccount(String userRole, String userId, SuccessfulCallback callback) {
        int count = database.delete(TABLE_NAME, LECTURER_ID + "=?", new String[]{userId});

        Log.d(TAG, "deleteAccount: record count: " + count);

        callback.successful(Constants.DELETED_ACCOUNT_MESSAGE);
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

    private String getPassWd(String role, String username) {
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

        cursor.close();
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
        values.put(TimetablerContract.Lecturer.IN_SESSION, item.isInSession());

        long countRow = database.insert(TimetablerContract.Lecturer.TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: Successfully recorded..." + countRow + "record(s)");
        }
    }

    @Override
    public void deleteLecturer(Lecturer lecturer, SuccessCallback deleteCallback) {
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, lecturer.getFirstName());
        values.put(LAST_NAME, lecturer.getLastName());
        values.put(MIDDLE_NAME, lecturer.getMiddleName());
        values.put(USERNAME, lecturer.getUsername());
        values.put(IN_SESSION, lecturer.isInSession());
        values.put(EMAIL, lecturer.getEmail());

        int count = database.update(TABLE_NAME, values, LECTURER_ID + "=?", new String[]{lecturer.getId()});

        Log.d(TAG, "updateUserDetails: records changed: " + count);
    }
}
