package com.example.timetablerapp.settings;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponse;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentResponse;
import com.example.timetablerapp.login.LoginActivity;
import com.example.timetablerapp.settings.dialog.ShowExplanationDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 23/08/19 -bernard
 */
public class SettingsActivity extends AppCompatActivity implements SettingsView {
    private static final int ACTIVITY_RESULT = 101;
    private static final int ACTIVITY_CAM_RESULT = 102;

    Bitmap bitmap;
    AlertDialog.Builder builderImage;

    private Student student;
    private Faculty faculty;
    private Department department;
    private Programme programme;
    private Campus campus;
    private Admin admin;

    private SettingsPresenter presenter;

    // android widgets
    private CircleImageView imgPicChange;
    private ImageView imgDisplayName;
    private ImageView editDetails;
    private TextView txtDisplayName;
    private TextView txtUserId;
    private TextView txtUserRole;
    private EditText edtNewPasswd;
    private EditText edtFullName;
    private EditText edtFaculty;
    private EditText edtEmail;
    private EditText edtDepartment;
    private EditText edtProgramme;
    private EditText edtYearofStudy;
    private EditText edtCampus;
    private EditText edtCurrentPasswd;
    private Switch swInSession;
    private ImageButton imgShowPasswd;
    private ImageButton imgShowCurrentPasswd;
    private Button btnSave;
    private Button btnSaveDetails;

    // Literals
    private String userId = "";
    private String username = "";
    private String fileName = "";
    private String userRole = "";
    private Lecturer lecturer;
    private String newPasswd;
    private String currentPasswd;

    @Override
    protected void onStart() {
        super.onStart();

        if (presenter != null) {
            presenter = new SettingsPresenter(this,
                    MainApplication.getAdminRepo(),
                    MainApplication.getLecturerRepo(),
                    MainApplication.getStudentRepository());
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MainApplication.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                DialogFragment dialog = ShowExplanationDialog.newFragment("I require your permission to write to storage to make your experience even better.", MainApplication.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                dialog.show(getSupportFragmentManager(), "Explanation Dialog");
            }
        }

        // get user id and username from shared preferences
        userId = MainApplication.getSharedPreferences().getString(Constants.USER_ID, "");
        username = MainApplication.getSharedPreferences().getString(Constants.USERNAME, "");
        userRole = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");

        fileName = userId + " " + username + ".png";

        // get uri
//        Uri uri = Uri.parse("content://com.example.timetablerapp/" + fileName);
//        File file = new File(this.getFilesDir(), Objects.requireNonNull(uri.getPath()));
        String filepath;

        filepath = this.getFilesDir().getPath() + "/" +  fileName;

        bitmap = BitmapFactory.decodeFile(filepath);

        if (bitmap != null) {
            imgPicChange.setImageBitmap(bitmap);
        }

        txtUserId.setText(userId);
        txtUserRole.setText(userRole);
        txtDisplayName.setText(username);

        // user details section
        presenter.setUserDetails(userId, userRole);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        presenter = new SettingsPresenter(this,
                MainApplication.getAdminRepo(),
                MainApplication.getLecturerRepo(),
                MainApplication.getStudentRepository());

        // define widgets
        txtDisplayName = findViewById(R.id.text_display_name);
        txtUserId = findViewById(R.id.text_id);
        txtUserRole = findViewById(R.id.text_role);

        LayoutInflater inflater = getLayoutInflater();

        imgPicChange = findViewById(R.id.circle_view);
        imgPicChange.setOnClickListener(view -> {
            @SuppressLint("InflateParams")
            View v = inflater.inflate(R.layout.edit_image, null);

            ImageButton imgCamera = v.findViewById(R.id.image_take_pic);
            imgCamera.setOnClickListener(view1 -> {
                startActivityForResult(
                        new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE),
                        ACTIVITY_CAM_RESULT
                );
            });

            ImageButton imgGallery = v.findViewById(R.id.image_pick_from_gallery);
            imgGallery.setOnClickListener(view1 -> {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), ACTIVITY_RESULT);
            });

