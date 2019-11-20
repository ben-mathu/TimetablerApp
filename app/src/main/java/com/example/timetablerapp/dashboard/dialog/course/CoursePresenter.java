package com.example.timetablerapp.dashboard.dialog.course;

import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.DepartmentRepository;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.ProgrammeDS;
import com.example.timetablerapp.data.programmes.ProgrammesRepository;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.UnitsRepo;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 17/11/19
 *
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public class CoursePresenter {
    private CourseView view;
    private UnitsRepo unitsRepo;
    private FacultiesRepository facultyRepo;
    private final DepartmentRepository depRepo;
    private final ProgrammesRepository programmeRepo;

    public CoursePresenter(CourseView view,
                           UnitsRepo unitsRepo,
                           FacultiesRepository facultyRepo,
                           DepartmentRepository depRepo,
                           ProgrammesRepository programmeRepo) {
        this.view = view;
        this.unitsRepo = unitsRepo;
        this.facultyRepo = facultyRepo;
        this.depRepo = depRepo;
        this.programmeRepo = programmeRepo;
    }

    public void getCourses() {
        unitsRepo.getCourses(new UnitDataSource.UnitsLoadedCallback() {
            @Override
            public void successful(List<Unit> units) {
                view.setUnits(units);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getFaculties() {
        facultyRepo.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
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

    public void getDepartments(String id) {
        depRepo.getDepsByIdFromRemote(new DepartmentDS.LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                view.setDepartments(departments);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        }, id);
    }

    public void getProgrammes(String departmentId) {
        programmeRepo.getAllFromRemote(new ProgrammeDS.LoadProgrammesCallBack() {
            @Override
            public void loadProgrammesSuccessfully(List<Programme> programmes) {
                view.setProgrammes(programmes);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        }, departmentId);
    }

    public void addCourse(Unit unit, String passCode) {
        unitsRepo.addCourse(unit, passCode, new UnitDataSource.UnitsRegisteredCallback() {
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



    public void deleteCourse(Unit item) {
        unitsRepo.deleteCourse(item, new UnitDataSource.UnitsRegisteredCallback() {
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

    public void updateCourse(Unit unit) {
        unitsRepo.updateCourse(unit, new UnitDataSource.UnitsRegisteredCallback() {
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
