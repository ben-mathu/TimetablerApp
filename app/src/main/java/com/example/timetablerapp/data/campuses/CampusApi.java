package com.example.timetablerapp.data.campuses;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.campuses.model.CampusesReponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 08/05/19 -bernard
 */
public interface CampusApi {
    @GET("campuses")
    Call<CampusesReponse> getAll();
}
