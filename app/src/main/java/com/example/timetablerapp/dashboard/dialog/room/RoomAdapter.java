package com.example.timetablerapp.dashboard.dialog.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.dashboard.dialog.util.UtilViewHolder;
import com.example.timetablerapp.data.room.model.Room;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public class RoomAdapter extends RecyclerView.Adapter<UtilViewHolder> {
    private Context context;
    private List<Room> rooms;
    private OnItemSelectedListener<Room> onItemSelectedListener;

    RoomAdapter(Context context, List<Room> rooms, OnItemSelectedListener<Room> onItemSelectedListener) {
        this.context = context;
        this.rooms = rooms;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public UtilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_list, parent, false);
        return new UtilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilViewHolder holder, int position) {
        holder.txtId.setText(rooms.get(position).getHall_id());
        String name = rooms.get(position).getId();
        holder.txtName.setText(name);
        holder.itemView.setOnClickListener(view -> {
            onItemSelectedListener.onItemSelected(rooms.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public void setList(List<Room> filteredList) {
        rooms = filteredList;
    }
}
