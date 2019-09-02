package com.example.timetablerapp.dashboard.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.timetablerapp.R;

/**
 * 01/09/19 -bernard
 */
public class AddLecturerFragment extends Fragment {

    private EditText edtFName, edtMName, edtLName;
    private Button btnCreateUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_lecturer, container, false);

        edtFName = view.findViewById(R.id.edit_first_name);
        edtLName = view.findViewById(R.id.edit_last_name);
        edtMName = view.findViewById(R.id.edit_middle_name);

        btnCreateUser = view.findViewById(R.id.button_add_lecturer);
        btnCreateUser.setOnClickListener(view1 -> {
            // TODO : handle creating lecturer
        });
        return view;
    }
}
