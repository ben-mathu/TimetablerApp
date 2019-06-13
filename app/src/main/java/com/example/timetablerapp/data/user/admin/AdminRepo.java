package com.example.timetablerapp.data.user.admin;

import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.source.AdminLocalDS;
import com.example.timetablerapp.data.user.admin.source.AdminRemoteDS;

/**
 * 22/05/19 -bernard
 */
public class AdminRepo implements UserDataSource<Admin> {
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
            public void userIsAuthSuccessfull(String message) {
                callBack.userIsAuthSuccessfull(message);
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
    public void validateUser(String role, String username, String password, UserAuthCallback callback) {

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
