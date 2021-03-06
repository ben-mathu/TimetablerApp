package com.example.timetablerapp.data.user;

import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.settings.model.DeadlineSettings;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;

/**
 * 19/05/19 -bernard
 */
public interface UserDataSource<T, E> extends DataSource<T> {
    void userSignUp(SuccessfulCallback callBack, T obj, String pass);
    void authUser(SuccessfulCallback callBack, T obj);

    void updateUsername(String name, String userId, String role, SuccessfulCallback callback);

    void fetchSettingsFromRemote(FetchSettingsCallback callback);

    void validateUser(String role, String username, String password, String userId, SuccessfulCallback callback);

    void sendUserRole(GetSaltCallBack callBack, String role);

    void getDetails(String userId, String userRole, LoadUserDetailsCallback<E> callback);

    void changePassword(String userId, String role, LecturerDS.SuccessCallback callback, String hashedNewPasswd);

    void updateUserDetails(T obj, SuccessfulCallback callback);

    void deleteAccount(String userRole, String userId, SuccessfulCallback callback);

    interface GetSaltCallBack {

        void successful(String salt);
        void unsuccessful(String message);

    }

    interface FetchSettingsCallback {
        void fetchingSettingsSuccessful(DeadlineSettings settings);
        void settingsNotAvailable(String message);
    }

    interface LoadUserDetailsCallback<E> {
        void loadData(E obj);
        void unsuccessful(String message);
    }
}
