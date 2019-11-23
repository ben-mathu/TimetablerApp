package com.example.timetablerapp.data.user.lecturer.source;

import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.SuccessfulCallback;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.source.CampusLocalDS;
import com.example.timetablerapp.data.department.source.DepartmentLocalDataSrc;
import com.example.timetablerapp.data.faculties.source.FacultyLocalDS;
import com.example.timetablerapp.data.programmes.source.ProgLocalDS;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.user.RequestParams;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.AdminApi;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.admin.model.AdminRequest;
import com.example.timetablerapp.data.user.admin.source.AdminLocalDS;
import com.example.timetablerapp.data.user.lecturer.LecturerApi;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.model.LecRequest;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.model.LecturerRequest;
import com.example.timetablerapp.data.user.ValidationRequest;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponse;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponseList;
import com.example.timetablerapp.data.user.student.StudentApi;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentResponse;
import com.example.timetablerapp.data.user.student.model.UserResponse;
import com.example.timetablerapp.data.user.student.source.StudentLocalDS;
import com.example.timetablerapp.data.utils.RetrofitClient;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class LecturerRemoteDS implements UserDataSource<Lecturer>, LecturerDS {
    private static final String TAG = Lecturer.class.getSimpleName();

    private StudentLocalDS studentLocalDS;
    private AdminLocalDS adminLocalDS;
    private LecturerLocalDS lecturerLocalDS;
    private CampusLocalDS campusLocalDS;
    private DepartmentLocalDataSrc departmentLocalDS;
    private ProgLocalDS progLocalDS;
    private FacultyLocalDS facultyLocalDS;

    public LecturerRemoteDS() {
        studentLocalDS = new StudentLocalDS();
        adminLocalDS = new AdminLocalDS();
        lecturerLocalDS = new LecturerLocalDS();
        campusLocalDS = new CampusLocalDS();
        departmentLocalDS = new DepartmentLocalDataSrc();
        progLocalDS = new ProgLocalDS();
        facultyLocalDS = new FacultyLocalDS();
    }

    @Override
    public void userSignUp(UserDataSource.UserAuthCallback callBack, Lecturer lecturer, String pass) {
        LecturerRequest lecturerRequest = new LecturerRequest();
        lecturerRequest.setLecturer(lecturer);
        lecturerRequest.setPass(pass);

        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .signUpLec("application/json", lecturerRequest);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.userIsAuthSuccessful(response.body().getMessage());
                } else {
                    callBack.authNotSuccessful(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                callBack.authNotSuccessful(t.getMessage());
            }
        });
    }

    @Override
    public void authUser(UserDataSource.UserAuthCallback callBack, Lecturer lecturer) {

    }

    @Override
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        RequestParams requestParams = new RequestParams(name, userId, role);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .updateUsername("application/json", requestParams);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                    callback.unsuccessful("Please contact administrator for assistance.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof ConnectException) {
                    callback.unsuccessful("Check your internet connection and try again.");
                } else {
                    callback.unsuccessful("Please contact administrator, " + t.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback callback) {
        RequestParams req = new RequestParams("", userId, userRole);
        Call<LecturerResponse> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .getDetails("application/json", req);

        call.enqueue(new Callback<LecturerResponse>() {
            @Override
            public void onResponse(@NotNull Call<LecturerResponse> call, @NotNull Response<LecturerResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    callback.loadData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<LecturerResponse> call, @NotNull Throwable t) {
                if (t instanceof ConnectException)
                    callback.unsuccessful("Check your connection and try again.");
                else
                    callback.unsuccessful("Please contact the admin to resolve the issue.");
            }
        });
    }

    @Override
    public void changePassword(String userId, String role, SuccessCallback callback, String hashedNewPasswd) {
        UserResponse req = new UserResponse();
        req.setPassword(hashedNewPasswd);
        req.setUserId(userId);
        req.setRole(role);

        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .changePassword("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please contact the administrator");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: Error: " + t.getMessage(), t);

                if (t instanceof ConnectException) {
                    callback.unsuccessful("Check you internet connection settings, then try again.");
                } else {
                    callback.unsuccessful("Please contact the administrator to resolve the problem: " + t.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void updateUserDetails(Lecturer obj, SuccessfulCallback callback) {
        LecturerRequest req = new LecturerRequest();
        req.setLecturer(obj);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .updateUserDetails("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(Constants.MESSAGE_SUCCESS);
                } else {
                    callback.unsuccessful("Please try again, or contact the administrator.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                if (t instanceof ConnectException)
                    callback.unsuccessful(Constants.CHECK_CONNECTION);
                else
                    callback.unsuccessful(Constants.OTHER_ISSUE + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void validateUser(String role, String username, String password, String userId, UserAuthCallback callback) {
        ValidationRequest request = new ValidationRequest();
        request.setRole(role);
        request.setUsername(username);
        request.setPassword(password);
        request.setUserId(userId);

        if (role.equalsIgnoreCase("admin")) {
            Call<AdminRequest> call = RetrofitClient.getRetrofit()
                    .create(AdminApi.class)
                    .validateLec("application/json", request);

            call.enqueue(new Callback<AdminRequest>() {
                @Override
                public void onResponse(@NotNull Call<AdminRequest> call, @NotNull Response<AdminRequest> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Admin admin = response.body().getAdmin();

                        if (admin.getPassword() != null) {
                            if (admin.getPassword().equals(password)) {
                                MainApplication.getSharedPreferences().edit()
                                        .putString(Constants.USER_ID, admin.getAdminId())
                                        .apply();
                                adminLocalDS.save(admin);
                                callback.userIsAuthSuccessful("Authentication Successful");
                            } else {
                                callback.authNotSuccessful("Authentication not successful");
                            }
                        } else {
                            callback.authNotSuccessful("Authentication not successful, please try again");
                        }
                    } else {
                        callback.authNotSuccessful("Authentication not successful. Please try again.");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AdminRequest> call, @NotNull Throwable t) {
                    Log.e(TAG, "onFailure: Error" + t.getLocalizedMessage() + "\n", t);
                    callback.authNotSuccessful("Authentication not successful. Please contact the administrator.");
                }
            });
        } else if (role.equalsIgnoreCase("student")) {
            Call<StudentResponse> call = RetrofitClient.getRetrofit()
                    .create(StudentApi.class)
                    .validateLec("application/json", request);

            call.enqueue(new Callback<StudentResponse>() {
                @Override
                public void onResponse(@NotNull Call<StudentResponse> call, @NotNull Response<StudentResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Student student = response.body().getStudent();

                        if (student.getPassword() != null) {
                            if (student.getPassword().equals(password)) {
                                MainApplication.getSharedPreferences().edit()
                                        .putString(Constants.USER_ID, student.getStudentId())
                                        .apply();

                                studentLocalDS.save(student);
                                departmentLocalDS.save(response.body().getDepartment());
                                facultyLocalDS.save(response.body().getFaculty());
                                progLocalDS.save(response.body().getProgramme());
                                campusLocalDS.save(response.body().getCampus());

                                callback.userIsAuthSuccessful("Authentication Successful");
                            } else {
                                callback.authNotSuccessful("Authentication not successful");
                            }
                        } else {
                            callback.authNotSuccessful("Authentication not successful, please try again");
                        }
                    } else {
                        callback.authNotSuccessful("Authentication not successful. Please try again.");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<StudentResponse> call, @NotNull Throwable t) {
                    Log.e(TAG, "onFailure: Error" + t.getLocalizedMessage() + "\n", t);
                    callback.authNotSuccessful("Authentication not successful. Please contact the administrator.");
                }
            });
        } else if (role.equalsIgnoreCase("lecturer")) {
            Call<LecturerResponse> call = RetrofitClient.getRetrofit()
                    .create(LecturerApi.class)
                    .validateLec("application/json", request);

            call.enqueue(new Callback<LecturerResponse>() {
                @Override
                public void onResponse(@NotNull Call<LecturerResponse> call, @NotNull Response<LecturerResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Lecturer lecturer = response.body().getLecturer();

                        if (lecturer.getPassword() != null) {
                            if (lecturer.getPassword().equals(password)) {
                                MainApplication.getSharedPreferences().edit()
                                        .putString(Constants.USER_ID, lecturer.getId())
                                        .apply();
                                lecturerLocalDS.save(lecturer);

                                departmentLocalDS.save(response.body().getDepartment());
                                facultyLocalDS.save(response.body().getFaculty());
//                                progLocalDS.save(response.body().getProgramme());

                                callback.userIsAuthSuccessful("Authentication Successful");
                            } else {
                                callback.authNotSuccessful("Authentication not successful");
                            }
                        } else {
                            callback.authNotSuccessful("Authentication not successful, please try again");
                        }
                    } else {
                        callback.authNotSuccessful("Authentication not successful. Please try again.");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LecturerResponse> call, @NotNull Throwable t) {
                    Log.e(TAG, "onFailure: Error" + t.getLocalizedMessage() + "\n", t);
                    callback.authNotSuccessful("Authentication not successful. Please contact the administrator.");
                }
            });
        }
    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {

    }

    @Override
    public void fetchSettingsFromRemote(FetchSettingsCallback callback) {

    }

    @Override
    public void update(Lecturer item) {

    }

    @Override
    public void delete(Lecturer item) {

    }

    @Override
    public void save(Lecturer item) {

    }

    public void getLecturers(LecturerDS.LecturersLoadedCallback callback) {
        Call<LecturerResponseList> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .getLecturers();

        call.enqueue(new Callback<LecturerResponseList>() {
            @Override
            public void onResponse(@NotNull Call<LecturerResponseList> call, @NotNull Response<LecturerResponseList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successfullyLoaded(response.body().getResponseList());
                } else {
                    callback.unsuccessful("Data not available, please contact administrator.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<LecturerResponseList> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.unsuccessful("An error occurred, please contact administrator.");
            }
        });
    }

    public void createLecturer(String email, String fname, String mname, String lname, LecturerDS.CreatingLecturerCallback callback) {
        LecRequest req = new LecRequest();
        req.setEmail(email);
        req.setFname(fname);
        req.setMname(mname);
        req.setLname(lname);

        PackageRequest request = new PackageRequest();
        request.setLecRequest(req);
        Call<PackageResponse> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .createLecturer("application/json", request);

        call.enqueue(new Callback<PackageResponse>() {
            @Override
            public void onResponse(@NotNull Call<PackageResponse> call, @NotNull Response<PackageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successfullyCreated(response.body().getLecRequest());
                } else {
                    callback.unSuccessful("Please try again.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<PackageResponse> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof UnknownHostException) {
                    callback.unSuccessful("Connection error, check network connections");
                } else if (t instanceof ConnectException) {
                    callback.unSuccessful("Connection error, check network connections");
                } else {
                    callback.unSuccessful("An error occurred, please try again or contact administrator");
                }
            }
        });
    }

    @Override
    public void deleteLecturer(Lecturer lecturer, SuccessCallback deleteCallback) {
        LecturerRequest lecturerRequest = new LecturerRequest();
        lecturerRequest.setLecturer(lecturer);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .deleteLecturer("application/json", lecturerRequest);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    deleteCallback.success(response.body().getMessage());
                } else {
                    deleteCallback.unsuccessful("Please try again or contact administrator.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                if (t instanceof HttpException || t instanceof NoRouteToHostException) {
                    deleteCallback.unsuccessful("Please contact administrator to assist you.");
                } else if (t instanceof ConnectException || t instanceof SocketTimeoutException) {
                    deleteCallback.unsuccessful("Check your connection and try again");
                }
            }
        });
    }

    /*@Override
    public void updateLecturer(Lecturer lecturer, SuccessCallback callback) {
        LecturerRequest req = new LecturerRequest();
        req.setLecturer(lecturer);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .updateLec("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please try again or contact administrator.");
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                if (t instanceof HttpException || t instanceof NoRouteToHostException) {
                    callback.unsuccessful("Please contact administrator to assist you.");
                } else if (t instanceof ConnectException || t instanceof SocketTimeoutException) {
                    callback.unsuccessful("Check your connection and try again");
                }
            }
        });
    }*/

    public class PackageRequest {
        @SerializedName("package")
        private LecRequest lecRequest;

        public LecRequest getLecRequest() {
            return lecRequest;
        }

        void setLecRequest(LecRequest lecRequest) {
            this.lecRequest = lecRequest;
        }
    }

    public class PackageResponse {
        @SerializedName("package")
        private LecResponse lecRequest;

        LecResponse getLecRequest() {
            return lecRequest;
        }

        public void setLecRequest(LecResponse lecRequest) {
            this.lecRequest = lecRequest;
        }
    }
}
