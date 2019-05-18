package com.example.timetablerapp.data.department.source;

import com.example.timetablerapp.data.department.DepartmentApi;
import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.department.model.DepartmentResponse;
import com.example.timetablerapp.data.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class DepartmentRemoteDataSrc implements DepartmentDS {

    @Override
    public void getAllFromRemote(LoadDepartmentsCallBack callBack, String id) {
        Call<DepartmentResponse> call = RetrofitClient.getRetrofit()
                .create(DepartmentApi.class)
                .getAll(id);

        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                if (response.isSuccessful()) {
                    callBack.loadDepartmentsSuccessful(response.body().getList());
                } else {
                    callBack.dataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<DepartmentResponse> call, Throwable t) {
                callBack.dataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void update(Department item) {

    }

    @Override
    public void delete(Department item) {

    }

    @Override
    public void save(Department item) {

    }
}
