package com.example.timetablerapp.dashboard.dialog.hall;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.timetablerapp.R;

import java.text.BreakIterator;

/**
 * 03/12/19
 *
 * @author bernard
 */
public class HallViewHolder extends RecyclerView.ViewHolder {
    public TextView txtId;
    public TextView txtName;

    public HallViewHolder(View itemView) {
        super(itemView);
        txtId = itemView.findViewById(R.id.text_id);
        txtName = itemView.findViewById(R.id.text_name);
    }
}
