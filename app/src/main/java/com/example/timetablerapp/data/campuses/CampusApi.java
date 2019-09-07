package com.example.timetablerapp.data.campuses;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.campuses.model.CampusRequest;
import com.example.timetablerapp.data.campuses.model.CampusesReponse;
import com.example.timetablerapp.data.response.SuccessfulReport;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

/**
 * 08/05/19 -bernard
 */
public interface CampusApi {
    @GET("campuses")
    Call<CampusesReponse> getAll();

    @PUT("add-campus")
    Call<SuccessfulReport> addCampus(@Header("Content-Type") String contentType,
                                     @Body CampusRequest req);
}
