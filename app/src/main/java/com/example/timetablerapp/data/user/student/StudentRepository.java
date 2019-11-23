package com.example.timetablerapp.data.user.student;


import com.example.timetablerapp.SuccessfulCallback;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentResponse;
import com.example.timetablerapp.data.user.student.source.StudentLocalDS;
import com.example.timetablerapp.data.user.student.source.StudentRemoteDS;

import org.jetbrains.annotations.NotNull;

/**
 * 06/05/19 -bernard
 */
public class StudentRepository implements UserDataSource<Student> {
    private static StudentRepository INSTANCE = null;
    private StudentRemoteDS userDataSourceRemote;
    private StudentLocalDS userDataSourceLocal;


    public StudentRepository(StudentRemoteDS userDataSourceRemote, StudentLocalDS userDataSourceLocal) {
        this.userDataSourceLocal = userDataSourceLocal;
        this.userDataSourceRemote = userDataSourceRemote;
    }

    public static StudentRepository newInstance(StudentRemoteDS userDataSourceRemote, StudentLocalDS userDataSourceLocal) {
        if (INSTANCE == null) {
            INSTANCE = new StudentRepository(userDataSourceRemote, userDataSourceLocal);
        }
        return INSTANCE;
    }

    @Override
    public void userSignUp(UserAuthCallback callBack, Student obj, String pass) {
        save(obj);
        userDataSourceRemote.userSignUp(new UserAuthCallback() {
            @Override
            public void userIsAuthSuccessful(String message) {
                callBack.userIsAuthSuccessful(message);
            }

            @Override
            public void authNotSuccessful(String message) {
                callBack.authNotSuccessful(message);
            }
        }, obj, "");

    }

    @Override
    public void authUser(UserAuthCallback callBack, Student obj) {

    }

    @Override
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        userDataSourceRemote.updateUsername(name, userId, role, new SuccessfulCallback() {
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
        userDataSourceLocal.updateUsername(name, userId, role, new SuccessfulCallback() {
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
    public void validateUser(String role, String username, String password, String userId, UserAuthCallback callback) {
        userDataSourceRemote.validateUser(role, username, password, userId, new UserAuthCallback() {
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
        userDataSourceRemote.sendUserRole(new GetSaltCallBack() {
            @Override
            public void successful(String salt) {
                callBack.successful(salt);
            }

            @Override
            public void unsuccessful(String message) {
                callBack.unsuccessful(message);
            }
        }, role);
    }

    @Override
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback callback) {
        userDataSourceRemote.getDetails(userId, userRole, new LoadUserDetailsCallback<StudentResponse>() {
            @Override
            public void loadData(@NotNull StudentResponse obj) {
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
    public void changePassword(String userId, String role, LecturerDS.SuccessCallback callback, String hashedNewPasswd) {
        userDataSourceRemote.changePassword(userId, role, new LecturerDS.SuccessCallback() {
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

    private void changeLocalPassword(String userId, String role, String hashedNewPasswd, LecturerDS.SuccessCallback callback) {
        userDataSourceLocal.changePassword(userId, role, new LecturerDS.SuccessCallback() {
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
        userDataSourceLocal.getDetails(userId, userRole, new LoadUserDetailsCallback<StudentResponse>() {
            @Override
            public void loadData(@NotNull StudentResponse obj) {
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
    public void update(Student item) {

    }

    @Override
    public void delete(Student item) {

    }

    @Override
    public void save(Student item) {
        userDataSourceLocal.save(item);
    }
}
