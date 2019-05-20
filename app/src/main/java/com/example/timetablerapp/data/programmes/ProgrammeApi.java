package com.example.timetablerapp.data.programmes;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.programmes.model.ProgrammesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 08/05/19 -bernard
 */
public interface ProgrammeApi {
    @GET("programmes")
    Call<ProgrammesResponse> getAll(@Query(Constants.DEPARTMENT_ID) String departmentId);
}
