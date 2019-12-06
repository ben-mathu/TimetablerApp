package com.example.timetablerapp.settings;

import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.CampusesRepository;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.util.PerformWordChecks;
import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.AdminRepo;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponse;
import com.example.timetablerapp.data.user.student.StudentRepository;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentResponse;

import org.jetbrains.annotations.NotNull;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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
    private Admin admin = new Admin();
    private Student student = new Student();
    private Lecturer lecturer = new Lecturer();
    private CampusesRepository campusRepo;

    SettingsPresenter(SettingsView view,
                      AdminRepo adminRepo,
                      LecturerRepo lecturerRepo,
                      StudentRepository studentRepo, CampusesRepository campusRepo) {
        this.view = view;
        this.adminRepo = adminRepo;
        this.lecturerRepo = lecturerRepo;
        this.studentRepo = studentRepo;
        this.campusRepo = campusRepo;
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
                    admin = obj;
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
                    student = obj.getStudent();
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
                    lecturer = obj.getLecturer();
                    view.setLecturerDetails(obj);
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        }
    }

    void updateUser(String fullName, String email, String role, String userId, boolean inSession) {
        List<String> names;

        // Check email validity
        if  (!PerformWordChecks.checkEmailValidity(email)) {
            view.showMessage("Your email is invalid.");
            return;
        }

        if (role.equalsIgnoreCase("admin")) {
            names = PerformWordChecks.serializeName(fullName);
            if (names.isEmpty()) {
                view.showMessage("Your name is invalid.");
                return;
            }

            admin.setfName(names.get(0));
            if (names.size() > 2) {
                admin.setmName(names.get(1));
                admin.setlName(names.get(2));
            } else {
                admin.setlName(names.get(1));
            }
            admin.setEmail(email);

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
        } else if (role.equalsIgnoreCase("student")) {
            names = PerformWordChecks.serializeName(fullName);
            if (names.isEmpty()) {
                view.showMessage("Your name is invalid.");
                return;
            }

            student.setFname(names.get(0));
            if (names.size() > 2) {
                student.setMname(names.get(1));
                student.setLname(names.get(2));
            } else {
                admin.setlName(names.get(1));
            }

            student.setEmail(email);
            student.setInSession(inSession);

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
        } else if (role.equalsIgnoreCase("lecturer")){
            names = PerformWordChecks.serializeName(fullName);
            if (names.isEmpty()) {
                view.showMessage("Your name is invalid.");
                return;
            }

            lecturer.setFirstName(names.get(0));
            if (names.size() > 2) {
                lecturer.setMiddleName(names.get(1));
                lecturer.setLastName(names.get(2));
            } else {
                lecturer.setLastName(names.get(1));
            }

            lecturer.setEmail(email);
            lecturer.setInSesson(inSession);

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
    }

    public void changePassword(String passwd, String newPasswd, String userId, String userRole, String currentPasswd) {
        if (newPasswd.length() < 8) {
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

    public void deleteAccount(String userRole, String userId) {
        if (userRole.equalsIgnoreCase("admin"))
            adminRepo.deleteAccount(userRole, userId, new SuccessfulCallback() {
                @Override
                public void successful(String message) {
                    view.showMessage(message);
                    view.logUserOut();
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        else if (userRole.equalsIgnoreCase("student"))
            studentRepo.deleteAccount(userRole, userId, new SuccessfulCallback() {
                @Override
                public void successful(String message) {
                    view.showMessage(message);
                    view.logUserOut();
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        else if (userRole.equalsIgnoreCase("lecturer"))
            lecturerRepo.deleteAccount(userRole, userId, new SuccessfulCallback() {
                @Override
                public void successful(String message) {
                    view.showMessage(message);
                    view.logUserOut();
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
    }
}
