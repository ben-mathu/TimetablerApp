package com.example.timetablerapp.dashboard;

import com.example.timetablerapp.dashboard.dialog.campus.CampusView;
import com.example.timetablerapp.dashboard.dialog.course.CourseView;
import com.example.timetablerapp.dashboard.dialog.department.DepartView;
import com.example.timetablerapp.dashboard.dialog.faculty.FacultyView;
import com.example.timetablerapp.dashboard.dialog.lecturer.LecturerView;
import com.example.timetablerapp.dashboard.dialog.program.ProgView;
import com.example.timetablerapp.dashboard.dialog.program.ProgrammeFragment;
import com.example.timetablerapp.dashboard.dialog.room.RoomView;
import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.CampusesRepository;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.DepartmentRepository;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.hall.HallDS;
import com.example.timetablerapp.data.hall.HallRepo;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.programmes.ProgrammeDS;
import com.example.timetablerapp.data.programmes.ProgrammesRepository;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.room.Room;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.UnitsRepo;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class DashboardPresenter {
    private ProgView progView;
    private DepartView departView;
    private FacultyView facultyView;
    private CampusView campusView;
    private RoomView roomView;
    private DashboardView view;
    private LecturerView lecView;
    private CourseView courseView;

    private CampusesRepository campusRepo;
    private UnitsRepo unitsRepo;
    private FacultiesRepository facultyRepo;
    private DepartmentRepository depRepo;
    private ProgrammesRepository progRepo;
    private LecturerRepo lecturerRepo;
    private HallRepo hallRepo;

    public DashboardPresenter(DashboardView view, UnitsRepo unitsRepo) {
        this.view = view;
        this.unitsRepo = unitsRepo;
    }

    public DashboardPresenter(LecturerView view, LecturerRepo lecturerRepo) {
        this.lecView = view;
        this.lecturerRepo = lecturerRepo;
    }

    public DashboardPresenter(CourseView view,
                              UnitsRepo unitsRepo,
                              FacultiesRepository facultyRepo,
                              DepartmentRepository depRepo,
                              ProgrammesRepository progRepo) {
        this.courseView = view;
        this.unitsRepo = unitsRepo;
        this.facultyRepo = facultyRepo;
        this.depRepo = depRepo;
        this.progRepo = progRepo;
    }

    public DashboardPresenter(RoomView view,
                              FacultiesRepository facultyRepo,
                              HallRepo hallRepo) {
        this.roomView = view;
        this.facultyRepo = facultyRepo;
        this.hallRepo = hallRepo;
    }

    public DashboardPresenter(CampusView campusView,
                              CampusesRepository campusRepo) {
        this.campusView = campusView;
        this.campusRepo = campusRepo;
    }

    public DashboardPresenter(FacultyView facultyView, FacultiesRepository facultyRepo, CampusesRepository campusRepo) {
        this.facultyView = facultyView;
        this.facultyRepo = facultyRepo;
        this.campusRepo = campusRepo;
    }

    public DashboardPresenter(DepartView departView, FacultiesRepository facultyRepo, DepartmentRepository depRepo, CampusesRepository campusRepo) {

        this.departView = departView;
        this.facultyRepo = facultyRepo;
        this.depRepo = depRepo;
        this.campusRepo = campusRepo;
    }

    public DashboardPresenter(ProgView progView,
                              CampusesRepository campusRepo,
                              FacultiesRepository facultyRepo,
                              DepartmentRepository depRepo,
                              ProgrammesRepository progRepo) {
        this.progView = progView;
        this.campusRepo = campusRepo;
        this.facultyRepo = facultyRepo;
        this.depRepo = depRepo;
        this.progRepo = progRepo;
    }

    public void getUnitsByLecturerId(String strId) {
        unitsRepo.getUnitsByLecturerId(strId, new UnitDataSource.UnitsLoadedCallback() {
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

    public void getUnitsByStudentId(String strId) {
        unitsRepo.getUnitsByStudentId(strId, new UnitDataSource.UnitsLoadedCallback() {
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

    public void getTimetableByStudentId(String studentId) {
        unitsRepo.getTimetableByStudentId(studentId, new UnitDataSource.TimetableLoadedCallback() {

            @Override
            public void successful(List<Timetable> timetablelist) {
                view.showTimetable(timetablelist);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getTimetableByLecturerId(String lecturerId) {
        unitsRepo.getTimetableByLecturerId(lecturerId, new UnitDataSource.TimetableLoadedCallback() {
            @Override
            public void successful(List<Timetable> timetableList) {
                view.showTimetable(timetableList);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getTimetable() {
        unitsRepo.getTimetable(new UnitDataSource.TimetableLoadedCallback() {
            @Override
            public void successful(List<Timetable> timetableList) {
                view.showTimetable(timetableList);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void setDeadline(String startDate, String deadline) {
        unitsRepo.setRegistrationDeadline(startDate, deadline, new UnitDataSource.UnitsRegisteredCallback() {
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

    public void getLecturers() {
        lecturerRepo.getLecturers(new LecturerDS.LecturersLoadedCallback() {
            @Override
            public void successfullyLoaded(List<Lecturer> list) {
                lecView.setLecturers(list);
            }

            @Override
            public void unsuccessful(String message) {
                lecView.showMessage(message);
            }
        });
    }

    public void createLecturer(String email, String fname, String mname, String lname) {
        lecturerRepo.createLecturer(email, fname, mname, lname, new LecturerDS.CreatingLecturerCallback() {
            @Override
            public void successfullyCreated(LecResponse response) {
                lecView.showMessage(response.getMessage());
                lecView.sendEmail(response);
            }

            @Override
            public void unSuccessful(String message) {
                lecView.showMessage(message);
            }
        });
    }

    public void getCourses() {
        unitsRepo.getCourses(new UnitDataSource.UnitsLoadedCallback() {
            @Override
            public void successful(List<Unit> units) {
                courseView.setUnits(units);
            }

            @Override
            public void unsuccessful(String message) {
                courseView.showMessage(message);
            }
        });
    }

    public void getFaculties() {
        facultyRepo.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                courseView.setFaculties(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                courseView.showMessage(message);
            }
        });
    }



    public void getDepartments(String id) {
        depRepo.getDepsByIdFromRemote(new DepartmentDS.LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                if (courseView != null) {
                    courseView.setDepartments(departments);
                }

                if (progView != null) {
                    progView.setDepartments(departments);
                }
            }

            @Override
            public void dataNotAvailable(String message) {
                if (courseView != null) {
                    courseView.showMessage(message);
                }

                if (progView != null) {
                    progView.showMessage(message);
                }
            }
        }, id);
    }

    public void getProgrammes(String id) {
        progRepo.getAllFromRemote(new ProgrammeDS.LoadProgrammesCallBack() {
            @Override
            public void loadProgrammesSuccessfully(List<Programme> programmes) {
                courseView.setProgrammes(programmes);
            }

            @Override
            public void dataNotAvailable(String message) {
                courseView.showMessage(message);
            }
        }, id);
    }

    public void addCourse(Unit unit, String passCode) {
        unitsRepo.addCourse(unit, passCode, new UnitDataSource.UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                courseView.showMessage(message);
            }

            @Override
            public void unsuccessful(String message) {
                courseView.showMessage(message);
            }
        });
    }

    public void getRooms() {
        hallRepo.getRooms(new HallDS.RoomsLoadedCallback() {
            @Override
            public void loadingRoomsSuccessful(List<Room> rooms) {
                roomView.setRooms(rooms);
            }

            @Override
            public void dataNotAvailable(String message) {
                roomView.showMessage(message);
            }
        });
    }

    public void getHalls(String facultyId) {
        hallRepo.getHalls(facultyId, new HallDS.HallLoadedCallback() {
            @Override
            public void loadingHallsSuccessful(List<Hall> halls) {
                roomView.setHalls(halls);
            }

            @Override
            public void dataNotAvailable(String message) {
                roomView.showMessage(message);
            }
        });
    }

    public void addRoom(Room room, String passcode) {
        hallRepo.addRoom(room, passcode, new HallDS.Success() {
            @Override
            public void success(String message) {
                roomView.showMessage(message);
            }

            @Override
            public void unsuccess(String message) {
                roomView.showMessage(message);
            }
        });
    }

    public void getFacultiesForHalls() {
        facultyRepo.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                roomView.setFaculties(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                roomView.showMessage(message);
            }
        });
    }

    public void getDepartments() {
        depRepo.getAllFromRemote(new DepartmentDS.LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                departView.setDepartments(departments);
            }

            @Override
            public void dataNotAvailable(String message) {
                departView.showMessage(message);
            }
        });
    }

    public void getCampuses() {
        campusRepo.getAllFromRemote(new CampusesDS.LoadCampusesCallBack() {
            @Override
            public void loadCampusesSuccessful(List<Campus> campuses) {
                campusView.setList(campuses);
            }

            @Override
            public void dataNotAvailable(String message) {
                campusView.showMessage(message);
            }
        });
    }

    public void addCampus(Campus campus) {
        campusRepo.addCampus(campus, new CampusesDS.SuccessFullySavedCallback() {
            @Override
            public void success(String message) {
                campusView.showMessage(message);
            }

            @Override
            public void unSuccess(String message) {
                campusView.showMessage(message);
            }
        });
    }

    public void getCampusesForFaculty() {
        campusRepo.getAllFromRemote(new CampusesDS.LoadCampusesCallBack() {
            @Override
            public void loadCampusesSuccessful(List<Campus> campuses) {
                facultyView.setCampusList(campuses);
            }

            @Override
            public void dataNotAvailable(String message) {
                facultyView.showMessage(message);
            }
        });
    }

    public void addFaculty(Faculty faculty) {
        facultyRepo.addFaculty(faculty, new FacultyDS.SuccessFulCallback() {

            @Override
            public void success(String message) {
                facultyView.showMessage(message);
            }

            @Override
            public void unSuccess(String message) {
                facultyView.showMessage(message);
            }
        });
    }

    public void getFacultiesForFaculty() {
        facultyRepo.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                facultyView.setFaculties(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                facultyView.showMessage(message);
            }
        });
    }

    public void getFacultyById(String campusId) {
        facultyRepo.getAllFromRemote(new FacultyDS.LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                if (departView != null) {
                    departView.setFaculties(faculties);
                }

                if (progView != null) {
                    progView.setFaculties(faculties);
                }
            }

            @Override
            public void dataNotAvailable(String message) {
                if (departView != null) {
                    departView.showMessage(message);
                }

                if (departView != null) {
                    progView.showMessage(message);
                }
            }
        }, campusId);
    }

    public void getCampusesForDepartment() {
        campusRepo.getAllFromRemote(new CampusesDS.LoadCampusesCallBack() {
            @Override
            public void loadCampusesSuccessful(List<Campus> campuses) {
                if (departView != null) {
                    departView.setCampusList(campuses);
                }

                if (progView != null) {
                    progView.setCampuses(campuses);
                }
            }

            @Override
            public void dataNotAvailable(String message) {
                if (departView != null) {
                    departView.showMessage(message);
                }

                if (progView != null) {
                    progView.showMessage(message);
                }
            }
        });
    }

    public void addDepartment(Department department) {
        depRepo.addDepartment(department, new DepartmentDS.SuccessfulCallback() {
            @Override
            public void success(String message) {
                departView.showMessage(message);
            }

            @Override
            public void unSuccessful(String message) {
                departView.showMessage(message);
            }
        });
    }

    public void getAllProgrammes() {
        progRepo.getAllProgrammes(new ProgrammeDS.LoadProgrammesCallBack() {
            @Override
            public void loadProgrammesSuccessfully(List<Programme> programmes) {
                progView.setProgrammes(programmes);
            }

            @Override
            public void dataNotAvailable(String message) {
                progView.showMessage(message);
            }
        });
    }

    public void addProgramme(Programme programme) {
        progRepo.addProgramme(programme, new ProgrammeDS.SuccessfullySavedCallback() {
            @Override
            public void success(String message) {
                progView.showMessage(message);
            }

            @Override
            public void unSuccessful(String message) {
                progView.showMessage(message);
            }
        });
    }
}
