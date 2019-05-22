package com.example.timetablerapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

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
import com.example.timetablerapp.data.programmes.ProgrammesRepository;
import com.example.timetablerapp.data.programmes.source.ProgLocalDS;
import com.example.timetablerapp.data.programmes.source.ProgRemoteDS;
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
import com.example.timetablerapp.data.user.student.source.StudentDataSourceLocal;
import com.example.timetablerapp.data.user.student.source.StudentDataSourceRemote;

/**
 * 06/05/19 -bernard
 */
public class MainApplication extends Application {
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

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        databaseWritable = new TimetablerDatabaseHelper(this).getWritableDatabase();
        databaseReadable = new TimetablerDatabaseHelper(this).getReadableDatabase();

        studentRepo = StudentRepository.newInstance(new StudentDataSourceRemote(),  new StudentDataSourceLocal());

        lecturerRepo = LecturerRepo.newInstance(new LecturerLocalDS(databaseWritable), new LecturerRemoteDS());

        departmentRepo = DepartmentRepository.newInstance(new DepartmentLocalDataSrc(), new DepartmentRemoteDataSrc());

        programmesRepo = ProgrammesRepository.newInstance(new ProgLocalDS(), new ProgRemoteDS());

        campusRepo = CampusesRepository.newInstance(new CampusLocalDS(), new CampusRemoteDS());

        facultyRepo = FacultiesRepository.newInstance(new FacultyLocalDS(), new FacultyRemoteDS());

        unitRepo = UnitsRepo.newInstance(new UnitsLocalDS(), new UnitsRemoteDS());

        adminRepo = AdminRepo.newInstance(new AdminLocalDS(), new AdminRemoteDS());
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
}
