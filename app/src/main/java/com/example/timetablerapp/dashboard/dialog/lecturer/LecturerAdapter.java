package com.example.timetablerapp.dashboard.dialog.lecturer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import java.util.List;

/**
 * 02/09/19 -bernard
 */
public class LecturerAdapter extends RecyclerView.Adapter<LecturerViewHolder>  {
    private List<Lecturer> list;
    private Context context;
    private OnItemSelectedListener<Lecturer> onItemSelectedListener;

    public LecturerAdapter(Context context, List<Lecturer> list, OnItemSelectedListener<Lecturer> onItemSelectedListener) {
        this.context = context;
        this.list = list;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public LecturerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lecturer_list, parent, false);
        return new LecturerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LecturerViewHolder holder, int position) {
        holder.txtId.setText(list.get(position).getId());
        String name = list.get(position).getFirstName() + " " + list.get(position).getMiddleName() + " " + list.get(position).getLastName();
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

    public void setList(List<Lecturer> filteredList) {
        list = filteredList;
    }
}
