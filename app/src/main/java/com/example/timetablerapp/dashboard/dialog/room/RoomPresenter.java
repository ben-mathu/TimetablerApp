package com.example.timetablerapp.dashboard.dialog.room;

import com.example.timetablerapp.Presenter;
import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.hall.HallDS;
import com.example.timetablerapp.data.hall.HallRepo;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.room.Room;

import java.util.List;

/**
 * 17/11/19
 *
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public class RoomPresenter extends Presenter<RoomView> {
    private RoomView view;
    private HallRepo hallRepo;
    private FacultiesRepository facultyRepo;

    public RoomPresenter(FacultiesRepository facultyRepo, HallRepo hallRepo) {
        this.facultyRepo = facultyRepo;
        this.hallRepo = hallRepo;
    }

    public void getRooms() {
        hallRepo.getRooms(new HallDS.RoomsLoadedCallback() {
            @Override
            public void loadingRoomsSuccessful(List<Room> rooms) {
                view.setRooms(rooms);
            }

            @Override
            public void dataNotAvailable(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getHalls(String facultyId) {
        hallRepo.getHalls(facultyId, new HallDS.HallLoadedCallback() {
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

    public void addRoom(Room room, String passcode) {
        hallRepo.addRoom(room, passcode, new HallDS.Success() {
            @Override
            public void success(String message) {
                view.showMessage(message);
            }

            @Override
            public void unsuccess(String message) {
                view.showMessage(message);
            }
        });
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

    @Override
    public void setView(RoomView item) {
        this.view = item;
    }
}
