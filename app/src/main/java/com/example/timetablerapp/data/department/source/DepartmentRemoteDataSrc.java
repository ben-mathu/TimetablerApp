package com.example.timetablerapp.data.department.source;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.department.DepartmentApi;
import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.department.model.DepartmentRequest;
import com.example.timetablerapp.data.department.model.DepartmentsResponse;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.utils.RetrofitClient;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class DepartmentRemoteDataSrc implements DepartmentDS {

    @Override
    public void getDepsByIdFromRemote(LoadDepartmentsCallBack callBack, String id) {
        Call<DepartmentsResponse> call = RetrofitClient.getRetrofit()
                .create(DepartmentApi.class)
                .getAll(id);

        call.enqueue(new Callback<DepartmentsResponse>() {
            @Override
            public void onResponse(Call<DepartmentsResponse> call, Response<DepartmentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.loadDepartmentsSuccessful(response.body().getList());
                } else {
                    callBack.dataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<DepartmentsResponse> call, Throwable t) {
                callBack.dataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getFromLocalDb(LoadDepartmentsCallBack callBack) {

    }

    @Override
    public void getAllFromRemote(LoadDepartmentsCallBack callback) {
        Call<DepartmentsResponse> call = RetrofitClient.getRetrofit()
                .create(DepartmentApi.class)
                .getAll();

        call.enqueue(new Callback<DepartmentsResponse>() {
            @Override
            public void onResponse(Call<DepartmentsResponse> call, Response<DepartmentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.loadDepartmentsSuccessful(response.body().getList());
                } else {
                    callback.dataNotAvailable("Data not available.");
                }
            }

            @Override
            public void onFailure(Call<DepartmentsResponse> call, Throwable t) {
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
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful(response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callback.unsuccessful("Error, " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getDepartmentById(String departmentId, LoadDepartmentCallback callback) {
        Call<DepartmentRequest> call = RetrofitClient.getRetrofit()
                .create(DepartmentApi.class)
                .getDepartmentById(departmentId);

        call.enqueue(new Callback<DepartmentRequest>() {
            @Override
            public void onResponse(Call<DepartmentRequest> call, Response<DepartmentRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.loadDepartment(response.body().getDepartment());
                } else {
                    callback.unsuccessful("Please try again or contact administrator.");
                }
            }

            @Override
            public void onFailure(Call<DepartmentRequest> call, Throwable t) {
                if (t instanceof HttpException || t instanceof NoRouteToHostException) {
                    callback.unsuccessful("Please contact administrator to assist you.");
                } else if (t instanceof ConnectException || t instanceof SocketTimeoutException) {
                    callback.unsuccessful("Check your connection and try again");
                }
            }
        });
    }

    @Override
    public void update(Department item, SuccessfulCallback callback) {
        DepartmentRequest req = new DepartmentRequest(item);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(DepartmentApi.class)
                .update(Constants.APPLICATION_JSON, req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please try again or contact the administrator.");
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callback.unsuccessful("Error: " + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void delete(Department item, SuccessfulCallback callback) {
        DepartmentRequest req = new DepartmentRequest(item);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(DepartmentApi.class)
                .deleteCourse("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please try again");
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callback.unsuccessful("Error: " + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void save(Department item, SuccessfulCallback callback) {

    }
}
