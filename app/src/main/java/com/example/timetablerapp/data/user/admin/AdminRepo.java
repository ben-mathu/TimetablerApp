package com.example.timetablerapp.data.user.admin;

import com.example.timetablerapp.SuccessfulCallback;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.source.AdminLocalDS;
import com.example.timetablerapp.data.user.admin.source.AdminRemoteDS;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;

import org.jetbrains.annotations.NotNull;

/**
 * 22/05/19 -bernard
 */
public class AdminRepo implements UserDataSource<Admin>{
    private static final String TAG = AdminRepo.class.getSimpleName();

    private static AdminRepo INSTANCE;
    private final AdminLocalDS localDs;
    private final AdminRemoteDS remoteDs;

    public AdminRepo(AdminLocalDS localDs, AdminRemoteDS remoteDs) {
        this.localDs = localDs;
        this.remoteDs = remoteDs;
    }

    public static AdminRepo newInstance(AdminLocalDS localDS, AdminRemoteDS remoteDs) {
        if (INSTANCE == null) {
            INSTANCE = new AdminRepo(localDS, remoteDs);
        }
        return INSTANCE;
    }

    @Override
    public void userSignUp(UserAuthCallback callBack, Admin obj, String pass) {
        remoteDs.userSignUp(new UserAuthCallback() {
            @Override
            public void userIsAuthSuccessful(String message) {
                callBack.userIsAuthSuccessful(message);
                save(obj);
            }

            @Override
            public void authNotSuccessful(String message) {
                callBack.authNotSuccessful(message);
            }
        }, obj, pass);
    }

    @Override
    public void authUser(UserAuthCallback callBack, Admin obj) {

    }

    @Override
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        remoteDs.updateUsername(name, userId, role , new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                updateLocalUsername(name, userId, role, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
                updateLocalUsername(name, userId, role, callback);
            }
        });
    }

    @Override
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback callback) {
        remoteDs.getDetails(userId, userRole, new LoadUserDetailsCallback<Admin>() {
            @Override
            public void loadData(@NotNull Admin obj) {
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
        remoteDs.changePassword(userId, role, new LecturerDS.SuccessCallback() {
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
        localDs.changePassword(userId, role, new LecturerDS.SuccessCallback() {
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
        localDs.getDetails(userId, userRole, new LoadUserDetailsCallback<Admin>() {
            @Override
            public void loadData(@NotNull Admin obj) {
                callback.loadData(obj);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    private void updateLocalUsername(String name, String userId, String role, SuccessfulCallback callback) {
        localDs.updateUsername(name, userId, role, new SuccessfulCallback() {
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
        localDs.save(item);
    }
}
