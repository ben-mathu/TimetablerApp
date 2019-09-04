package com.example.timetablerapp.dashboard.dialog.course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {
    private Context context;
    private List<Unit> list;

    public CourseAdapter(Context context, List<Unit> unitList) {
        this.context = context;
        list = unitList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lecturer_list, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.txtId.setText(list.get(position).getId());
        String name = list.get(position).getUnitName();
        holder.txtName.setText(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Unit> filteredList) {
        list = filteredList;
    }
}
