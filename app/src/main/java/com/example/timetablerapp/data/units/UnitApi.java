package com.example.timetablerapp.data.units;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.response.SuccessfulReport;
import com.example.timetablerapp.data.units.model.UnitRequest;
import com.example.timetablerapp.data.units.model.UnitResponse;
import com.example.timetablerapp.data.units.model.UnitsRequest;
import com.example.timetablerapp.data.units.source.remote.UnitsRemoteDS;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
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

    @GET("get-units")
    Call<UnitResponse> getUnitsByDepartmentId(@Query(Constants.DEPARTMENT_ID) String departmentId);

    @PUT("submit-units/{" + Constants.STUDENT_ID + "}")
    Call<SuccessfulReport> submitRegisteredUnits(@Header("Content-Type") String contentType,
                                                 @Query(Constants.STUDENT_ID) String userId,
                                                 @Body UnitsRequest request);

    @PUT("set-registration-date")
    Call<SuccessfulReport> setRegistrationDeadline(@Header("Content-Type") String contentType,
                                                   @Body UnitsRemoteDS.DeadlineRequest request);
}
