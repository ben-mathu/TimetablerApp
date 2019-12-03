package com.example.timetablerapp.dashboard.dialog.faculty;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.dashboard.dialog.util.UtilViewHolder;
import com.example.timetablerapp.data.faculties.model.Faculty;

import java.util.List;

/**
 * 07/09/19 -bernard
 */
public class FacultyAdapter extends RecyclerView.Adapter<UtilViewHolder> {
    private Context context;
    private List<Faculty> facultyList;
    private OnItemSelectedListener<Faculty> onItemSelectedListener;

    public FacultyAdapter(Context context, List<Faculty> facultyList, OnItemSelectedListener<Faculty> onItemSelectedListener) {
        this.context = context;
        this.facultyList = facultyList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public UtilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lecturer_list, parent, false);
        return new UtilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilViewHolder holder, int position) {
        holder.txtId.setText(facultyList.get(position).getFacultyId());
        holder.txtName.setText(facultyList.get(position).getFacultyName());
        holder.itemView.setOnClickListener(view -> {
            onItemSelectedListener.onItemSelected(facultyList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return facultyList.size();
    }

    public void setList(List<Faculty> filteredList) {
        facultyList = filteredList;
    }
}
