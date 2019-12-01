package com.example.timetablerapp.dashboard.dialog.lecturer;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.util.CompareStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 01/09/19 -bernard
 */
public class AddLecturerFragment extends Fragment implements LecturerView, OnItemSelectedListener<Lecturer> {

    private LecturerAdapter adapter;
    private LecturerPresenter presenter;

    private List<Lecturer> list;
    private List<Department> departments;
    private List<Faculty> faculties;

    private Faculty faculty;
    private Department department;
    private Lecturer lecturer;

    private Button btnCreateUser;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Spinner spinnerFaculty;
    private Spinner spinnerDepartment;
    private AlertDialog dialog;
    private TextView txtFacultyName;
    private TextView txtDepartmentName;

    private String positiveBtnText;
    private String negativeBtnText;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new LecturerPresenter(
                this,
                MainApplication.getLecturerRepo(),
                MainApplication.getFacultyRepo(),
                MainApplication.getDepRepo());

        presenter.getLecturers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_lecturer, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_lecturers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Lecturer> filteredList = filterList(list, s);
                if (adapter != null) {
                    adapter.setList(filteredList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        btnCreateUser = view.findViewById(R.id.button_add_lecturer);
        btnCreateUser.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.Theme_Dialogs);

            @SuppressLint("InflateParams")
            View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.lecturer_fields, null, false);
            EditText edtFName = dialogView.findViewById(R.id.edit_first_name),
                    edtMName = dialogView.findViewById(R.id.edit_middle_name),
                    edtLName = dialogView.findViewById(R.id.edit_last_name),
                    edtEmail = dialogView.findViewById(R.id.edit_email);
            builder.setView(dialogView);
            builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                if (edtEmail.getText().toString().isEmpty()
                        || edtFName.getText().toString().isEmpty()
                        || edtLName.getText().toString().isEmpty()
                        || edtMName.getText().toString().isEmpty()
                ) {
                    showMessage("All the fields are required");
                } else {
                    presenter.createLecturer(edtEmail.getText().toString(), edtFName.getText().toString(),
                            edtLName.getText().toString(), edtMName.getText().toString()
                    );
                }
            });

            builder.setCancelable(true);
            AlertDialog dialog = builder.create();
            dialog.show();

            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(550, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        return view;
    }

    private List<Lecturer> filterList(List<Lecturer> list, String s) {
        List<Lecturer> items = new ArrayList<>();

        if (list != null) {
            for (Lecturer lecturer : list) {
                if (CompareStrings.compare(lecturer.getFirstName(), s) || CompareStrings.compare(lecturer.getMiddleName(), s)
                        || CompareStrings.compare(lecturer.getLastName(), s) || CompareStrings.compare(lecturer.getId(), s)
                ) {

                    items.add(lecturer);
                }
            }
        }

        return items;
    }

    @Override
    public void setLecturers(List<Lecturer> list) {
        if (list != null) {
            if (!list.isEmpty()) {
                this.list = list;
                adapter = new LecturerAdapter(getActivity(), list, this);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void showMessage(String message) {
        try {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmail(LecResponse response) {
        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_EMAIL, new String[]{response.getEmail()})
                .putExtra(Intent.EXTRA_SUBJECT, "Account Creation")
                .putExtra(Intent.EXTRA_TEXT, response.getMessage() + "\nName: " + response.getFname() + " " + response.getMname() + " " + response.getLname()
                        + "\nAccess code: " + response.getCode())
                .setType("message/rfc822");

        try {
            startActivity(Intent.createChooser(intent, "Send email:"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
        txtFacultyName.setText(faculty.getFacultyName());

        // set default faculty
        spinnerFaculty.setSelection(getPosition(faculty));
    }

    /**
     * Get position to set default faculty name
     *
     * @param faculty object stores current faculty properties for selected lecturer
     * @return position of faculty
     */
    private int getPosition(Faculty faculty) {
        int pos = 0;

        for (Faculty item : faculties) {
            if (faculty.getFacultyName().equals(item.getFacultyName())) {
                return faculties.indexOf(item);
            }
        }
        return pos;
    }

    @Override
    public void setDepartment(Department department) {
        this.department = department;
        txtDepartmentName.setText(department.getDepartmentName());

        spinnerDepartment.setSelection(getPosition(department));

    }

    private int getPosition(Department department) {
        int pos = 0;

        for (Department item : departments) {
            if (department.getDepartmentName().equals(item.getDepartmentName())) {
                return departments.indexOf(item);
            }
        }
        return pos;
    }

    @Override
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
        if (spinnerFaculty != null) {
            List<String> facultyNames = new ArrayList<>();
            for (Faculty faculty : this.faculties) {
                facultyNames.add(faculty.getFacultyName());
            }

            // declare adapter for spinner
            spinnerFaculty.setAdapter(adapter(facultyNames));
        }

        if (faculties != null)
            presenter.getFaculty(lecturer.getFacultyId());
    }

    @Override
    public void setDepartments(List<Department> departments) {
        this.departments = departments;

        if (spinnerDepartment != null) {
            List<String> departmentNames = new ArrayList<>();
            for (Department department : departments) {
                departmentNames.add(department.getDepartmentName());
            }

            spinnerDepartment.setAdapter(adapter(departmentNames));
        }

        if (departments != null)
            presenter.getDepartmentById(lecturer.getDepartmentId());
    }

    /**
     * Prepares an array of string to fill the drop down list
     *
     * @param strList list of strings to be included in the menu
     * @return an array adapter to fill the drop down menu
     */
    private ArrayAdapter<String> adapter(List<String> strList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_dropdown_item, strList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    @Override
    public void onItemSelected(Lecturer lec) {
        lecturer = lec;

        presenter.getFaculties();
        presenter.getDepartments(lec.getFacultyId());

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_lecturer, null, false);

        LinearLayout llLecturerDetails = view.findViewById(R.id.ll_lecturer_details);
        LinearLayout llEditLecturerDetails = view.findViewById(R.id.ll_lecturer_edit_details);

        // Before editing
        // show lecturer details.
        TextView txtLecturerId = view.findViewById(R.id.text_lecturer_id);
        txtLecturerId.setText(lec.getId());
        TextView txtFName = view.findViewById(R.id.text_f_name);
        txtFName.setText(lec.getFirstName());

        TextView txtMName = view.findViewById(R.id.text_m_name);
        txtMName.setText(lec.getMiddleName());

        TextView txtLName = view.findViewById(R.id.text_l_name);
        txtLName.setText(lec.getLastName());

        txtFacultyName = view.findViewById(R.id.text_faculty);


        txtDepartmentName = view.findViewById(R.id.text_department);


        TextView txtInSession = view.findViewById(R.id.show_in_session);
        if (lec.isInSession()) txtInSession.setText(R.string.in_session);
        else txtInSession.setText(R.string.not_in_session);

        EditText edtLecId = view.findViewById(R.id.edit_lecturer_id);
        edtLecId.setText(lec.getId());

        EditText edtFName = view.findViewById(R.id.edit_f_name);
        edtFName.setText(lec.getFirstName());

        EditText edtMName = view.findViewById(R.id.edit_m_name);
        edtMName.setText(lec.getMiddleName());

        EditText edtLName = view.findViewById(R.id.edit_l_name);
        edtLName.setText(lec.getLastName());

        spinnerFaculty = view.findViewById(R.id.change_spinner_faculty);
        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private String facultyName;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                facultyName = parent.getItemAtPosition(position).toString();
                faculty = faculties.get(position);
                presenter.getDepartments(faculties.get(position).getFacultyId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                facultyName = parent.getSelectedItem().toString();
                faculty = faculties.get(parent.getSelectedItemPosition());
                presenter.getDepartments(faculties.get(parent.getSelectedItemPosition()).getFacultyId());
            }
        });

        spinnerDepartment = view.findViewById(R.id.change_spinner_departments);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private String departmentName = "";

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                departmentName = parent.getItemAtPosition(position).toString();
                department = departments.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                departmentName = parent.getSelectedItem().toString();
                department = departments.get(parent.getSelectedItemPosition());
            }
        });

        Switch swInSession = view.findViewById(R.id.switch_in_session);
        if (lec.isInSession()) {
            swInSession.setChecked(true);
        } else {
            swInSession.setChecked(false);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()),R.style.Theme_Dialogs);
        builder.setTitle("Edit Lecturer");
        builder.setView(view);

        // set initial positive button text to edit
        positiveBtnText = "Edit";
        builder.setPositiveButton(positiveBtnText, null);

        // set initial negative button text to delete
        negativeBtnText = "Delete";
        builder.setNegativeButton(negativeBtnText, null);

        dialog = builder.create();
        dialog.show();

        // configure positive button
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
            if (positiveBtnText.equalsIgnoreCase("edit")) {
                llEditLecturerDetails.setVisibility(View.VISIBLE);
                llLecturerDetails.setVisibility(View.GONE);

                // if button pressed and text == "edit" set positive button to "save"
                // and negative button "cancel"
                positiveBtnText = "Save";
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.save);

                negativeBtnText = "Cancel";
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(negativeBtnText);
            } else {
                Lecturer lecturer = new Lecturer();
                lecturer.setInSesson(swInSession.isChecked());
                lecturer.setId(edtLecId.getText().toString());
                lecturer.setFirstName(edtFName.getText().toString());
                lecturer.setMiddleName(edtMName.getText().toString());
                lecturer.setLastName(edtLName.getText().toString());
                lecturer.setFacultyId(faculties.get(spinnerFaculty.getSelectedItemPosition()).getFacultyId());
                lecturer.setDepartmentId(departments.get(spinnerDepartment.getSelectedItemPosition()).getDepartmentId());
                presenter.updateLecturer(lecturer);

                dialog.dismiss();
            }
        });

        // configure negative button
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
            if (negativeBtnText.equalsIgnoreCase("cancel")) {
                dialog.dismiss();
            } else {
                presenter.deleteLecturer(lec);
                dialog.dismiss();
            }
        });
    }
}
