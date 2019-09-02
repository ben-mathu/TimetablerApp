package com.example.timetablerapp.data;

import com.example.timetablerapp.data.user.admin.model.Admin;

import org.jetbrains.annotations.NotNull;

/**
 * 07/05/19 -bernard
 */
public class Constants {
    public static final String BASE_URL = "http://192.168.43.146:8080/Timetabler-v2.0/";
    public static final String DATABASE_NAME = "timetabler.db";
    public static final String SALT = "salt";
    public static final String ROLE = "role";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // Campus - done
    public static final String TABLE_CAMPUS = "campuses";
    public static final String CAMPUS_ID = "campus_id";
    public static final String CAMPUS_NAME = "campus_name";

    // Faculties - done
    public static final String TABLE_FACULTIES = "faculties";
    public static final String FACULTY_ID = "faculty_id";
    public static final String FACULTY_NAME = "faculty_name";

    // Halls
    public static final String TABLE_HALLS = "halls";
    public static final String HALL_ID = "hall_id";
    public static final String HALL_NAME = "hall_name";

    // Classes
    public static final String TABLE_CLASSES = "classes";
    public static final String CLASS_ID = "class_id";
    public static final String AVAILABILITY = "availability";
    public static final String VOLUME = "volume";

    // Departments - done
    public static final String TABLE_DEPARTMENTS = "departments";
    public static final String DEPARTMENT_ID = "department_id";
    public static final String DEPARTMENT_NAME = "department_name";

    // Lecturers
    public static final String TABLE_LECTURERS = "lecturers";
    public static final String LECTURER_ID = "lecturer_id";

    // Programmes - done
    public static final String TABLE_PROGRAMMES = "programmes";
    public static final String PROGRAMME_ID = "programme_id";
    public static final String PROGRAMME_NAME = "programme_name";

    // Students - done
    public static final String TABLE_STUDENTS = "students";
    public static final String STUDENT_ID = "student_id";
    public static final String F_NAME = "f_name";
    public static final String L_NAME = "l_name";
    public static final String M_NAME = "m_name";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String IN_SESSION = "in_session";
    public static final String YEAR_OF_STUDY = "year_of_study";

    // Units
    public static final String TABLE_UNITS = "units";
    public static final String UNIT_ID = "unit_id";
    public static final String UNIT_NAME = "unit_name";

    // Admin
    public static final String TABLE_ADMIN = "admins";
    public static final String ADMIN_ID = "admin_id";

    // Lecturers Programmes
    public static final String TABLE_LECTURER_PROGRAMMES = "lecturer_programmes";

    // Class units
    public static final String TABLE_CLASS_UNITS = "class_units";

    // Lecturer units
    public static final String TABLE_LECTURER_UNITS = "lecturer_units";

    // Student units
    public static final String TABLE_STUDENT_UNITS = "student_units";
    public static final String ADMISSION_DATE = "admission_date";
    public static final String IS_LAB = "is_lab";
    public static final String IS_PRACTICAL = "is_practical";

    // other parameters
    public static final String MESSAGE = "message";

    public static final String SALTROLE = "salt_role";

    public static final String TIMESLOT = "timeslot";
    public static final String UNIT = "unit";
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String ACTION_SNOOZE = "Reminder";
    public static final String NOTIFICATION_CONTENT = "notification_content";
    public static final String REMINDER = "reminder";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";

    public static final String SCHEDULE = "job scheduled";
    public static final String USER_ID = "user_id";
    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String IS_TIME_REACHED = "isTimeReached";
    public static final String IS_TIME_ADDED = "isTimeAdded";
    public static final String EMAIL = "email";
    public static final String ACCESS_CODE = "access_code";
}
