package com.example.timetablerapp.timetable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.timetablerapp.R;

/**
 * 29/05/19 -bernard
 */
public class TimetableViewHolder extends RecyclerView.ViewHolder {
    TextView txtTimeslot, txtUnit, txtRoom;
    public TimetableViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTimeslot = itemView.findViewById(R.id.text_time_slot);
        txtUnit = itemView.findViewById(R.id.text_unit);
        txtRoom = itemView.findViewById(R.id.text_room);
    }
}
