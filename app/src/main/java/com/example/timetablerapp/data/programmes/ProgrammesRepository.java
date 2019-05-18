package com.example.timetablerapp.data.programmes;

import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.programmes.source.ProgLocalDS;
import com.example.timetablerapp.data.programmes.source.ProgRemoteDS;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class ProgrammesRepository implements ProgrammeDS {

    private static ProgrammesRepository INSTANCE = null;
    private ProgLocalDS progLocalDS;
    private ProgRemoteDS progRemoteDS;

    public ProgrammesRepository(ProgLocalDS progLocalDs, ProgRemoteDS progRemoteDS) {
        this.progLocalDS = progLocalDs;
        this.progRemoteDS = progRemoteDS;
    }

    public static ProgrammesRepository newInstance(ProgLocalDS progLocalDS, ProgRemoteDS progRemoteDS) {
        if (INSTANCE == null) {
            INSTANCE = new ProgrammesRepository(progLocalDS, progRemoteDS);
        }
        return INSTANCE;
    }

    @Override
    public void getAllFromRemote(LoadProgrammesCallBack callBack, String name) {
        progRemoteDS.getAllFromRemote(new LoadProgrammesCallBack() {
            @Override
            public void loadProgrammesSuccessfully(List<Programme> programmes) {
                callBack.loadProgrammesSuccessfully(programmes);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        }, name);
    }

    @Override
    public void update(Programme item) {

    }

    @Override
    public void delete(Programme item) {

    }

    @Override
    public void save(Programme item) {

    }
}
