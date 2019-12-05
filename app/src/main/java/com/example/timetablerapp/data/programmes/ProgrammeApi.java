package com.example.timetablerapp.data.programmes;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.programmes.model.ProgrammeRequest;
import com.example.timetablerapp.data.programmes.model.ProgrammesResponse;
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
public interface ProgrammeApi {
    @GET("programmes")
    Call<ProgrammesResponse> getAll(@Query(Constants.DEPARTMENT_ID) String departmentId);

    @GET("programmes")
    Call<ProgrammesResponse> getAllProgrammes();

    @PUT("add-programme")
    Call<MessageReport> addProgramme(@Header("Content-Type") String contentType,
                                     @Body ProgrammeRequest req);

    @PUT("delete-programme")
    Call<MessageReport> delete(@Header(Constants.CONTENT_TYPE) String contentType,
                               @Body ProgrammeRequest request);

    @PUT("update-programme")
    Call<MessageReport> update(@Header(Constants.CONTENT_TYPE) String contentType,
                               @Body ProgrammeRequest request);
}
