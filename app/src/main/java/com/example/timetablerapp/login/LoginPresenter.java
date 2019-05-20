package com.example.timetablerapp.login;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.encryption.Hashing;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.student.StudentRepository;

import java.security.NoSuchAlgorithmException;

import static com.example.timetablerapp.data.encryption.Hashing.createHash;

/**
 * 06/05/19 -bernard
 */
public class LoginPresenter {
    private final LoginView view;
    private StudentRepository studentRepository;
    private final LecturerRepo lecturerRepo;

    public LoginPresenter(StudentRepository studentRepository, LecturerRepo lecturerRepo, LoginView view) {
        this.studentRepository = studentRepository;
        this.lecturerRepo = lecturerRepo;
        this.view = view;
    }

    public void login() {
        String username = view.getUsername();
        String password = view.getPassword();
        String role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");

        if (username.isEmpty()) {
            view.showUsernameError(R.string.username_error);
            return;
        } else if (password.isEmpty()) {
            view.showPasswordError(R.string.password_error);
            return;
        }

        try {
            password = createHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        lecturerRepo.validateUser(role, username, password, new UserDataSource.UserAuthCallback() {
            @Override
            public void userIsAuthSuccessfull(String message) {
                view.showMessage(message);
                view.startTimetableActivity();
            }

            @Override
            public void authNotSuccessful(String message) {
                view.showUsernameError(R.string.auth_error);
                view.showPasswordError(R.string.auth_error);
            }
        });

    }
}