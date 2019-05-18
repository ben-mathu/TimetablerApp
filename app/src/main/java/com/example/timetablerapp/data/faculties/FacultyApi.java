package com.example.timetablerapp.data.faculties;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.faculties.model.FacultyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 08/05/19 -bernard
 */
public interface FacultyApi {
    @GET("faculties")
    Call<List<Faculty>> getAll(@Query(Constants.CAMPUS_NAME) String campus);

    @GET("faculties")
    Call<FacultyResponse> getAll();
}
