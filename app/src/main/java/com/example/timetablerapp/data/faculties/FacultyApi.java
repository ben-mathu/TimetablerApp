package com.example.timetablerapp.data.faculties;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.faculties.model.FacultiesResponse;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.faculties.model.FacultyRequest;
import com.example.timetablerapp.data.faculties.model.FacultyResponse;
import com.example.timetablerapp.data.response.MessageReport;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * 08/05/19 -bernard
 */
public interface FacultyApi {
    @GET("faculties")
    Call<FacultiesResponse> getAll(@Query(Constants.CAMPUS_ID) String campusId);

    @GET("faculties")
    Call<FacultyResponse> getAll();

    @PUT("add-faculty")
    Call<MessageReport> addFaculty(@Header("Content-Type") String contentType,
                                   @Body FacultyRequest req);
}
