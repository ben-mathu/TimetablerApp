package com.example.timetablerapp.login;

import com.example.timetablerapp.R;
import com.example.timetablerapp.data.user.student.StudentRepository;

/**
 * 06/05/19 -bernard
 */
public class LoginPresenter {
        private final LoginView view;
        private StudentRepository userRepository;

    public LoginPresenter(StudentRepository userRepository, LoginView view) {
        this.userRepository = userRepository;
        this.view = view;
    }

    public void login() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty()) {
            view.showUsernameError(R.string.username_error);
            return;
        } else if (password.isEmpty()) {
            view.showPasswordError(R.string.password_error);
            return;
        }

        userRepository.validateUser(username, password);

    }
}
