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
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TimetablerContract.Student.STUDENT_ID} VARCHAR(10)," +
                    "${TimetablerContract.Student.FIRST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Student.LAST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Student.MIDDLE_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Student.USERNAME} VARCHAR(15)," +
                    "${TimetablerContract.Student.PASSWORD} VARCHAR(32)," +
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
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TimetablerContract.Lecturer.LECTURER_ID} VARCHAR(10)," +
                    "${TimetablerContract.Lecturer.FIRST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Lecturer.LAST_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Lecturer.MIDDLE_NAME} VARCHAR(15)," +
                    "${TimetablerContract.Lecturer.USERNAME} VARCHAR(15)," +
                    "${TimetablerContract.Lecturer.PASSWORD} VARCHAR(32)," +
                    "${TimetablerContract.Lecturer.IN_SESSION} BOOLEAN," +
                    "${TimetablerContract.Lecturer.DEPARTMENT_ID} VARCHAR(10)," +
                    "${TimetablerContract.Lecturer.FACULTY_ID} VARCHAR(10))"

    const val SQL_DELETE_LECTURER = "DROP TABLE IF EXISTS ${TimetablerContract.Lecturer.TABLE_NAME}"
}