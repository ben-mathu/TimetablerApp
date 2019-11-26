package com.example.timetablerapp.data.department;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.department.model.DepartmentRequest;
import com.example.timetablerapp.data.department.model.DepartmentsResponse;
import com.example.timetablerapp.data.response.MessageReport;

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
    Call<DepartmentsResponse> getAll(@Query(Constants.FACULTY_ID) String id);

    @PUT("add-department")
    Call<MessageReport> addDepartment(@Header("Content-Type") String contentType,
                                      @Body DepartmentRequest req);

    @GET("departments")
    Call<DepartmentsResponse> getAll();

    @GET("department")
    Call<DepartmentRequest> getDepartmentById(@Query(Constants.DEPARTMENT_ID) String departmentId);

    @PUT("delete")
    Call<MessageReport> deleteCourse(@Header(Constants.APPLICATION_JSON) String contentType,
                                     @Body DepartmentRequest req);

    @PUT("update")
    Call<MessageReport> upDepartment(@Header(Constants.APPLICATION_JSON) String contentType, @Body DepartmentRequest req);
}
