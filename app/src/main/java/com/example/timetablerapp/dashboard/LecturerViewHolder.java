package com.example.timetablerapp.dashboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.timetablerapp.R;

/**
 * 02/09/19 -bernard
 */
public class LecturerViewHolder extends RecyclerView.ViewHolder {
    TextView txtId, txtName;

    public LecturerViewHolder(View itemView) {
        super(itemView);

        txtId = itemView.findViewById(R.id.text_id);
        txtName = itemView.findViewById(R.id.text_name);
    }
}
