package com.example.timetablerapp.dashboard.dialog.lecturer;

import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.DepartmentRepository;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import java.util.List;

/**
 * 17/11/19
 *
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public class LecturerPresenter {
    private LecturerView view;
    private LecturerRepo lecturerRepo;
    private FacultiesRepository facultiesRepo;
    private DepartmentRepository departmentRepo;

    public LecturerPresenter(LecturerView view,
                             LecturerRepo lecturerRepo,
                             FacultiesRepository facultiesRepo,
                             DepartmentRepository departmentRepo) {
        this.view = view;
        this.lecturerRepo = lecturerRepo;
        this.facultiesRepo = facultiesRepo;
        this.departmentRepo = departmentRepo;
    }

    public void getLecturers() {
        lecturerRepo.getLecturers(new LecturerDS.LecturersLoadedCallback() {
            @Override
            public void successfullyLoaded(List<Lecturer> list) {
                view.setLecturers(list);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void createLecturer(String email, String fname, String mname, String lname) {
        lecturerRepo.createLecturer(email, fname, mname, lname, new LecturerDS.CreatingLecturerCallback() {
            @Override
            public void successfullyCreated(LecResponse response) {
                view.showMessage(response.getMessage());
                view.sendEmail(response);
            }

            @Override
            public void unSuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getFaculty(String facultyId) {
        facultiesRepo.getFacultyById(facultyId, new FacultyDS.LoadFacultyCallback() {
            @Override
            public void successfullyLoadedFaculty(Faculty faculty) {
                view.setFaculty(faculty);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getDepartmentById(String departmentId) {
        departmentRepo.getDepartmentById(departmentId, new DepartmentDS.LoadDepartmentCallback() {
            @Override
            public void loadDepartment(Department department) {
                view.setDepartment(department);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getFaculties() {
        facultiesRepo.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                view.setFaculties(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getDepartments(String facultyId) {
        departmentRepo.getDepsByIdFromRemote(new DepartmentDS.LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                view.setDepartments(departments);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        }, facultyId);
    }

    public void deleteLecturer(Lecturer item) {
        lecturerRepo.deleteLecturer(item, new LecturerDS.SuccessCallback() {

            @Override
            public void success(String message) {
                view.showMessage(message);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void updateLecturer(Lecturer lecturer) {
        lecturerRepo.updateLecturer(lecturer, new LecturerDS.SuccessCallback() {
            @Override
            public void success(String message) {
                view.showMessage(message);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }
}
