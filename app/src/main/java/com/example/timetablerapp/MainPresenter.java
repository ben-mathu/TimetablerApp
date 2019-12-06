package com.example.timetablerapp;

import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.student.StudentDataSource;
import com.example.timetablerapp.data.user.student.StudentRepository;

/**
 * 07/05/19 -bernard
 */
public class MainPresenter implements BasePresenter {

    private StudentRepository userRepository;
    private MainView view;

    public MainPresenter(StudentRepository userRepository, MainView view) {
        this.userRepository = userRepository;
        this.view = view;
    }

    @Override
    public void sendUserRole(String role) {
        userRepository.sendUserRole(new UserDataSource.GetSaltCallBack() {
            @Override
            public void successful(String salt) {
                view.setSalt(salt);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        }, role);
    }
}
