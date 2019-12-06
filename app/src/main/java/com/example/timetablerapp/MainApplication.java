package com.example.timetablerapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;

import com.example.timetablerapp.dashboard.dialog.room.RoomRepo;
import com.example.timetablerapp.data.campuses.CampusesRepository;
import com.example.timetablerapp.data.campuses.source.CampusLocalDS;
import com.example.timetablerapp.data.campuses.source.CampusRemoteDS;
import com.example.timetablerapp.data.db.TimetablerDatabaseHelper;
import com.example.timetablerapp.data.department.DepartmentRepository;
import com.example.timetablerapp.data.department.source.DepartmentLocalDataSrc;
import com.example.timetablerapp.data.department.source.DepartmentRemoteDataSrc;
import com.example.timetablerapp.data.faculties.FacultiesRepository;
import com.example.timetablerapp.data.faculties.source.FacultyLocalDS;
import com.example.timetablerapp.data.faculties.source.FacultyRemoteDS;
import com.example.timetablerapp.data.hall.HallRepo;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.hall.source.HallLocalDS;
import com.example.timetablerapp.data.hall.source.HallRemoteDS;
import com.example.timetablerapp.data.programmes.ProgrammesRepository;
import com.example.timetablerapp.data.programmes.source.ProgLocalDS;
import com.example.timetablerapp.data.programmes.source.ProgRemoteDS;
import com.example.timetablerapp.data.room.source.local.RoomLocalDS;
import com.example.timetablerapp.data.room.source.remote.RoomRemoteDS;
import com.example.timetablerapp.data.units.UnitsRepo;
import com.example.timetablerapp.data.units.source.local.UnitsLocalDS;
import com.example.timetablerapp.data.units.source.remote.UnitsRemoteDS;
import com.example.timetablerapp.data.user.admin.AdminRepo;
import com.example.timetablerapp.data.user.admin.source.AdminLocalDS;
import com.example.timetablerapp.data.user.admin.source.AdminRemoteDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.lecturer.source.LecturerLocalDS;
import com.example.timetablerapp.data.user.lecturer.source.LecturerRemoteDS;
import com.example.timetablerapp.data.user.student.StudentRepository;
import com.example.timetablerapp.data.user.student.source.StudentLocalDS;
import com.example.timetablerapp.data.user.student.source.StudentRemoteDS;

/**
 * 06/05/19 -bernard
 */
public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getSimpleName();
    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 105;
    public static final int INTERNET_ACCESS_REQUEST_CODE = 106;

    public static final String CHANNEL_ID = "1";
    private static SharedPreferences sharedPreferences;

    private static SQLiteDatabase databaseWritable;
    private static SQLiteDatabase databaseReadable;

    private static StudentRepository studentRepo;
    private static LecturerRepo lecturerRepo;
    private static DepartmentRepository departmentRepo;
    private static ProgrammesRepository programmesRepo;
    private static FacultiesRepository facultyRepo;
    private static CampusesRepository campusRepo;
    private static UnitsRepo unitRepo;
    private static AdminRepo adminRepo;
    private static HallRepo hallRepo;
    private static RoomRepo roomRepo;

    public static ProgrammesRepository getProgRepo() {
        return programmesRepo;
    }

    public static FacultiesRepository getFacultyRepo() {
        return facultyRepo;
    }

    public static CampusesRepository getCampusRepo() {
        return campusRepo;
    }

    public static LecturerRepo getLecturerRepo() {
        return lecturerRepo;
    }

    public static UnitsRepo getUnitRepo() {
        return unitRepo;
    }

    public static AdminRepo getAdminRepo() {
        return adminRepo;
    }

    public static HallRepo getHallRepo() {
        return hallRepo;
    }

    public static RoomRepo getRoomRepo() {
        return roomRepo;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        databaseWritable = new TimetablerDatabaseHelper(this).getWritableDatabase();
        databaseReadable = new TimetablerDatabaseHelper(this).getReadableDatabase();

        studentRepo = StudentRepository.newInstance(new StudentRemoteDS(),  new StudentLocalDS());

        lecturerRepo = LecturerRepo.newInstance(new LecturerLocalDS(), new LecturerRemoteDS());

        departmentRepo = DepartmentRepository.newInstance(new DepartmentLocalDataSrc(), new DepartmentRemoteDataSrc());

        programmesRepo = ProgrammesRepository.newInstance(new ProgLocalDS(), new ProgRemoteDS());

        campusRepo = CampusesRepository.newInstance(new CampusLocalDS(), new CampusRemoteDS());

        facultyRepo = FacultiesRepository.newInstance(new FacultyLocalDS(), new FacultyRemoteDS());

        unitRepo = UnitsRepo.newInstance(new UnitsLocalDS(), new UnitsRemoteDS());

        adminRepo = AdminRepo.newInstance(new AdminLocalDS(), new AdminRemoteDS());

        hallRepo = HallRepo.newInstance(new HallLocalDS(), new HallRemoteDS());

        roomRepo = RoomRepo.newInstance(new RoomLocalDS(), new RoomRemoteDS());
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static DepartmentRepository getDepRepo() {
        return departmentRepo;
    }

    public static StudentRepository getStudentRepository() {
        return studentRepo;
    }

    public static SQLiteDatabase getWritableDatabase() {
        return databaseWritable;
    }

    public static SQLiteDatabase getReadableDatabase() {
        return databaseReadable;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onTerminate() {
        databaseReadable.close();
        databaseWritable.close();
        super.onTerminate();
    }
}
