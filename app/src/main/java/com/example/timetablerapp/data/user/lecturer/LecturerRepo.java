package com.example.timetablerapp.data.user.lecturer;

import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.model.LecRequest;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.source.LecturerLocalDS;
import com.example.timetablerapp.data.user.lecturer.source.LecturerRemoteDS;

import java.util.List;

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
    public void userSignUp(UserAuthCallback callBack, Lecturer lecturer, String pass) {
        lecturerRemoteDS.userSignUp(new UserAuthCallback() {
            @Override
            public void userIsAuthSuccessful(String message) {
                save(lecturer);
                callBack.userIsAuthSuccessful(message);
            }

            @Override
            public void authNotSuccessful(String message) {
                callBack.authNotSuccessful(message);
            }
        }, lecturer, pass);
    }

    @Override
    public void authUser(UserAuthCallback callBack, Lecturer lecturer) {
        lecturerRemoteDS.userSignUp(new UserAuthCallback(){
            @Override
            public void userIsAuthSuccessful(String message) {
                callBack.userIsAuthSuccessful(message);
            }

            @Override
            public void authNotSuccessful(String message) {
                callBack.authNotSuccessful(message);
            }
        }, lecturer, "");
    }

    @Override
    public void validateUser(String role, String username, String password, String userId, UserAuthCallback callback) {
        lecturerLocalDS.validateUser(role, username, password, userId, new UserAuthCallback() {
            @Override
            public void userIsAuthSuccessful(String message) {
                callback.userIsAuthSuccessful(message);
            }

            @Override
            public void authNotSuccessful(String message) {
                validateUserFromRemote(role, username, password, userId, callback)
;            }
        });
    }

    private void validateUserFromRemote(String role, String username, String password, String userId, UserAuthCallback callback) {
        lecturerRemoteDS.validateUser(role, username, password, userId, new UserAuthCallback() {
            @Override
            public void userIsAuthSuccessful(String message) {
                callback.userIsAuthSuccessful(message);
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
        lecturerLocalDS.save(item);
    }

    public void getLecturers(LecturerDS.LecturersLoadedCallback callback) {
        lecturerRemoteDS.getLecturers(new LecturerDS.LecturersLoadedCallback() {
            @Override
            public void successfullyLoaded(List<Lecturer> list) {
                callback.successfullyLoaded(list);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    public void createLecturer(String email, String fname, String mname, String lname, LecturerDS.CreatingLecturerCallback callback) {
        lecturerRemoteDS.createLecturer(email, fname, mname, lname, new LecturerDS.CreatingLecturerCallback() {
            @Override
            public void successfullyCreated(LecResponse res) {
                callback.successfullyCreated(res);
            }

            @Override
            public void unSuccessful(String message) {
                callback.unSuccessful(message);
            }
        });
    }
}
