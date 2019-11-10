package com.example.timetablerapp.data.user.lecturer.source;

import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.source.CampusLocalDS;
import com.example.timetablerapp.data.department.source.DepartmentLocalDataSrc;
import com.example.timetablerapp.data.faculties.source.FacultyLocalDS;
import com.example.timetablerapp.data.programmes.source.ProgLocalDS;
import com.example.timetablerapp.data.response.MessageReport;
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
import com.example.timetablerapp.data.user.student.source.StudentLocalDS;
import com.example.timetablerapp.data.utils.RetrofitClient;
import com.google.gson.annotations.SerializedName;

import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class LecturerRemoteDS implements UserDataSource<Lecturer> {
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
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful()) {
                    callBack.userIsAuthSuccessful(response.body().getMessage());
                } else {
                    callBack.authNotSuccessful(response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callBack.authNotSuccessful(t.getMessage());
            }
        });
    }

    @Override
    public void authUser(UserDataSource.UserAuthCallback callBack, Lecturer lecturer) {

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
                public void onResponse(Call<AdminRequest> call, Response<AdminRequest> response) {
                    if (response.isSuccessful()) {
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
                public void onFailure(Call<AdminRequest> call, Throwable t) {
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
                public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                    if (response.isSuccessful() &&
                            response.body().getStudent() != null &&
                            response.body().getCampus() != null &&
                            response.body().getProgramme() != null &&
                            response.body().getFaculty() != null &&
                            response.body().getDepartment() != null) {
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
                public void onFailure(Call<StudentResponse> call, Throwable t) {
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
                public void onResponse(Call<LecturerResponse> call, Response<LecturerResponse> response) {
                    if (response.isSuccessful() &&
                            response.body().getLecturer() != null &&
                            response.body().getFaculty() != null &&
                            response.body().getDepartment() != null) {
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
                public void onFailure(Call<LecturerResponse> call, Throwable t) {
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
            public void onResponse(Call<LecturerResponseList> call, Response<LecturerResponseList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successfullyLoaded(response.body().getResponseList());
                } else {
                    callback.unsuccessful("Data not available, please contact administrator.");
                }
            }

            @Override
            public void onFailure(Call<LecturerResponseList> call, Throwable t) {
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
            public void onResponse(Call<PackageResponse> call, Response<PackageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successfullyCreated(response.body().getLecRequest());
                } else {
                    callback.unSuccessful("Please try again.");
                }
            }

            @Override
            public void onFailure(Call<PackageResponse> call, Throwable t) {
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

    public class PackageRequest {
        @SerializedName("package")
        private LecRequest lecRequest;

        public LecRequest getLecRequest() {
            return lecRequest;
        }

        public void setLecRequest(LecRequest lecRequest) {
            this.lecRequest = lecRequest;
        }
    }

    public class PackageResponse {
        @SerializedName("package")
        private LecResponse lecRequest;

        public LecResponse getLecRequest() {
            return lecRequest;
        }

        public void setLecRequest(LecResponse lecRequest) {
            this.lecRequest = lecRequest;
        }
    }
}
