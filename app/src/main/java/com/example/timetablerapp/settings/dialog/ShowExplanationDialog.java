package com.example.timetablerapp.settings.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardActivity;
import com.example.timetablerapp.data.Constants;

/**
 * 01/09/19 -bernard
 */
public class ShowExplanationDialog extends DialogFragment {

    public ShowExplanationDialog() {}

    public static DialogFragment newFragment(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.MESSAGE, message);

        DialogFragment fragment = new ShowExplanationDialog();
        fragment.setArguments(bundle);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(Constants.MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialogs);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_show_explanation, null, false);

        TextView txtExplanation = view.findViewById(R.id.text_show_explanation);
        txtExplanation.setText(message);

        builder.setView(view);

        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MainApplication.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }).setNegativeButton("Cancel", (dialogInterface, i) -> {
            dismiss();
            getActivity().startActivity(new Intent(getActivity(), DashboardActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
            );
        });
        builder.setTitle("Permission Explanation");

        return builder.create();
    }
}
