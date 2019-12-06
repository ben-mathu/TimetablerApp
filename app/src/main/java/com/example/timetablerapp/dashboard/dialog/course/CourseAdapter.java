package com.example.timetablerapp.dashboard.dialog.course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.dashboard.dialog.ScheduleRegistration;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {
    private Context context;
    private List<Unit> list;
    private OnItemSelectedListener<Unit> onItemSelectedListener;

    public CourseAdapter(Context context, List<Unit> unitList, OnItemSelectedListener<Unit> onItemSelectedListener) {
        this.context = context;
        list = unitList;
        this.onItemSelectedListener = onItemSelectedListener;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemSelectedListener.onItemSelected(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Unit> filteredList) {
        list = filteredList;
    }
}
