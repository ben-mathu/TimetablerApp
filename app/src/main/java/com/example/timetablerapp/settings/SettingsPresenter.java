package com.example.timetablerapp.settings;

import com.example.timetablerapp.SuccessfulCallback;
import com.example.timetablerapp.data.user.admin.AdminRepo;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.student.StudentRepository;

/**
 * 20/11/19
 *
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public class SettingsPresenter {
    private SettingsView view;
    private AdminRepo adminRepo;
    private LecturerRepo lecturerRepo;
    private StudentRepository studentRepo;

    public SettingsPresenter(SettingsView view,
                             AdminRepo adminRepo,
                             LecturerRepo lecturerRepo,
                             StudentRepository studentRepo) {
        this.view = view;
        this.adminRepo = adminRepo;
        this.lecturerRepo = lecturerRepo;
        this.studentRepo = studentRepo;
    }

    public void updateUsername(String name, String userId, String role) {
        if (role.equalsIgnoreCase("admin")) {
            adminRepo.updateUsername(name, userId, role, new SuccessfulCallback() {
                @Override
                public void successful(String message) {
                    view.showMessage(message);
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        }

        if (role.equalsIgnoreCase("student")) {
            studentRepo.updateUsername(name, userId, role, new SuccessfulCallback() {
                @Override
                public void successful(String message) {
                    view.showMessage(message);
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        }

        if (role.equalsIgnoreCase("lecturer")) {
            lecturerRepo.updateUsername(name, userId, role, new SuccessfulCallback() {
                @Override
                public void successful(String message) {
                    view.showMessage(message);
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        }
    }
}
