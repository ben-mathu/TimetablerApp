package com.example.timetablerapp.dashboard.dialog.program;

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
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.List;

/**
 * 17/11/19
 *
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public class ProgrammePresenter {

    private final CampusesRepository campusRepo;
    private final FacultiesRepository facultyRepo;
    private ProgrammeView view;
    private DepartmentRepository depRepo;
    private final ProgrammesRepository programmesRepo;

    public ProgrammePresenter(ProgrammeView view, CampusesRepository campusRepo, FacultiesRepository facultyRepo, DepartmentRepository depRepo, ProgrammesRepository programmesRepo) {
        this.view = view;
        this.campusRepo = campusRepo;
        this.facultyRepo = facultyRepo;
        this.depRepo = depRepo;
        this.programmesRepo = programmesRepo;
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

    public void getAllProgrammes() {
        programmesRepo.getAllProgrammes(new ProgrammeDS.LoadProgrammesCallBack() {
            @Override
            public void loadProgrammesSuccessfully(List<Programme> programmes) {
                view.setProgrammes(programmes);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getCampusesForDepartment() {
        campusRepo.getAllFromRemote(new CampusesDS.LoadCampusesCallBack() {
            @Override
            public void loadCampusesSuccessful(List<Campus> campuses) {
                view.setCampuses(campuses);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        });
    }

    public void addProgramme(Programme programme) {
        programmesRepo.addProgramme(programme, new ProgrammeDS.SuccessfullySavedCallback() {
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

    public void deleteProgramme(Programme item) {
        programmesRepo.delete(item, new SuccessfulCallback() {
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

    public void updateCourse(Programme p) {
        programmesRepo.update(p, new SuccessfulCallback() {
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
}
