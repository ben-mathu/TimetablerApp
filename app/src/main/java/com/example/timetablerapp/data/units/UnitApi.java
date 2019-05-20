package com.example.timetablerapp.data.units;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.units.model.UnitRequest;
import com.example.timetablerapp.data.units.model.UnitResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 19/05/19 -bernard
 */
public interface UnitApi {
    @GET("lecturers/units")
    Call<UnitResponse> getUnits(@Query(Constants.LECTURER_ID) String id);

    @GET("students/units")
    Call<UnitResponse> getUnitsByStudentId(@Query(Constants.STUDENT_ID) String strId);
}
