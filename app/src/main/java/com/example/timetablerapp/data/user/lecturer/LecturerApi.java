package com.example.timetablerapp.data.user.lecturer;

import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.user.lecturer.model.LecturerRequest;
import com.example.timetablerapp.data.user.ValidationRequest;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponse;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponseList;
import com.example.timetablerapp.data.user.lecturer.source.LecturerRemoteDS;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 08/05/19 -bernard
 */
public interface LecturerApi {

    @POST("lecturer-sign-up")
    Call<MessageReport> signUpLec(@Header("Content-Type") String contentType,
                                  @Body LecturerRequest lecturerRequest);

    @POST("validate-user")
    Call<LecturerResponse> validateLec(@Header("Content-Type") String contentType,
                                       @Body ValidationRequest request);

    @GET("lecturers")
    Call<LecturerResponseList> getLecturers();

    @POST("create-user")
    Call<LecturerRemoteDS.PackageResponse> createLecturer(@Header("Content-Type") String contentType,
                                                         @Body LecturerRemoteDS.PackageRequest req);
}
