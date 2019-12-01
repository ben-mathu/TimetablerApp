package com.example.timetablerapp.dashboard.dialog.room;

import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.hall.HallDS;
import com.example.timetablerapp.data.hall.HallRepo;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.room.model.Room;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.List;

/**
 * 17/11/19
 *
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public class RoomPresenter {
    private RoomView view;
    private HallRepo hallRepo;
    private FacultiesRepository facultyRepo;
    private RoomRepo roomRepo;

    RoomPresenter(RoomView view, FacultiesRepository facultyRepo, HallRepo hallRepo, RoomRepo roomRepo) {
        this.view = view;
        this.facultyRepo = facultyRepo;
        this.hallRepo = hallRepo;
        this.roomRepo = roomRepo;
    }

    void getRooms() {
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

    void getHalls(String facultyId) {
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

    void addRoom(Room room, String passcode) {
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

    void getFacultiesForHalls() {
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

    public void getFacultyById(String facultyId) {
        facultyRepo.getFacultyById(facultyId, new FacultyDS.LoadFacultyCallback() {
            @Override
            public void successfullyLoadedFaculty(Faculty faculty) {
                view.loadFaculty(faculty);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    void getHall(String hall_id) {
        hallRepo.getHall(hall_id, new HallDS.LoadHallCallback() {
            @Override
            public void loadHall(Hall hall) {
                view.setHall(hall);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    void deleteHall(Room item) {
        roomRepo.delete(item, new SuccessfulCallback() {
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

    void updateRoom(Room room) {
        roomRepo.update(room, new SuccessfulCallback() {
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
