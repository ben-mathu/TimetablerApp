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
    }

    const val SQL_CREATE_STUDENT_TABLE =
            "CREATE TABLE ${TimetablerContract.Student.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${TimetablerContract.Student.STUDENT_ID} VARCHAR(10)," +
                    "${TimetablerContract.Student.FIRST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Student.LAST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Student.MIDDLE_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Student.USERNAME} VARCHAR(15) UNIQUE," +
                    "${TimetablerContract.Student.PASSWORD} VARCHAR(32) UNIQUE," +
                    "${TimetablerContract.Student.YEAR_OF_STUDY} INTEGER," +
                    "${TimetablerContract.Student.PROGRAMME_ID} VARCHAR(10)," +
                    "${TimetablerContract.Student.DEPARTMENT_ID} VARCHAR(10)," +
                    "${TimetablerContract.Student.CAMPUS_ID} VARCHAR(10)," +
                    "${TimetablerContract.Student.FACULTY_ID} VARCHAR(10)," +
                    "${TimetablerContract.Student.ADMISSION_DATE} VARCHAR(32)," +
                    "${TimetablerContract.Student.IN_SESSION} BOOLEAN)"

    const val SQL_DELETE_STUDENT = "DROP TABLE IF EXISTS ${TimetablerContract.Student.TABLE_NAME}"

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
    }

    const val SQL_CREATE_LECTURER_TABLE =
            "CREATE TABLE ${TimetablerContract.Lecturer.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${TimetablerContract.Lecturer.LECTURER_ID} VARCHAR(10)," +
                    "${TimetablerContract.Lecturer.FIRST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Lecturer.LAST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Lecturer.MIDDLE_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Lecturer.USERNAME} VARCHAR(15) UNIQUE," +
                    "${TimetablerContract.Lecturer.PASSWORD} VARCHAR(32) UNIQUE," +
                    "${TimetablerContract.Lecturer.IN_SESSION} BOOLEAN," +
                    "${TimetablerContract.Lecturer.DEPARTMENT_ID} VARCHAR(10)," +
                    "${TimetablerContract.Lecturer.FACULTY_ID} VARCHAR(10))"

    const val SQL_DELETE_LECTURER = "DROP TABLE IF EXISTS ${TimetablerContract.Lecturer.TABLE_NAME}"

    // define admin fields
    object Admin : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_ADMIN
        const val LECTURER_ID = Constants.ADMIN_ID
        const val FIRST_NAME = Constants.F_NAME
        const val LAST_NAME = Constants.L_NAME
        const val MIDDLE_NAME = Constants.M_NAME
        const val USERNAME = Constants.USERNAME
        const val PASSWORD = Constants.PASSWORD
    }

    const val SQL_CREATE_ADMIN_TABLE =
            "CREATE TABLE ${TimetablerContract.Admin.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${TimetablerContract.Admin.LECTURER_ID} VARCHAR(10)," +
                    "${TimetablerContract.Admin.FIRST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Admin.LAST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Admin.MIDDLE_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Admin.USERNAME} VARCHAR(15) UNIQUE," +
                    "${TimetablerContract.Admin.PASSWORD} VARCHAR(32) UNIQUE)"

    const val SQL_DELETE_ADMIN = "DROP TABLE IF EXISTS ${TimetablerContract.Lecturer.TABLE_NAME}"

    // define campus details
    object Campus : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_CAMPUS
        const val CAMPUS_NAME = Constants.CAMPUS_NAME
        const val CAMPUS_ID = Constants.CAMPUS_ID
    }

    const val SQL_CREATE_CAMPUS_TABLE =
            "CREATE TABLE ${Campus.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTERGER PRIMARY KEY AUTOINCREMENT," +
                    "${Campus.CAMPUS_ID} TEXT," +
                    "${Campus.CAMPUS_NAME} TEXT"

    const val  SQL_DELETE_CAMPUS = "DROP TABLE IF EXISTS ${Campus.TABLE_NAME}"

    object Faculty : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_FACULTIES
        const val FACULTY_ID = Constants.FACULTY_ID
        const val FACULTY_NAME = Constants.FACULTY_NAME
        const val CAMPUS_ID = Constants.CAMPUS_ID
    }

    const val SQL_CREATE_FACULTY_TABLE =
            "CREATE TABLE ${Faculty.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTERGER PRIMARY KEY AUTOINCREMENT," +
                    "${Faculty.FACULTY_ID} TEXT," +
                    "${Faculty.FACULTY_NAME} TEXT," +
                    "${Faculty.CAMPUS_ID} TEXT"

    const val  SQL_DELETE_FACULTY = "DROP TABLE IF EXISTS ${Faculty.TABLE_NAME}"

    object Department : BaseColumns {
        const val TABLE_NAME = Constants.TABLE_DEPARTMENTS
        const val DEPARTMENT_ID = Constants.DEPARTMENT_ID
        const val DEPARTMENT_NAME = Constants.DEPARTMENT_NAME
        const val FACULTY_ID = Constants.FACULTY_ID

    }

    const val SQL_CREATE_DEPARTMENT_TABLE =
            "CREATE TABLE ${Department.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTERGER PRIMARY KEY AUTOINCREMENT," +
                    "${Department.DEPARTMENT_ID} TEXT," +
                    "${Department.DEPARTMENT_NAME} TEXT," +
                    "${Department.FACULTY_ID} TEXT"

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
                    "${BaseColumns._ID} INTERGER PRIMARY KEY AUTOINCREMENT," +
                    "${Programme.PROGRAMME_ID} TEXT," +
                    "${Programme.PROGRAMME_NAME} TEXT," +
                    "${Programme.DEPARTMENT_ID} TEXT," +
                    "${Programme.FACULTY_ID} TEXT"

    const val  SQL_DELETE_PROGRAMMES = "DROP TABLE IF EXISTS ${Programme.TABLE_NAME}"

}