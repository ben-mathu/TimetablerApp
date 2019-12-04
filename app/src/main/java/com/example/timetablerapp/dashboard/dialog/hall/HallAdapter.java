package com.example.timetablerapp.dashboard.dialog.hall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.data.hall.model.Hall;

import java.util.List;

/**
 * 03/12/19
 *
 * @author bernard
 */
public class HallAdapter extends RecyclerView.Adapter<HallViewHolder> {
    private final Context context;
    private List<Hall> hallList;
    private final OnItemSelectedListener<Hall> onItemSelectedListener;

    public HallAdapter(Context context, List<Hall> halls, OnItemSelectedListener<Hall> onItemSelectedListener) {
        this.context = context;
        hallList = halls;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_list, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HallViewHolder holder, int position) {
        holder.txtId.setText(hallList.get(position).getHallId());
        String name = hallList.get(position).getHallName();
        holder.txtName.setText(name);
        holder.itemView.setOnClickListener(view -> {
            onItemSelectedListener.onItemSelected(hallList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return hallList.size();
    }

    public void setList(List<Hall> filteredList) {
        this.hallList = filteredList;
    }
}
