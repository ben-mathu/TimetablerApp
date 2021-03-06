package com.example.timetablerapp.data.units.source.remote;

import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.timetable.TimetableApi;
import com.example.timetablerapp.data.timetable.model.TimetableResponse;
import com.example.timetablerapp.data.units.UnitApi;
import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.data.units.model.UnitRequest;
import com.example.timetablerapp.data.units.model.UnitResponse;
import com.example.timetablerapp.data.units.model.UnitsRequest;
import com.example.timetablerapp.data.utils.RetrofitClient;
import com.example.timetablerapp.util.SuccessfulCallback;
import com.google.gson.annotations.SerializedName;

import java.net.ConnectException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 19/05/19 -bernard
 */
public class UnitsRemoteDS implements UnitDataSource {
    private static final String TAG = UnitsRemoteDS.class.getSimpleName();
    @Override
    public void update(Unit item, SuccessfulCallback callback) {

    }

    @Override
    public void delete(Unit item, SuccessfulCallback callback) {

    }

    @Override
    public void save(Unit item, SuccessfulCallback callback) {

    }

    @Override
    public void getUnitsByLecturerId(String id, UnitsLoadedCallback callback) {
        UnitRequest request = new UnitRequest();
        request.setLecturerId(id);
        Call<UnitResponse> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .getUnits(id);

        call.enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                if (response.isSuccessful()) {
                    List<Unit> unitList = response.body().getUnitList();
                    if (unitList != null) {
                        callback.successful(unitList);
                    }
                } else {
                    callback.unsuccessful("Units are not available, please contact admin");
                }
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.unsuccessful("An error occurred, please contact admin.");
            }
        });
    }

    @Override
    public void getUnitsByStudentId(String strId, UnitsLoadedCallback callback) {
        Call<UnitResponse> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .getUnitsByStudentId(strId);

        call.enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                if (response.isSuccessful()) {
                    List<Unit> unitList = response.body().getUnitList();
                    if (unitList != null) {
                        callback.successful(unitList);
                    }
                } else {
                    callback.unsuccessful("Units are not available, please contact admin");
                }
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.unsuccessful("An error occurred, please contact admin.");
            }
        });
    }

    @Override
    public void getUnits(UnitsLoadedCallback callback) {
        Call<UnitResponse> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .getUnitsGeneral();

        call.enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                if (response.isSuccessful()) {
                    List<Unit> unitList = response.body().getUnitList();
                    if (unitList != null) {
                        callback.successful(unitList);
                    }
                } else {
                    callback.unsuccessful("Units are not available, please contact admin");
                }
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.unsuccessful("An error occurred, please contact admin.");
            }
        });
    }

    public void getCourses(UnitsLoadedCallback callback) {
        Call<UnitResponse> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .getUnits();

        call.enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                if (response.isSuccessful()) {
                    List<Unit> unitList = response.body().getUnitList();
                    if (unitList != null) {
                        callback.successful(unitList);
                    }
                } else {
                    callback.unsuccessful("Units are not available, please contact admin");
                }
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.unsuccessful("An error occurred, please contact admin.");
            }
        });
    }

    @Override
    public void getTimetableByStudentId(String studentId, TimetableLoadedCallback callback) {
        Call<TimetableResponse> call = RetrofitClient.getRetrofit()
                .create(TimetableApi.class)
                .getTimetableByStudentId(studentId);

        call.enqueue(new Callback<TimetableResponse>() {
            @Override
            public void onResponse(Call<TimetableResponse> call, Response<TimetableResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getTimetableList());
                } else {
                    callback.unsuccessful("Data not available, please contact the administrator.");
                }
            }

            @Override
            public void onFailure(Call<TimetableResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof ConnectException) {
                    callback.unsuccessful("An error occurred, please contact the administrator.");
                } else if (t instanceof HttpException) {
                    callback.unsuccessful("Check your connection and try again.");
                } else {
                    callback.unsuccessful("An error occurred, please contact the administrator.");
                }
            }
        });
    }

    @Override
    public void getTimetableByLecturerId(String lecturerId, TimetableLoadedCallback callback) {
        Call<TimetableResponse> call = RetrofitClient.getRetrofit()
                .create(TimetableApi.class)
                .getTimetable(lecturerId);

        call.enqueue(new Callback<TimetableResponse>() {
            @Override
            public void onResponse(Call<TimetableResponse> call, Response<TimetableResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getTimetableList());
                } else {
                    callback.unsuccessful("Data not available, please contact administrator");
                }
            }

            @Override
            public void onFailure(Call<TimetableResponse> call, Throwable t) {
                Log.e(TAG, "Error", t);
                callback.unsuccessful("An error has occurred, please try again");
            }
        });
    }

    @Override
    public void getTimetable(TimetableLoadedCallback callback) {
        Call<TimetableResponse> call = RetrofitClient.getRetrofit()
                .create(TimetableApi.class)
                .getTimeTable();

        call.enqueue(new Callback<TimetableResponse>() {
            @Override
            public void onResponse(Call<TimetableResponse> call, Response<TimetableResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getTimetableList());
                } else {
                    callback.unsuccessful("Data not available.");
                }
            }

            @Override
            public void onFailure(Call<TimetableResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.unsuccessful("An error occurred, check logs" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getAllUnitsByDepartmentId(String departmentId, UnitsLoadedCallback callback) {
        Call<UnitResponse> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .getUnitsOnOffer(departmentId);

        call.enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                if (response.isSuccessful()) {
                    callback.successful(response.body().getUnitList());
                } else {
                    callback.unsuccessful(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Error " + t.getLocalizedMessage(), t);
                callback.unsuccessful("An error occurred, please try again.");
            }
        });
    }

    @Override
    public void submitRegisteredUnits(String userId, List<Unit> unitList, UnitsRegisteredCallback callback) {
        String role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");
        UnitsRequest request = new UnitsRequest();
        request.setUnitList(unitList);
        Call<MessageReport> call;
        if (role.equalsIgnoreCase("lecturer")) {
            call = RetrofitClient.getRetrofit()
                    .create(UnitApi.class)
                    .submitRegisteredUnitsLec("application/json", userId, request);

            call.enqueue(new Callback<MessageReport>() {
                @Override
                public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                    if (response.isSuccessful()) {
                        callback.successful(response.body().getMessage());
                    } else {
                        callback.unsuccessful("An error occurred, please try again.");
                    }
                }

                @Override
                public void onFailure(Call<MessageReport> call, Throwable t) {
                    Log.e(TAG, "onFailure: Error " + t.getLocalizedMessage() , t);
                    callback.unsuccessful("An error occurred, please contact administrator.");
                }
            });
        } else if (role.equalsIgnoreCase("student")) {
            call = RetrofitClient.getRetrofit()
                    .create(UnitApi.class)
                    .submitRegisteredUnits("application/json", userId, request);

            call.enqueue(new Callback<MessageReport>() {
                @Override
                public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                    if (response.isSuccessful()) {
                        callback.successful(response.body().getMessage());
                    } else {
                        callback.unsuccessful("An error occurred, please try again.");
                    }
                }

                @Override
                public void onFailure(Call<MessageReport> call, Throwable t) {
                    Log.e(TAG, "onFailure: Error " + t.getLocalizedMessage() , t);
                    callback.unsuccessful("An error occurred, please contact administrator.");
                }
            });
        }
    }

    @Override
    public void setRegistrationDeadline(String startDate, String deadline, UnitsRegisteredCallback callback) {
        DeadlineRequest request = new DeadlineRequest();
        request.setStartDate(startDate);
        request.setDeadline(deadline);

        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .setRegistrationDeadline("application/json", request);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful()) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage(), t);
                t.printStackTrace();

                callback.unsuccessful(t.getMessage());
            }
        });
    }

    @Override
    public void removeUnits(String userId, List<Unit> unitList, UnitsRegisteredCallback callback) {
        String role = MainApplication.getSharedPreferences()
                .getString(Constants.ROLE, "");

        UnitRequest req = new UnitRequest();
        req.setUnits(unitList);

        if (role.equalsIgnoreCase("student")) {
            Call<MessageReport> call = RetrofitClient.getRetrofit()
                    .create(UnitApi.class)
                    .removeUnits("application/json", req, userId);

            call.enqueue(new Callback<MessageReport>() {
                @Override
                public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            callback.successful(response.body().getMessage());
                        }
                    } else {
                        callback.unsuccessful("An error occurred units were not removed, please contact administrator");
                    }
                }

                @Override
                public void onFailure(Call<MessageReport> call, Throwable t) {
                    callback.unsuccessful("An error occurred units were not removed, please contact administrator");
                }
            });
        }

        if (role.equalsIgnoreCase("lecturer")) {
            Call<MessageReport> call = RetrofitClient.getRetrofit()
                    .create(UnitApi.class)
                    .removeUnitsLec("application/json", req, userId);

            call.enqueue(new Callback<MessageReport>() {
                @Override
                public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            callback.successful(response.body().getMessage());
                        }
                    } else {
                        callback.unsuccessful("An error occurred units were not removed, please contact administrator");
                    }
                }

                @Override
                public void onFailure(Call<MessageReport> call, Throwable t) {
                    callback.unsuccessful("An error occurred units were not removed, please contact administrator");
                }
            });
        }

        if (role.equalsIgnoreCase("admin")) {
            Call<MessageReport> call = RetrofitClient.getRetrofit()
                    .create(UnitApi.class)
                    .removeUnitsAdmin("application/json", req);

            call.enqueue(new Callback<MessageReport>() {
                @Override
                public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            callback.successful(response.body().getMessage());
                        }
                    } else {
                        callback.unsuccessful("An error occurred units were not removed, please contact administrator");
                    }
                }

                @Override
                public void onFailure(Call<MessageReport> call, Throwable t) {
                    callback.unsuccessful("An error occurred units were not removed, please contact administrator");
                }
            });
        }
    }

    @Override
    public void deleteCourse(Unit item, UnitsRegisteredCallback callback) {
        UnitReq req = new UnitReq(item, "");
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
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
    public void updateCourse(Unit unit, UnitsRegisteredCallback callback) {
        UnitReq req = new UnitReq(unit, "");
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .updateCourse("application/json", req);

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

    public void addCourse(Unit unit, String passCode, UnitsRegisteredCallback callback) {
        UnitReq unitReq = new UnitReq(unit, passCode);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .addCourse("application/json", unitReq);

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
                callback.unsuccessful("An error occurred, please contact administrator");
            }
        });
    }

    public class DeadlineRequest {
        @SerializedName("start_date")
        private String startDate;
        @SerializedName("deadline")
        private String deadline;

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
    }

    public class UnitReq {
        @SerializedName("unit")
        private Unit unit;
        @SerializedName("passcode")
        private String passCode;

        public UnitReq(Unit unit, String passCode) {
            this.unit = unit;
            this.passCode = passCode;
        }
        public void setUnit(Unit unit) {
            this.unit = unit;
        }

        public Unit getUnit() {
            return unit;
        }
    }
}
