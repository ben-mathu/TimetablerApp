package com.example.timetablerapp.data.user.student;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.user.RequestParams;
import com.example.timetablerapp.data.user.ValidationRequest;
import com.example.timetablerapp.data.user.student.model.StudentRequest;
import com.example.timetablerapp.data.user.student.model.StudentResponse;
import com.example.timetablerapp.data.user.student.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 21/05/19 -bernard
 */
public interface StudentApi {
    @POST("student-sign-up")
    Call<MessageReport> signUpStudent(@Header("Content-Type") String contentType,
                                      @Body StudentRequest request);

    @POST("validate-user")
    Call<StudentResponse> validateLec(@Header("Content-Type") String contentType,
                                      @Body ValidationRequest request);

    @POST("update-username")
    Call<MessageReport> updateUsername(@Header("Content-Type") String contentType,
                                       @Body RequestParams requestParams);

    @GET("student")
    Call<StudentResponse> getDetails(@Header("Content-Type") String contentType,
                                     @Body RequestParams req);

    @POST("change-password")
    Call<MessageReport> changePassword(@Header("Content-Type") String contentType,
                                       @Body UserResponse req);

    @POST("update-user-details")
    Call<MessageReport> updateUserDetails(@Header(Constants.CONTENT_TYPE) String contentType,
                                          @Body StudentRequest req);
}