            builderImage= new AlertDialog.Builder(SettingsActivity.this, R.style.Theme_Dialogs)
                    .setView(v)
                    .setTitle("Change Picture")
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builderImage.create().show();
        });

        imgDisplayName = findViewById(R.id.img_edit);
        imgDisplayName.setOnClickListener(view -> {

            @SuppressLint("InflateParams")
            View v2 = inflater.inflate(R.layout.edit_display_name, null);

            EditText editText = v2.findViewById(R.id.edit_display_name);
            editText.setText(txtDisplayName.getText());

            AlertDialog.Builder builderName= new AlertDialog.Builder(SettingsActivity.this, R.style.Theme_Dialogs)
                    .setView(v2)
                    .setCancelable(true)
                    .setPositiveButton("Ok", (dialogInterface, i) -> {
                        String name = editText.getText().toString();
                        txtDisplayName.setText(name);
                        MainApplication.getSharedPreferences().edit()
                                .putString(Constants.USERNAME, name)
                                .apply();
                        presenter.updateUsername(name, userId, userRole);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                    .setTitle("Change Display Name");

            builderName.create().show();
        });

        // Change passwords
        // Current Password
        edtCurrentPasswd = findViewById(R.id.edit_current_password);

        imgShowCurrentPasswd = findViewById(R.id.image_show_current_password);
        imgShowCurrentPasswd.setOnClickListener(view -> {
            if (edtCurrentPasswd.getInputType() == InputType.TYPE_CLASS_TEXT) {
                edtCurrentPasswd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (edtCurrentPasswd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                edtCurrentPasswd.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });

        // New password
        edtNewPasswd = findViewById(R.id.edit_new_password);

        imgShowPasswd = findViewById(R.id.image_show_password);
        imgShowPasswd.setOnClickListener(view -> {
            if (edtNewPasswd.getInputType() == InputType.TYPE_CLASS_TEXT) {
                edtNewPasswd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (edtNewPasswd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                edtNewPasswd.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });

        btnSave = findViewById(R.id.button_save_password);
        btnSave.setOnClickListener(view -> {
            if (userRole.equalsIgnoreCase("admin"))
                presenter.changePassword(admin.getPassword(), newPasswd, userId, userRole, currentPasswd);
            else if (userRole.equalsIgnoreCase("student")) {
                presenter.changePassword(admin.getPassword(), newPasswd, userId,userRole,currentPasswd);
            } else if (userRole.equalsIgnoreCase("lecturer")) {
                presenter.changePassword(lecturer.getPassword(), newPasswd, userId, userRole, currentPasswd);
            }
        });

        // Edit details section
        edtFullName = findViewById(R.id.edit_user_full_name);
        edtEmail = findViewById(R.id.edit_email);
        edtCampus = findViewById(R.id.edit_campus);
        edtFaculty = findViewById(R.id.edit_faculty);
        edtDepartment = findViewById(R.id.edit_department);
        edtProgramme = findViewById(R.id.edit_programme);
        edtYearofStudy = findViewById(R.id.edit_year_of_study);

        swInSession = findViewById(R.id.switch_in_session);

        btnSaveDetails = findViewById(R.id.button_save_details);
        btnSaveDetails.setOnClickListener(view -> {
            if (userRole.equalsIgnoreCase("admin"))
                presenter.updateAdmin(admin);
            else if (userRole.equalsIgnoreCase("student"))
                presenter.updateStudent(student);
            else
                presenter.updateLecturer(lecturer);
        });

        Button btnDeleteAccount = findViewById(R.id.button_delete_account);
        btnDeleteAccount.setOnClickListener(view -> {
            AlertDialog.Builder builderName= new AlertDialog.Builder(SettingsActivity.this, R.style.Theme_Dialogs)
                    .setMessage("You are about to delete your account are you sure\n" +
                            "you want to do this.\n\n" +
                            "Use the cancel button to cancel this dialog and use the proceed button to delete your account")
                    .setCancelable(true)
                    .setPositiveButton("Proceed", (dialogInterface, i) -> {
                        presenter.deleteAccount(userRole, userId);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                    .setTitle("Change Display Name");

            builderName.create().show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;

        if (ACTIVITY_RESULT == requestCode) {

            // select the image data
            Uri imageUri = data.getData();

            // get the path of the image
            String[] filePathColumn= {MediaStore.Images.Media.DATA};
            assert imageUri != null;
            Cursor cursor = getContentResolver()
                    .query(imageUri, filePathColumn, null, null, null);

            try (FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)) {
                //move cursor to first index
                assert cursor != null;
                cursor.moveToFirst();
                int index = cursor.getColumnIndex(filePathColumn[0]);
                String imagePath = cursor.getString(index);
                cursor.close();

                // convert to bitmap to set the image in imageview
                bitmap = BitmapFactory.decodeFile(imagePath);
                imgPicChange.setImageBitmap(bitmap);

                // save the image to a file
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        } else if (ACTIVITY_CAM_RESULT == requestCode) { // only for camera pictures
            // get the bitmap from
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imgPicChange.setImageBitmap(bitmap);

            try (FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)) {

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MainApplication.READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                onBackPressed();
            }
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdminDetails(Admin admin) {
        String fullName = admin.getfName() + admin.getmName() + admin.getlName();
        edtFullName.setText(fullName);

    }

    @Override
    public void setStudentDetails(StudentResponse response) {
        student = response.getStudent();
        faculty = response.getFaculty();
        department = response.getDepartment();
        programme = response.getProgramme();
        campus = response.getCampus();

        String fullName = student.getFname() + " " + student.getMname() + " " + student.getLname();
        edtFullName.setText(fullName);
        edtEmail.setText(student.getEmail());
        edtCampus.setText(campus.getCampusName());

        edtFaculty.setVisibility(View.VISIBLE);
        edtFaculty.setText(faculty.getFacultyName());

        edtDepartment.setVisibility(View.VISIBLE);
        edtDepartment.setText(department.getDepartmentName());

        edtProgramme.setVisibility(View.VISIBLE);
        edtProgramme.setText(programme.getProgrammeName());

        edtYearofStudy.setVisibility(View.VISIBLE);
        edtYearofStudy.setText(student.getYearOfStudy());

        edtYearofStudy.setVisibility(View.VISIBLE);
        swInSession.setChecked(student.isInSession());
    }

    @Override
    public void setLecturerDetails(LecturerResponse obj) {
        lecturer = obj.getLecturer();
        faculty = obj.getFaculty();
        department = obj.getDepartment();

        String fullName = lecturer.getFirstName() + " " + lecturer.getMiddleName() + " " + lecturer.getLastName();
        edtFullName.setText(fullName);
        edtEmail.setText(lecturer.getEmail());

        edtYearofStudy.setVisibility(View.VISIBLE);
        edtFaculty.setText(faculty.getFacultyName());

        edtYearofStudy.setVisibility(View.VISIBLE);
        edtDepartment.setText(department.getDepartmentName());

    }

    @Override
    public void logUserOut() {
        MainApplication.getSharedPreferences().edit()
                .putString(Constants.USERNAME, "")
                .putBoolean(Constants.IS_LOGGED_IN, false)
                .putString(Constants.USER_ID, "")
                .apply();

        startActivity(new Intent(this, LoginActivity.class));
    }
}
