package com.example.timetablerapp.data.user.lecturer;

import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponse;
import com.example.timetablerapp.data.user.lecturer.source.LecturerLocalDS;
import com.example.timetablerapp.data.user.lecturer.source.LecturerRemoteDS;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class LecturerRepo implements UserDataSource<Lecturer>, LecturerDS {
    private static LecturerRepo INSTANCE = null;

    private LecturerLocalDS lecturerLocalDS;
    private LecturerRemoteDS lecturerRemoteDS;

    private LecturerRepo(LecturerLocalDS lecturerLocalDS, LecturerRemoteDS lecturerRemoteDS) {
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
    public void userSignUp(SuccessfulCallback callBack, Lecturer lecturer, String pass) {
        lecturerRemoteDS.userSignUp(new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                save(lecturer, callBack);
                callBack.unsuccessful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callBack.unsuccessful(message);
            }
        }, lecturer, pass);
    }

    @Override
    public void authUser(SuccessfulCallback callBack, Lecturer lecturer) {
        lecturerRemoteDS.userSignUp(new SuccessfulCallback(){
            @Override
            public void successful(String message) {
                callBack.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callBack.unsuccessful(message);
            }
        }, lecturer, "");
    }

    @Override
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        lecturerRemoteDS.updateUsername(name, userId, role, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                updateLocalUsername(name, userId, role, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.successful(message);
                updateLocalUsername(name, userId, role, callback);
            }
        });
    }

    private void updateLocalUsername(String name, String userId, String role, SuccessfulCallback callback) {
        lecturerLocalDS.updateUsername(name, userId, role, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void validateUser(String role, String username, String password, String userId, SuccessfulCallback callback) {
        lecturerLocalDS.validateUser(role, username, password, userId, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                validateUserFromRemote(role, username, password, userId, callback)
;            }
        });
    }

    private void validateUserFromRemote(String role, String username, String password, String userId, SuccessfulCallback callback) {
        lecturerRemoteDS.validateUser(role, username, password, userId, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {

    }

    @Override
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback callback) {
        lecturerRemoteDS.getDetails(userId, userRole, new LoadUserDetailsCallback<LecturerResponse>() {
            @Override
            public void loadData(@NotNull LecturerResponse obj) {
                callback.loadData(obj);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
                getFromLocalDS(userId, userRole, callback);
            }
        });
    }

    @Override
    public void changePassword(String userId, String role, SuccessCallback callback, String hashedNewPasswd) {
        lecturerRemoteDS.changePassword(userId, role, new SuccessCallback() {
            @Override
            public void success(String message) {
                callback.success(message);
                changeLocalPassword(userId, role, hashedNewPasswd, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
                changeLocalPassword(userId, role, hashedNewPasswd, callback);
            }
        }, hashedNewPasswd);
    }

    @Override
    public void updateUserDetails(Lecturer obj, SuccessfulCallback callback) {
        lecturerRemoteDS.updateUserDetails(obj, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                updateLocalUserDetails(obj, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
                updateLocalUserDetails(obj, callback);
            }
        });
    }

    @Override
    public void deleteAccount(String userRole, String userId, SuccessfulCallback callback) {
        lecturerRemoteDS.deleteAccount(userRole, userId, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                deleteAccountFromLocal(userRole, userId, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    private void deleteAccountFromLocal(String userRole, String userId, SuccessfulCallback callback) {
        lecturerLocalDS.deleteAccount(userRole, userId, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    private void updateLocalUserDetails(Lecturer obj, SuccessfulCallback callback) {
        lecturerLocalDS.updateUserDetails(obj, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    private void changeLocalPassword(String userId, String role, String hashedNewPasswd, SuccessCallback callback) {
        lecturerLocalDS.changePassword(userId, role, new SuccessCallback() {
            @Override
            public void success(String message) {
                callback.success(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        }, hashedNewPasswd);
    }

    private void getFromLocalDS(String userId, String userRole, LoadUserDetailsCallback callback) {
        lecturerLocalDS.getDetails(userId, userRole, new LoadUserDetailsCallback<Lecturer>() {
            @Override
            public void loadData(@NotNull Lecturer obj) {
                callback.loadData(obj);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void fetchSettingsFromRemote(FetchSettingsCallback callback) {

    }

    @Override
    public void update(Lecturer item, SuccessfulCallback callback) {

    }

    @Override
    public void delete(Lecturer item, SuccessfulCallback callback) {

    }

    @Override
    public void save(Lecturer item, SuccessfulCallback callback) {
        lecturerLocalDS.save(item, callback);
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

    @Override
    public void deleteLecturer(Lecturer lecturer, SuccessCallback callback) {
        lecturerRemoteDS.deleteLecturer(lecturer, new SuccessCallback() {
            @Override
            public void success(String message) {
                callback.success(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }
}
