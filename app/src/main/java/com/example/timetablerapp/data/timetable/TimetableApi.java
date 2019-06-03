package com.example.timetablerapp.data.timetable;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.timetable.model.TimetableResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 23/05/19 -bernard
 */
public interface TimetableApi {
    @GET("lecturers/timetables")
    Call<TimetableResponse> getTimetable(@Query(Constants.LECTURER_ID) String lecturerId);

    @GET("students/timetables")
    Call<TimetableResponse> getTimetableByStudentId(@Query(Constants.STUDENT_ID) String studentId);
}
