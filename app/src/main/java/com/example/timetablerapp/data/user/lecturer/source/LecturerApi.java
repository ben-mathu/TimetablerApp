package com.example.timetablerapp.data.user.lecturer.source;

import com.example.timetablerapp.data.user.lecturer.model.LecturerRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 08/05/19 -bernard
 */
public interface LecturerApi {

    @POST("lecturer-sign-up")
    Call<String> signUpLec(@Header("Content-Type") String contentType,
                           @Body LecturerRequest lecturerRequest);

}
