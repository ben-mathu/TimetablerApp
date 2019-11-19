package com.example.timetablerapp.dashboard.dialog.faculty;

import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.CampusesRepository;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;

import java.util.List;

/**
 * 17/11/19
 *
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public class FacultyPresenter {
    private FacultyView view;
    private CampusesRepository campusRepo;
    private FacultiesRepository facultyRepo;

    public FacultyPresenter(FacultyView view, FacultiesRepository facultyRepo, CampusesRepository campusRepo) {
        this.view = view;
        this.facultyRepo = facultyRepo;
        this.campusRepo = campusRepo;
    }

    public void getCampusesForFaculty() {
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

    public void addFaculty(Faculty faculty) {
        facultyRepo.addFaculty(faculty, new FacultyDS.SuccessFulCallback() {

            @Override
            public void success(String message) {
                view.showMessage(message);
            }

            @Override
            public void unSuccess(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getFacultiesForFaculty() {
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
