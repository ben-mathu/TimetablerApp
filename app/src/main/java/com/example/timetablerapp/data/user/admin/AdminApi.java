package com.example.timetablerapp.data.user.admin;

import com.example.timetablerapp.data.response.SuccessfulReport;
import com.example.timetablerapp.data.user.admin.model.AdminRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 22/05/19 -bernard
 */
public interface AdminApi {
    @POST("register_admin")
    Call<SuccessfulReport> register(@Header("Content-Type") String contentType, @Body AdminRequest request);
}
