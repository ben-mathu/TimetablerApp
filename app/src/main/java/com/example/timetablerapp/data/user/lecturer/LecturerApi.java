package com.example.timetablerapp.data.user.lecturer;

import com.example.timetablerapp.data.response.SuccessfulReport;
import com.example.timetablerapp.data.user.lecturer.model.LecturerRequest;
import com.example.timetablerapp.data.user.ValidationRequest;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 08/05/19 -bernard
 */
public interface LecturerApi {

    @POST("lecturer-sign-up")
    Call<SuccessfulReport> signUpLec(@Header("Content-Type") String contentType,
                                     @Body LecturerRequest lecturerRequest);

    @POST("validate-user")
    Call<LecturerResponse> validateLec(@Header("Content-Type") String contentType,
                                       @Body ValidationRequest request);
}
