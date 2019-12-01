package com.example.timetablerapp.data.db

import android.provider.BaseColumns
import com.example.timetablerapp.data.Constants

/**
 * 17/05/19 -bernard
 */

object TimetablerContract {

    // define student fields
    object Student : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_STUDENTS
        const val STUDENT_ID = Constants.STUDENT_ID
        const val FIRST_NAME = Constants.F_NAME
        const val LAST_NAME = Constants.L_NAME
        const val MIDDLE_NAME = Constants.M_NAME
        const val USERNAME = Constants.USERNAME
        const val PASSWORD = Constants.PASSWORD
        const val YEAR_OF_STUDY = Constants.YEAR_OF_STUDY
        const val PROGRAMME_ID = Constants.PROGRAMME_ID
        const val DEPARTMENT_ID = Constants.DEPARTMENT_ID
        const val CAMPUS_ID = Constants.CAMPUS_ID
        const val FACULTY_ID = Constants.FACULTY_ID
        const val ADMISSION_DATE = Constants.ADMISSION_DATE
        const val IN_SESSION = Constants.IN_SESSION
        const val EMAIL = Constants.EMAIL
    }

    const val SQL_CREATE_STUDENT_TABLE =
            "CREATE TABLE ${Student.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${Student.STUDENT_ID} VARCHAR(10)," +
                    "${Student.FIRST_NAME} VARCHAR(15)," +
                    "${Student.LAST_NAME} VARCHAR(15)," +
                    "${Student.MIDDLE_NAME} VARCHAR(15)," +
                    "${Student.USERNAME} VARCHAR(15) UNIQUE," +
                    "${Student.PASSWORD} VARCHAR(32) UNIQUE," +
                    "${Student.YEAR_OF_STUDY} INTEGER," +
                    "${Student.PROGRAMME_ID} VARCHAR(10)," +
                    "${Student.DEPARTMENT_ID} VARCHAR(10)," +
                    "${Student.CAMPUS_ID} VARCHAR(10)," +
                    "${Student.FACULTY_ID} VARCHAR(10)," +
                    "${Student.ADMISSION_DATE} VARCHAR(32)," +
                    "${Student.IN_SESSION} BOOLEAN," +
                    "${Student.EMAIL} VARCHAR(32))"

    const val SQL_DELETE_STUDENT = "DROP TABLE IF EXISTS ${Student.TABLE_NAME}"

    // define lecturers fields
    object Lecturer : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_LECTURERS
        const val LECTURER_ID = Constants.LECTURER_ID
        const val FIRST_NAME = Constants.F_NAME
        const val LAST_NAME = Constants.L_NAME
        const val MIDDLE_NAME = Constants.M_NAME
        const val USERNAME = Constants.USERNAME
        const val PASSWORD = Constants.PASSWORD
        const val IN_SESSION = Constants.IN_SESSION
        const val DEPARTMENT_ID = Constants.DEPARTMENT_ID
        const val FACULTY_ID = Constants.FACULTY_ID
        const val CAMPUS_ID = Constants.CAMPUS_ID
        const val EMAIL = Constants.EMAIL
    }

    const val SQL_CREATE_LECTURER_TABLE =
            "CREATE TABLE ${Lecturer.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${Lecturer.LECTURER_ID} VARCHAR(10)," +
                    "${Lecturer.FIRST_NAME} VARCHAR(15)," +
                    "${Lecturer.LAST_NAME} VARCHAR(15)," +
                    "${Lecturer.MIDDLE_NAME} VARCHAR(15)," +
                    "${Lecturer.USERNAME} VARCHAR(15) UNIQUE," +
                    "${Lecturer.PASSWORD} VARCHAR(32) UNIQUE," +
                    "${Lecturer.IN_SESSION} BOOLEAN," +
                    "${Lecturer.DEPARTMENT_ID} VARCHAR(10)," +
                    "${Lecturer.FACULTY_ID} VARCHAR(10)," +
                    "${Lecturer.CAMPUS_ID} VARCHAR(10)," +
                    "${Lecturer.EMAIL} VARCHAR(32))"

    const val SQL_DELETE_LECTURER = "DROP TABLE IF EXISTS ${Lecturer.TABLE_NAME}"

    // define admin fields
    object Admin : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_ADMIN
        const val ADMIN_ID = Constants.ADMIN_ID
        const val FIRST_NAME = Constants.F_NAME
        const val LAST_NAME = Constants.L_NAME
        const val MIDDLE_NAME = Constants.M_NAME
        const val USERNAME = Constants.USERNAME
        const val PASSWORD = Constants.PASSWORD
        const val EMAIL = Constants.EMAIL
    }

    const val SQL_CREATE_ADMIN_TABLE =
            "CREATE TABLE ${Admin.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${Admin.ADMIN_ID} VARCHAR(10)," +
                    "${Admin.FIRST_NAME} VARCHAR(15)," +
                    "${Admin.LAST_NAME} VARCHAR(15)," +
                    "${Admin.MIDDLE_NAME} VARCHAR(15)," +
                    "${Admin.USERNAME} VARCHAR(15) UNIQUE," +
                    "${Admin.PASSWORD} VARCHAR(32)," +
                    "${Admin.EMAIL} VARCHAR(32) UNIQUE)"

    const val SQL_DELETE_ADMIN = "DROP TABLE IF EXISTS ${Admin.TABLE_NAME}"

    // define campus details
    object Campus : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_CAMPUS
        const val CAMPUS_NAME = Constants.CAMPUS_NAME
        const val CAMPUS_ID = Constants.CAMPUS_ID
    }

    const val SQL_CREATE_CAMPUS_TABLE =
            "CREATE TABLE ${Campus.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${Campus.CAMPUS_ID} VARCHAR(25)," +
                    "${Campus.CAMPUS_NAME} VARCHAR(255))"

    const val  SQL_DELETE_CAMPUS = "DROP TABLE IF EXISTS ${Campus.TABLE_NAME}"

    object Faculty : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_FACULTIES
        const val FACULTY_ID = Constants.FACULTY_ID
        const val FACULTY_NAME = Constants.FACULTY_NAME
        const val CAMPUS_ID = Constants.CAMPUS_ID
    }

    const val SQL_CREATE_FACULTY_TABLE =
            "CREATE TABLE ${Faculty.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${Faculty.FACULTY_ID} VARCHAR(25)," +
                    "${Faculty.FACULTY_NAME} VARCHAR(255)," +
                    "${Faculty.CAMPUS_ID} VARCHAR(25))"

    const val  SQL_DELETE_FACULTY = "DROP TABLE IF EXISTS ${Faculty.TABLE_NAME}"

    object Department : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_DEPARTMENTS
        const val DEPARTMENT_ID = Constants.DEPARTMENT_ID
        const val DEPARTMENT_NAME = Constants.DEPARTMENT_NAME
        const val FACULTY_ID = Constants.FACULTY_ID

    }

    const val SQL_CREATE_DEPARTMENT_TABLE =
            "CREATE TABLE ${Department.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${Department.DEPARTMENT_ID} VARCHAR(25)," +
                    "${Department.DEPARTMENT_NAME} VARCHAR(25)," +
                    "${Department.FACULTY_ID} VARCHAR(25))"

    const val  SQL_DELETE_DEPARTMENT = "DROP TABLE IF EXISTS ${Department.TABLE_NAME}"

    object Programme : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_PROGRAMMES
        const val PROGRAMME_ID = Constants.PROGRAMME_ID
        const val PROGRAMME_NAME = Constants.PROGRAMME_NAME
        const val DEPARTMENT_ID = Constants.DEPARTMENT_ID
        const val FACULTY_ID = Constants.FACULTY_ID
    }

    const val SQL_CREATE_PROGRAMME_TABLE =
            "CREATE TABLE ${Programme.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${Programme.PROGRAMME_ID} VARCHAR(25)," +
                    "${Programme.PROGRAMME_NAME} VARCHAR(255)," +
                    "${Programme.DEPARTMENT_ID} VARCHAR(25)," +
                    "${Programme.FACULTY_ID} VARCHAR(25))"

    const val  SQL_DELETE_PROGRAMMES = "DROP TABLE IF EXISTS ${Programme.TABLE_NAME}"

    object Hall : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_HALLS
        const val HALL_ID = Constants.HALL_ID
        const val HALL_NAME = Constants.HALL_NAME
        const val FACULTY_ID = Constants.FACULTY_ID
    }

    const val SQL_CREATE_HALL_TABLE =
            "CREATE TABLE ${Hall.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${Hall.HALL_ID} VARCHAR(25)," +
                    "${Hall.HALL_NAME} VARCHAR(255)," +
                    "${Hall.FACULTY_ID} VARCHAR(25))"

    const val  SQL_DELETE_HALL = "DROP TABLE IF EXISTS ${Hall.TABLE_NAME}"

    object Room : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_ROOM
        const val ROOM_ID = Constants.ROOM_ID
        const val HALL_ID = Constants.HALL_ID
        const val FACULTY_ID = Constants.FACULTY_ID
        const val VOLUME = Constants.VOLUME
        const val AVAILABILITY = Constants.AVAILABILITY
        const val IS_LAB = Constants.IS_LAB
    }

    const val SQL_CREATE_ROOM_TABLE =
            "CREATE TABLE ${Room.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${Room.ROOM_ID} VARCHAR(25)," +
                    "${Room.HALL_ID} VARCHAR(255)," +
                    "${Room.FACULTY_ID} VARCHAR(25)," +
                    "${Room.VOLUME} VARCHAR(25)," +
                    "${Room.AVAILABILITY} BOOLEAN," +
                    "${Room.IS_LAB} BOOLEAN)"

    const val  SQL_DELETE_ROOM = "DROP TABLE IF EXISTS ${Room.TABLE_NAME}"
}