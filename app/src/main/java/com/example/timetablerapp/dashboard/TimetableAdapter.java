package com.example.timetablerapp.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.data.timetable.model.Timetable;

import java.util.List;

/**
 * 29/05/19 -bernard
 */
public class TimetableAdapter extends RecyclerView.Adapter<TimetableViewHolder> {

    private List<Timetable> timetableList;
    private Context context;

    public TimetableAdapter(List<Timetable> timetableList, Context context) {
        this.timetableList = timetableList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimetableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_timetable, viewGroup, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableViewHolder holder, int i) {
        String timeslot = timetableList.get(i).getTimeslot().getDay() + " " + timetableList.get(i).getTimeslot().getTime();

        holder.txtTimeslot.setText(timeslot);

        if (timetableList.get(i).getRoom() != null) {
            holder.txtRoom.setText("Class: " + timetableList.get(i).getRoom().getId());
        }

        if (timetableList.get(i).getUnit() != null) {
            holder.txtUnit.setText("Unit: " + timetableList.get(i).getUnit().getUnitName());
        }
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
    }
}
