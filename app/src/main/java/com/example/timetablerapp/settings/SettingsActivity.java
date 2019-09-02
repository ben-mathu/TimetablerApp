package com.example.timetablerapp.settings;

import android.Manifest;
import android.app.Activity;
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
import android.text.format.DateFormat;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.settings.dialog.ShowExplanationDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 23/08/19 -bernard
 */
public class SettingsActivity extends AppCompatActivity {
    private static final int ACTIVITY_RESULT = 101;
    private static final int ACTIVITY_CAM_RESULT = 102;

    Bitmap bitmap;
    AlertDialog.Builder builderImage;

    // android widgets
    private CircleImageView imgPicChange;
    private ImageView imgDisplayName;
    private TextView txtDisplayName;
    private EditText edtNewPasswd;
    private ImageButton imgShowPasswd;
    private Button btnSave;

    // Literals
    private String userId = "";
    private String username = "";
    private String fileName = "";

    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MainApplication.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                DialogFragment dialog = ShowExplanationDialog.newFragment("I require your permission to write to storage to make your experience even better.");
                dialog.show(getSupportFragmentManager(), "Explanation Dialog");
            }
        }

        // get user id and username from shared preferences
        userId = MainApplication.getSharedPreferences().getString(Constants.USER_ID, "");
        username = MainApplication.getSharedPreferences().getString(Constants.USERNAME, "");

        fileName = userId + " " + username + ".png";

        // get uri
        Uri uri = Uri.parse("content://com.example.timetablerapp/" + fileName);
        File file = new File(this.getFilesDir(), uri.getPath());
        String filepath = file.getPath();

        bitmap = BitmapFactory.decodeFile(filepath);

        if (bitmap != null) {
            imgPicChange.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        txtDisplayName = findViewById(R.id.text_display_name);

        LayoutInflater inflater = getLayoutInflater();

        imgPicChange = findViewById(R.id.circle_view);
        imgPicChange.setOnClickListener(view -> {
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

            builderImage= new AlertDialog.Builder(SettingsActivity.this)
                    .setView(v)
                    .setTitle("Change Picture")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builderImage.create().show();
        });

        imgDisplayName = findViewById(R.id.img_edit);
        imgDisplayName.setOnClickListener(view -> {

            View v2 = inflater.inflate(R.layout.edit_display_name, null);

            EditText editText = v2.findViewById(R.id.edit_display_name);
            editText.setText(txtDisplayName.getText());

            AlertDialog.Builder builderName= new AlertDialog.Builder(SettingsActivity.this)
                    .setView(v2)
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            txtDisplayName.setText(editText.getText());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setTitle("Change Display Name");

            builderName.create().show();
        });

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
            // TODO : Add presenter to update user profiles
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
            Cursor cursor = getContentResolver()
                    .query(imageUri, filePathColumn, null, null, null);

            cursor.moveToFirst();
            int index = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(index);
            cursor.close();

            // convert to bitmap to set the image in imageview
            bitmap = BitmapFactory.decodeFile(imagePath);
            imgPicChange.setImageBitmap(bitmap);

            // save the image to a file
            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (ACTIVITY_CAM_RESULT == requestCode) { // only for camera pictures
            // get the bitmap from
            bitmap = (Bitmap) data.getExtras().get("data");
            imgPicChange.setImageBitmap(bitmap);

            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MainApplication.READ_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    onBackPressed();
                }
                break;
            case MainApplication.WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length <=0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    onBackPressed();
                }
        }
    }
}
