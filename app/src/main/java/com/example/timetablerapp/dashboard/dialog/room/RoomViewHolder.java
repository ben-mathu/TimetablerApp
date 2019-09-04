package com.example.timetablerapp.dashboard.dialog.room;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.timetablerapp.R;

/**
 * 03/09/19 -bernard
 */
public class RoomViewHolder extends RecyclerView.ViewHolder {
    TextView txtId, txtName;

    public RoomViewHolder(View itemView) {
        super(itemView);

        txtId = itemView.findViewById(R.id.text_id);
        txtName = itemView.findViewById(R.id.text_name);
    }
}
