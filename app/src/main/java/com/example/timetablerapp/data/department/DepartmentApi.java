package com.example.timetablerapp.data.department;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.department.model.DepartmentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 08/05/19 -bernard
 */
public interface DepartmentApi {
    @GET("departments")
    Call<DepartmentResponse> getAll(@Query(Constants.FACULTY_ID) String id);
}
