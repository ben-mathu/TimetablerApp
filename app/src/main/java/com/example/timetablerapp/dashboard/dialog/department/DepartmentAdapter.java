package com.example.timetablerapp.dashboard.dialog.department;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.util.UtilViewHolder;
import com.example.timetablerapp.data.department.model.Department;

import java.util.List;

/**
 * 08/09/19 -bernard
 */
public class DepartmentAdapter extends RecyclerView.Adapter<UtilViewHolder> {
    private List<Department> list;
    private Context context;

    public DepartmentAdapter(Context context, List<Department> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UtilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lecturer_list, parent, false);
        return new UtilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilViewHolder holder, int position) {
        holder.txtId.setText(list.get(position).getDepartmentId());
        holder.txtName.setText(list.get(position).getDepartmentName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Department> filteredList) {
        this.list = filteredList;
    }
}
