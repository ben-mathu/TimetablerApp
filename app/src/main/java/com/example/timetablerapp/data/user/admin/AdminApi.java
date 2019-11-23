package com.example.timetablerapp.data.user.admin;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.user.RequestParams;
import com.example.timetablerapp.data.user.admin.model.AdminRequest;
import com.example.timetablerapp.data.user.ValidationRequest;
import com.example.timetablerapp.data.user.student.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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

    @POST("update-username")
    Call<MessageReport> updateUsername(@Header("Content-Type") String contentType,
                                       @Body RequestParams requestParams);

    @POST("admin-details")
    Call<AdminDAO> getDetails(@Header(Constants.CONTENT_TYPE) String contentType,
                              @Body RequestParams requestParams);

    @POST("change-password")
    Call<MessageReport> changePassword(@Header(Constants.CONTENT_TYPE) String contentType,
                                       @Body UserResponse req);

    @POST("update-user-details")
    Call<MessageReport> updateUserDetails(@Header(Constants.CONTENT_TYPE) String contentType,
                                          @Body AdminRequest req);
}
