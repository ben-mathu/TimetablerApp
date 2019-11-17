package com.example.timetablerapp.dashboard.dialog.campus;

import com.example.timetablerapp.Presenter;
import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.CampusesRepository;
import com.example.timetablerapp.data.campuses.model.Campus;

import java.util.List;

/**
 * 17/11/19 -bernard
 */
public class CampusPresenter extends Presenter<CampusView> {
    private CampusView view;
    private CampusesRepository campusRepo;

    public CampusPresenter(CampusesRepository campusRepo) {
        this.campusRepo = campusRepo;
    }

    public void getCampuses() {
        campusRepo.getAllFromRemote(new CampusesDS.LoadCampusesCallBack() {
            @Override
            public void loadCampusesSuccessful(List<Campus> campuses) {
                view.setList(campuses);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        });
    }

    public void addCampus(Campus campus) {
        campusRepo.addCampus(campus, new CampusesDS.SuccessfullySavedCallback() {
            @Override
            public void successItem(Campus campus) {
                view.addCampus(campus);
            }

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

    public void updateCampus(Campus campus) {
        campusRepo.updateCampus(campus, new CampusesDS.SuccessfullySavedCallback() {
            @Override
            public void successItem(Campus campus) {
                view.addCampus(campus);
            }

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

    public void deleteCampus(Campus campus) {
        campusRepo.deleteRemote(campus, new CampusesDS.SuccessfullySavedCallback() {
            @Override
            public void successItem(Campus campus) {
                view.addCampus(campus);
            }

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

    @Override
    public void setView(CampusView item) {
        this.view = item;
    }
}
