package com.example.timetablerapp.login;

import com.example.timetablerapp.data.settings.model.DeadlineSettings;

/**
 * 07/05/19 -bernard
 */
public interface LoginView {

    boolean startProgressDialog();

    void showUsernameError(int s);

    String getUsername();

    String getPassword();

    void showPasswordError(int s);

    void startMainActivity();

    String getUserRole();

    void showMessage(String message);

    void startTimetableActivity();

    void configureSettings(DeadlineSettings settings);
}
