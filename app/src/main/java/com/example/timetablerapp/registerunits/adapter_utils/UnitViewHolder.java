package com.example.timetablerapp.registerunits.adapter_utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.example.timetablerapp.R;

/**
 * 11/06/19 -bernard
 */
public class UnitViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;
    View itemView;

    public UnitViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        checkBox = itemView.findViewById(R.id.checkbox_unit);
        checkBox.setClickable(false);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }
}
