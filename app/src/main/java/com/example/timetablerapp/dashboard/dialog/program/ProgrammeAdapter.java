package com.example.timetablerapp.dashboard.dialog.program;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.dashboard.dialog.util.UtilViewHolder;
import com.example.timetablerapp.data.programmes.model.Programme;

import java.util.List;

/**
 * 09/09/19 -bernard
 */
public class ProgrammeAdapter extends RecyclerView.Adapter<UtilViewHolder> {
    private Context context;
    private List<Programme> programmes;
    private OnItemSelectedListener<Programme> onItemSelectedListener;

    public ProgrammeAdapter(Context context, List<Programme> programmes, OnItemSelectedListener<Programme> onItemSelectedListener) {
        this.context = context;
        this.programmes = programmes;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public UtilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lecturer_list, null, false);
        return new UtilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilViewHolder holder, int position) {
        holder.txtId.setText(programmes.get(position).getProgrammeId());
        holder.txtName.setText(programmes.get(position).getProgrammeName());
        holder.itemView.setOnClickListener(view -> {
            onItemSelectedListener.onItemSelected(programmes.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return programmes.size();
    }

    public void setList(List<Programme> filteredList) {
        this.programmes = filteredList;
    }
}
