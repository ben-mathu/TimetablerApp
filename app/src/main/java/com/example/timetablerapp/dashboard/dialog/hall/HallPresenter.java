package com.example.timetablerapp.dashboard.dialog.hall;

import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.hall.HallDS;
import com.example.timetablerapp.data.hall.HallRepo;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.List;

/**
 * 03/12/19
 *
 * @author bernard
 */
public class HallPresenter {
    private final FacultiesRepository facultyRepo;
    private final HallRepo hallRepo;
    private HallView view;

    public HallPresenter(HallView view, FacultiesRepository facultyRepo, HallRepo hallRepo) {
        this.view = view;
        this.facultyRepo = facultyRepo;
        this.hallRepo = hallRepo;
    }

    public void getFacultiesForHalls() {
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

    public void getHalls() {
        hallRepo.getHalls(new HallDS.HallLoadedCallback() {
            @Override
            public void loadingHallsSuccessful(List<Hall> halls) {
                view.setHalls(halls);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        });
    }

    public void addHall(Hall hall) {
        hallRepo.addHall(hall, new SuccessfulCallback() {
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

    public void getFacultyById(String facultyId) {
        facultyRepo.getFacultyById(facultyId, new FacultyDS.LoadFacultyCallback() {
            @Override
            public void successfullyLoadedFaculty(Faculty faculty) {
                view.setFaculty(faculty);
            }

            @Override
            public void unsuccessful(String message) {

            }
        });
    }

    public void deleteHall(Hall item) {
        hallRepo.delete(item, new SuccessfulCallback() {
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

    public void updateHall(Hall hall) {
        hallRepo.update(hall, new SuccessfulCallback() {
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
