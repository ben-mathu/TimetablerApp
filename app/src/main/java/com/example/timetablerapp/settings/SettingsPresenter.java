package com.example.timetablerapp.settings;

import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.AdminRepo;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponse;
import com.example.timetablerapp.data.user.student.StudentRepository;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentResponse;

import org.jetbrains.annotations.NotNull;

import java.security.NoSuchAlgorithmException;

import static com.example.timetablerapp.data.encryption.Hashing.createHash;

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

    private String hashedPasswd = "";
    private String hashedNewPasswd = "";

    SettingsPresenter(SettingsView view,
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

    void setUserDetails(String userId, String userRole) {
        if (userRole.equalsIgnoreCase("admin")) {
            adminRepo.getDetails(userId, userRole, new UserDataSource.LoadUserDetailsCallback<Admin>() {
                @Override
                public void loadData(@NotNull Admin obj) {
                    view.setAdminDetails(obj);
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        } else if (userRole.equalsIgnoreCase("student")) {
            studentRepo.getDetails(userId, userRole, new UserDataSource.LoadUserDetailsCallback<StudentResponse>() {

                @Override
                public void loadData(@NotNull StudentResponse obj) {
                    view.setStudentDetails(obj);
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        } else if (userRole.equalsIgnoreCase("lecturer")) {
            lecturerRepo.getDetails(userId, userRole, new UserDataSource.LoadUserDetailsCallback<LecturerResponse>() {
                @Override
                public void loadData(@NotNull LecturerResponse obj) {
                    view.setLecturerDetails(obj);
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        }
    }

    void updateAdmin(Admin admin) {
        adminRepo.updateUserDetails(admin, new SuccessfulCallback() {
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

    void updateStudent(Student student) {
        studentRepo.updateUserDetails(student, new SuccessfulCallback() {
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

    void updateLecturer(Lecturer lecturer) {
        lecturerRepo.updateUserDetails(lecturer, new SuccessfulCallback() {
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

    public void changePassword(String passwd, String newPasswd, String userId, String userRole, String currentPasswd) {
        if (currentPasswd.length() < 8) {
            view.showMessage("Password must be at least 8 characters long.");
            return;
        }

        try {
            hashedPasswd = createHash(currentPasswd);
            hashedNewPasswd = createHash(newPasswd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (userRole.equalsIgnoreCase("student")) {
            if (hashedPasswd.equals(passwd)) {
                studentRepo.changePassword(userId, userRole, new LecturerDS.SuccessCallback() {
                    @Override
                    public void success(String message) {
                        view.showMessage(message);
                    }

                    @Override
                    public void unsuccessful(String message) {
                        view.showMessage(message);
                    }
                }, hashedNewPasswd);
            } else {
                view.showMessage("Current password is incorrect.");
            }
        } else if (userRole.equalsIgnoreCase("lecturer")) {
            if (hashedPasswd.equals(passwd)) {
                lecturerRepo.changePassword(userId, userRole, new LecturerDS.SuccessCallback() {
                    @Override
                    public void success(String message) {
                        view.showMessage(message);
                    }

                    @Override
                    public void unsuccessful(String message) {
                        view.showMessage(message);
                    }
                }, hashedNewPasswd);
            } else {
                view.showMessage("Current password is incorrect.");
            }
        } else if (userRole.equalsIgnoreCase("admin")) {
            if (hashedPasswd.equals(passwd)) {
                adminRepo.changePassword(userId, userRole, new LecturerDS.SuccessCallback() {
                    @Override
                    public void success(String message) {
                        view.showMessage(message);
                    }

                    @Override
                    public void unsuccessful(String message) {
                        view.showMessage(message);
                    }
                }, hashedNewPasswd);
            } else {
                view.showMessage("Current password is incorrect.");
            }
        }
    }
}
