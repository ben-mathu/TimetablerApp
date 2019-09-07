package com.example.timetablerapp.data.user.admin;

import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.user.admin.model.AdminRequest;
import com.example.timetablerapp.data.user.ValidationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 22/05/19 -bernard
 */
public interface AdminApi {
    @POST("register_admin")
    Call<MessageReport> register(@Header("Content-Type") String contentType, @Body AdminRequest request);

    @POST("validate-user")
    Call<AdminRequest> validateLec(@Header("Content-Type") String s, @Body ValidationRequest request);
}
