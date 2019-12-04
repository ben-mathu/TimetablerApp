package com.example.timetablerapp.signup;

import android.util.Log;

import com.example.timetablerapp.data.user.admin.AdminRepo;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.CampusesRepository;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.DepartmentRepository;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.ProgrammeDS;
import com.example.timetablerapp.data.programmes.ProgrammesRepository;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.student.StudentRepository;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.example.timetablerapp.data.encryption.Hashing.createHash;

/**
 * 08/05/19 -bernard
 */
public class SignUpPresenter implements SignUpContract.Presenter {
    private static final String TAG = SignUpPresenter.class.getSimpleName();

    private SignUpContract.View view;
    private DepartmentRepository departmentRepository;
    private ProgrammesRepository programmesRepository;
    private CampusesRepository campusesRepository;
    private FacultiesRepository facultiesRepository;
    private StudentRepository studentRepo;
    private LecturerRepo lecturerRepo;
    private AdminRepo adminRepo;
    private SuccessfulCallback callback;

    public SignUpPresenter(SignUpContract.View view,
                           DepartmentRepository departmentRepository,
                           ProgrammesRepository programmesRepository,
                           CampusesRepository campusesRepository,
                           FacultiesRepository facultiesRepository,
                           StudentRepository studentRepo,
                           LecturerRepo lecturerRepo,
                           AdminRepo adminRepo) {
        this.departmentRepository = departmentRepository;
        this.programmesRepository = programmesRepository;
        this.campusesRepository = campusesRepository;
        this.facultiesRepository = facultiesRepository;
        this.studentRepo = studentRepo;
        this.lecturerRepo = lecturerRepo;
        this.view = view;
        this.adminRepo = adminRepo;
    }

    @Override
    public void getDepartments(String id) {
        departmentRepository.getDepsByIdFromRemote(new DepartmentDS.LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                view.showDepartments(departments);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessages(message);
            }
        }, id);
    }

    @Override
    public void getProgrammes(String departmentId) {
        programmesRepository.getAllFromRemote(new ProgrammeDS.LoadProgrammesCallBack() {
            @Override
            public void loadProgrammesSuccessfully(List<Programme> programmes) {
                view.showProgrammes(programmes);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessages(message);
            }
        }, departmentId);
    }

    @Override
    public void getCampuses() {
        campusesRepository.getAllFromRemote(new CampusesDS.LoadCampusesCallBack() {
            @Override
            public void loadCampusesSuccessful(List<Campus> campuses) {
                view.showCampuses(campuses);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessages(message);
            }
        });
    }

    @Override
    public void getFaculties(String campusId) {
        facultiesRepository.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                view.showFaculties(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessages(message);
            }
        }, campusId);
    }

    @Override
    public void registerUser(Lecturer lec, String passw, Faculty faculty, Department department) {
        if (lec.getPassword().isEmpty() ||
                lec.getFacultyId().isEmpty() ||
                lec.getDepartmentId().isEmpty() ||
                lec.getUsername().isEmpty()
        ) {
            view.showMessages("Please fill out the form all field are required");
            return;
        }

        try {
            String hash = createHash(lec.getPassword());
            lec.setPassword(hash);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "registerUser: ", e);
            e.printStackTrace();
        }
        lecturerRepo.userSignUp(new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                view.showMessages(message);
                view.startLoginActivity();
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessages(message);
            }
        }, lec, passw);

        departmentRepository.save(department, callback);
        facultiesRepository.save(faculty, callback);
    }

    @Override
    public void registerUser(Student student, Department department, Faculty faculty, Campus campus, Programme programme) {
        if (student.getPassword().isEmpty() ||
                student.getFacultyId().isEmpty() ||
                student.getDepartmentId().isEmpty() ||
                student.getFname().isEmpty() ||
                student.getStudentId().isEmpty() ||
                student.getLname().isEmpty() ||
                student.getMname().isEmpty() ||
                student.getUsername().isEmpty() ||
                student.getAdmissionDate().isEmpty() ||
                student.getProgrammeId().isEmpty() ||
                student.getYearOfStudy().isEmpty()
        ) {
            view.showMessages("Please fill out the form all field are required");
            return;
        }

        try {
            String hash = createHash(student.getPassword());
            student.setPassword(hash);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "registerUser: ", e);
            e.printStackTrace();
        }
        studentRepo.userSignUp(new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                view.showMessages(message);
                view.startLoginActivity();
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessages(message);
            }
        }, student, "");

        // save attributes for the students
        departmentRepository.save(department, callback);
        facultiesRepository.save(faculty, callback);
        programmesRepository.save(programme, callback);
        campusesRepository.save(campus, callback);
    }

    @Override
    public void registerUser(Admin admin, String pass) {
        if (admin.getPassword().isEmpty() ||
                admin.getfName().isEmpty() ||
                admin.getAdminId().isEmpty() ||
                admin.getlName().isEmpty() ||
                admin.getmName().isEmpty() ||
                admin.getUsername().isEmpty() ||
                pass.isEmpty()
        ) {
            view.showMessages("Please fill out the form all field are required");
            return;
        }

        try {
            String hash = createHash(admin.getPassword());
            admin.setPassword(hash);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "registerUser: ", e);
            e.printStackTrace();
        }
        adminRepo.userSignUp(new SuccessfulCallback() {

            @Override
            public void successful(String message) {
                view.showMessages(message);
                view.startLoginActivity();
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessages(message);
            }
        }, admin, pass);
    }

    @Override
    public void getFaculties() {
        facultiesRepository.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                view.showFaculties(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessages(message);
            }
        });
    }
}
