package com.example.timetablerapp.data.user;

import com.example.timetablerapp.SuccessfulCallback;
import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.settings.model.DeadlineSettings;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;

/**
 * 19/05/19 -bernard
 */
public interface UserDataSource<T> extends DataSource<T> {
    void userSignUp(UserAuthCallback callBack, T obj, String pass);
    void authUser(UserDataSource.UserAuthCallback callBack, T obj);

    void updateUsername(String name, String userId, String role, SuccessfulCallback callback);

    void fetchSettingsFromRemote(FetchSettingsCallback callback);

    void validateUser(String role, String username, String password, String userId, UserAuthCallback callback);

    void sendUserRole(GetSaltCallBack callBack, String role);

    void getDetails(String userId, String userRole, LoadUserDetailsCallback callback);

    void changePassword(String userId, String role, LecturerDS.SuccessCallback callback, String hashedNewPasswd);

    interface UserAuthCallback {

        void userIsAuthSuccessful(String message);
        void authNotSuccessful(String message);
    }

    interface GetSaltCallBack {

        void successful(String salt);
        void unsuccessful(String message);

    }

    interface FetchSettingsCallback {
        void fetchingSettingsSuccessful(DeadlineSettings settings);
        void settingsNotAvailable(String message);
    }

    interface LoadUserDetailsCallback<T> {
        void loadData(T obj);
        void unsuccessful(String message);
    }
}
