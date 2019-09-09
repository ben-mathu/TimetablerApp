package com.example.timetablerapp.data.department;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.department.model.DepartmentRequest;
import com.example.timetablerapp.data.department.model.DepartmentResponse;
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
public interface DepartmentApi {
    @GET("departments")
    Call<DepartmentResponse> getAll(@Query(Constants.FACULTY_ID) String id);

    @PUT("add-department")
    Call<MessageReport> addDepartment(@Header("Content-Type") String contentType,
                                      @Body DepartmentRequest req);

    @GET("departments")
    Call<DepartmentResponse> getAll();
}
