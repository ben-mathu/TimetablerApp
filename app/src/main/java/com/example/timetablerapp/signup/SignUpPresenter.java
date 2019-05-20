package com.example.timetablerapp.signup;

import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;
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
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.student.StudentRepository;

import java.security.MessageDigest;
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

    public SignUpPresenter(SignUpContract.View view,
                           DepartmentRepository departmentRepository,
                           ProgrammesRepository programmesRepository,
                           CampusesRepository campusesRepository,
                           FacultiesRepository facultiesRepository,
                           StudentRepository studentRepo,
                           LecturerRepo lecturerRepo) {
        this.departmentRepository = departmentRepository;
        this.programmesRepository = programmesRepository;
        this.campusesRepository = campusesRepository;
        this.facultiesRepository = facultiesRepository;
        this.studentRepo = studentRepo;
        this.lecturerRepo = lecturerRepo;
        this.view = view;
    }

    @Override
    public void getDepartments(String id) {
        departmentRepository.getAllFromRemote(new DepartmentDS.LoadDepartmentsCallBack() {
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
    public void getProgrammes(String departmentName) {
        programmesRepository.getAllFromRemote(new ProgrammeDS.LoadProgrammesCallBack() {
            @Override
            public void loadProgrammesSuccessfully(List<Programme> programmes) {
                view.showProgrammes(programmes);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessages(message);
            }
        }, departmentName);
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
            public void gettinFacultiesSuccessful(List<Faculty> faculties) {
                view.showFaculties(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessages(message);
            }
        }, campusId);
    }

    @Override
    public void registerUser(Lecturer lec) {
        try {
            String hash = createHash(lec.getPassword());
            lec.setPassword(hash);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "registerUser: ", e);
            e.printStackTrace();
        }
        lecturerRepo.userSignUp(new UserDataSource.UserAuthCallback() {
            @Override
            public void userIsAuthSuccessfull(String message) {
                view.showMessages(message);
                view.startLoginActiity();
            }

            @Override
            public void authNotSuccessful(String message) {
                view.showMessages(message);
            }
        }, lec);
    }

    @Override
    public void getFaculties() {
        facultiesRepository.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
            @Override
            public void gettinFacultiesSuccessful(List<Faculty> faculties) {
                view.showFaculties(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessages(message);
            }
        });
    }
}
