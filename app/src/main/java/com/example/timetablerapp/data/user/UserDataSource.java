package com.example.timetablerapp.data.user;

import com.example.timetablerapp.SuccessfulCallback;
import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.settings.model.DeadlineSettings;

/**
 * 19/05/19 -bernard
 */
public interface UserDataSource<T> extends DataSource<T> {
    void userSignUp(UserAuthCallback callBack, T obj, String pass);
    void authUser(UserDataSource.UserAuthCallback callBack, T obj);

    void updateUsername(String name, String userId, String role, SuccessfulCallback callback);

    interface UserAuthCallback {

        void userIsAuthSuccessful(String message);
        void authNotSuccessful(String message);
    }

    void validateUser(String role, String username, String password, String userId, UserAuthCallback callback);

    void sendUserRole(GetSaltCallBack callBack, String role);

    interface GetSaltCallBack {

        void successful(String salt);
        void unsuccessful(String message);

    }

    void fetchSettingsFromRemote(FetchSettingsCallback callback);

    interface FetchSettingsCallback {
        void fetchingSettingsSuccessful(DeadlineSettings settings);
        void settingsNotAvailable(String message);
    }
}
