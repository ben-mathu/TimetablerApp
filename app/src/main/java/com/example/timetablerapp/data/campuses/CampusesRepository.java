package com.example.timetablerapp.data.campuses;

import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.campuses.source.CampusLocalDS;
import com.example.timetablerapp.data.campuses.source.CampusRemoteDS;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.login.LoginPresenter;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class CampusesRepository implements CampusesDS {
    private static CampusesRepository INSTANCE = null;
    private CampusLocalDS campusLocalDS;
    private CampusRemoteDS campusRemoteDS;

    public CampusesRepository(CampusLocalDS campusLocalDS, CampusRemoteDS campusRemoteDS) {
        this.campusLocalDS = campusLocalDS;
        this.campusRemoteDS = campusRemoteDS;
    }

    public static CampusesRepository newInstance(CampusLocalDS campusLocalDS, CampusRemoteDS campusRemoteDS) {
        if (INSTANCE == null) {
            INSTANCE = new CampusesRepository(campusLocalDS, campusRemoteDS);
        }
        return INSTANCE;
    }

    @Override
    public void getAllFromRemote(LoadCampusesCallBack callBack) {
        campusRemoteDS.getAllFromRemote(new LoadCampusesCallBack() {
            @Override
            public void loadCampusesSuccessful(List<Campus> campuses) {
                callBack.loadCampusesSuccessful(campuses);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        });
    }

    @Override
    public void addCampus(Campus campus, SuccessFullySavedCallback callback) {
        campusRemoteDS.addCampus(campus, new SuccessFullySavedCallback() {
            @Override
            public void success(String message) {
                callback.success(message);
            }

            @Override
            public void unSuccess(String message) {
                callback.unSuccess(message);
            }
        });
    }

    @Override
    public void update(Campus item) {

    }

    @Override
    public void delete(Campus item) {

    }

    @Override
    public void save(Campus item) {
        campusLocalDS.save(item);
    }
}
