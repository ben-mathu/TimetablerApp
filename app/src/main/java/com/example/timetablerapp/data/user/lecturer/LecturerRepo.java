package com.example.timetablerapp.data.user.lecturer;

import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.source.LecturerLocalDS;
import com.example.timetablerapp.data.user.lecturer.source.LecturerRemoteDS;

/**
 * 08/05/19 -bernard
 */
public class LecturerRepo implements LecturerDS {
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
    public void userSignUp(LecturerIsAuthCallBack callBack, Lecturer lecturer) {
        save(lecturer);
        lecturerRemoteDS.userSignUp(new LecturerIsAuthCallBack() {
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
    public void authUser(LecturerIsAuthCallBack callBack, Lecturer lecturer) {
        lecturerRemoteDS.userSignUp(new LecturerIsAuthCallBack() {
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
