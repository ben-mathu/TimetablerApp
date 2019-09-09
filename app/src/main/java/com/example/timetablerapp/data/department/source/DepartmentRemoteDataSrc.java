package com.example.timetablerapp.data.department.source;

import com.example.timetablerapp.data.department.DepartmentApi;
import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.department.model.DepartmentRequest;
import com.example.timetablerapp.data.department.model.DepartmentResponse;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class DepartmentRemoteDataSrc implements DepartmentDS {

    @Override
    public void getDepsByIdFromRemote(LoadDepartmentsCallBack callBack, String id) {
        Call<DepartmentResponse> call = RetrofitClient.getRetrofit()
                .create(DepartmentApi.class)
                .getAll(id);

        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
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
    public void getFromLocalDb(LoadDepartmentsCallBack callBack) {

    }

    @Override
    public void getAllFromRemote(LoadDepartmentsCallBack callback) {
        Call<DepartmentResponse> call = RetrofitClient.getRetrofit()
                .create(DepartmentApi.class)
                .getAll();

        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.loadDepartmentsSuccessful(response.body().getList());
                } else {
                    callback.dataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<DepartmentResponse> call, Throwable t) {
                callback.dataNotAvailable("Error: " + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void addDepartment(Department department, SuccessfulCallback callback) {
        DepartmentRequest req = new DepartmentRequest(department);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(DepartmentApi.class)
                .addDepartment("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body().getMessage());
                } else {
                    callback.unSuccessful(response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callback.unSuccessful("Error, " + t.getLocalizedMessage());
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
