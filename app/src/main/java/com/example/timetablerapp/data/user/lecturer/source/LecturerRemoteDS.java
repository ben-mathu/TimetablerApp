package com.example.timetablerapp.data.user.lecturer.source;

import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.campuses.source.CampusLocalDS;
import com.example.timetablerapp.data.department.source.DepartmentLocalDataSrc;
import com.example.timetablerapp.data.faculties.source.FacultyLocalDS;
import com.example.timetablerapp.data.programmes.source.ProgLocalDS;
import com.example.timetablerapp.data.response.SuccessfulReport;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.AdminApi;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.admin.model.AdminRequest;
import com.example.timetablerapp.data.user.admin.source.AdminLocalDS;
import com.example.timetablerapp.data.user.lecturer.LecturerApi;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.model.LecturerRequest;
import com.example.timetablerapp.data.user.ValidationRequest;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponse;
import com.example.timetablerapp.data.user.student.StudentApi;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentRequest;
import com.example.timetablerapp.data.user.student.model.StudentResponse;
import com.example.timetablerapp.data.user.student.source.StudentLocalDS;
import com.example.timetablerapp.data.utils.RetrofitClient;

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

        Call<SuccessfulReport> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .signUpLec("application/json", lecturerRequest);

        call.enqueue(new Callback<SuccessfulReport>() {
            @Override
            public void onResponse(Call<SuccessfulReport> call, Response<SuccessfulReport> response) {
                if (response.isSuccessful()) {
                    callBack.userIsAuthSuccessful(response.body().getMessage());
                } else {
                    callBack.authNotSuccessful(response.message());
                }
            }

            @Override
            public void onFailure(Call<SuccessfulReport> call, Throwable t) {
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
                            response.body().getProgramme() != null &&
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
                                progLocalDS.save(response.body().getProgramme());

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
}
