package com.example.timetablerapp.dashboard.dialog.department;

import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.CampusesRepository;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.DepartmentRepository;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;

import java.util.List;

/**
 * 17/11/19
 *
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public class DepartmentPresenter {
    private final FacultiesRepository facultyRepo;
    private DepartView view;
    private DepartmentRepository depRepo;
    private final CampusesRepository campusRepo;

    public DepartmentPresenter(DepartView view, FacultiesRepository facultyRepo, DepartmentRepository depRepo, CampusesRepository campusRepo) {
        this.view = view;
        this.facultyRepo = facultyRepo;
        this.depRepo = depRepo;
        this.campusRepo = campusRepo;
    }

    public void getDepartments() {
        depRepo.getAllFromRemote(new DepartmentDS.LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                view.setDepartments(departments);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getFacultyById(String campusId) {
        facultyRepo.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                view.setFaculties(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        }, campusId);
    }

    public void getCampusesForDepartment() {
        campusRepo.getAllFromRemote(new CampusesDS.LoadCampusesCallBack() {
            @Override
            public void loadCampusesSuccessful(List<Campus> campuses) {
                view.setCampusList(campuses);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        });
    }

    public void addDepartment(Department department) {
        depRepo.addDepartment(department, new DepartmentDS.SuccessfulCallback() {
            @Override
            public void success(String message) {
                view.showMessage(message);
            }

            @Override
            public void unSuccessful(String message) {
                view.showMessage(message);
            }
        });
    }
}
