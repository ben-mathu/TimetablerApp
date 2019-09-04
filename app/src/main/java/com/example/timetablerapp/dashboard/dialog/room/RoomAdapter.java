package com.example.timetablerapp.dashboard.dialog.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.data.room.Room;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomViewHolder> {
    private Context context;
    private List<Room> rooms;

    public RoomAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_list, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.txtId.setText(rooms.get(position).getHall_id());
        String name = rooms.get(position).getId();
        holder.txtName.setText(name);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public void setList(List<Room> filteredList) {
        rooms = filteredList;
    }
}
