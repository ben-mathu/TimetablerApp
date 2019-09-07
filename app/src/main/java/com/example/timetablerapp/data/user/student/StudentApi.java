package com.example.timetablerapp.data.user.student;

import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.user.ValidationRequest;
import com.example.timetablerapp.data.user.student.model.StudentRequest;
import com.example.timetablerapp.data.user.student.model.StudentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
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
}
