package com.example.timetablerapp.data.user.lecturer;

import android.database.Cursor;

import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.source.LecturerLocalDS;
import com.example.timetablerapp.data.user.lecturer.source.LecturerRemoteDS;

/**
 * 08/05/19 -bernard
 */
public class LecturerRepo implements UserDataSource<Lecturer> {
    private static LecturerRepo INSTANCE = null;

    private LecturerLocalDS lecturerLocalDS;
    private LecturerRemoteDS lecturerRemoteDS;

    public LecturerRepo(LecturerLocalDS lecturerLocalDS, LecturerRemoteDS lecturerRemoteDS) {
        this.lecturerLocalDS = lecturerLocalDS;
        this.lecturerRemoteDS = lecturerRemoteDS;
    }

    public static LecturerRepo newInstance(LecturerLocalDS lecturerLocalDS, LecturerRemoteDS lecturerRemoteDS) {
        if (INSTANCE == null) {
            INSTANCE = new LecturerRepo(lecturerLocalDS, lecturerRemoteDS);
        }
        return INSTANCE;
    }

    @Override
    public void userSignUp(UserAuthCallback callBack, Lecturer lecturer) {
        save(lecturer);
        lecturerRemoteDS.userSignUp(new UserAuthCallback() {
            @Override
            public void userIsAuthSuccessfull(String message) {
                callBack.userIsAuthSuccessfull(message);
            }

            @Override
            public void authNotSuccessful(String message) {
                callBack.authNotSuccessful(message);
            }
        }, lecturer);
    }

    @Override
    public void authUser(UserAuthCallback callBack, Lecturer lecturer) {
        lecturerRemoteDS.userSignUp(new UserAuthCallback(){
            @Override
            public void userIsAuthSuccessfull(String message) {
                callBack.userIsAuthSuccessfull(message);
            }

            @Override
            public void authNotSuccessful(String message) {
                callBack.authNotSuccessful(message);
            }
        }, lecturer);
    }

    @Override
    public void validateUser(String role, String username, String password, UserAuthCallback callback) {
        lecturerLocalDS.validateUser(role, username, password, new UserAuthCallback() {
            @Override
            public void userIsAuthSuccessfull(String message) {
                callback.userIsAuthSuccessfull(message);
            }

            @Override
            public void authNotSuccessful(String message) {
                callback.authNotSuccessful(message);
            }
        });
    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {

    }

    @Override
    public void update(Lecturer item) {

    }

    @Override
    public void delete(Lecturer item) {

    }

    @Override
    public void save(Lecturer item) {
        lecturerLocalDS.save(item);
    }
}
