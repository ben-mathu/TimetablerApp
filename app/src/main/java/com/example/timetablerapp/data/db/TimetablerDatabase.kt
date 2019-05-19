package com.example.timetablerapp.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.timetablerapp.data.Constants
import java.security.AccessControlContext

/**
 * 07/05/19 -bernard
 */

class TimetablerDatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TimetablerContract.SQL_CREATE_STUDENT_TABLE)
        db?.execSQL(TimetablerContract.SQL_CREATE_LECTURER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(TimetablerContract.SQL_DELETE_STUDENT)
        db?.execSQL(TimetablerContract.SQL_DELETE_LECTURER)

        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_NAME = Constants.DATABASE_NAME
        const val DATABASE_VERSION = 2
    }
}
