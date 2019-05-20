package com.example.timetablerapp.data.faculties;

import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.faculties.source.FacultyLocalDS;
import com.example.timetablerapp.data.faculties.source.FacultyRemoteDS;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class FacultiesRepository implements FacultyDS {

    private static FacultiesRepository INSTANCE = null;
    private FacultyLocalDS facultyLocalDS;
    private FacultyRemoteDS facultyRemoteDS;

    public FacultiesRepository(FacultyLocalDS facultyLocalDS,  FacultyRemoteDS facultyRemoteDS) {
        this.facultyLocalDS = facultyLocalDS;
        this.facultyRemoteDS = facultyRemoteDS;
    }

    public static FacultiesRepository newInstance(FacultyLocalDS facultyLocalDS, FacultyRemoteDS facultyRemoteDS) {
        if (INSTANCE == null) {
            INSTANCE = new FacultiesRepository(facultyLocalDS, facultyRemoteDS);
        }
        return INSTANCE;
    }

    @Override
    public void getAllFromRemote(LoadFacultiesCallBack callBack, String campusId) {
        facultyRemoteDS.getAllFromRemote(new LoadFacultiesCallBack() {
            @Override
            public void gettinFacultiesSuccessful(List<Faculty> faculties) {
                callBack.gettinFacultiesSuccessful(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        }, campusId);
    }

    @Override
    public void getAllFromRemote(LoadFacultiesCallBack callBack) {
        facultyRemoteDS.getAllFromRemote(new LoadFacultiesCallBack() {
            @Override
            public void gettinFacultiesSuccessful(List<Faculty> faculties) {
                callBack.gettinFacultiesSuccessful(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        });
    }

    @Override
    public void update(Faculty item) {

    }

    @Override
    public void delete(Faculty item) {

    }

    @Override
    public void save(Faculty item) {

    }
}
