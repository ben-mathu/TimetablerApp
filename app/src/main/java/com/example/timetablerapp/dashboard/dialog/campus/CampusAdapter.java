package com.example.timetablerapp.dashboard.dialog.campus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.util.UtilViewHolder;
import com.example.timetablerapp.data.campuses.model.Campus;

import java.util.List;

/**
 * 06/09/19 -bernard
 */
public class CampusAdapter extends RecyclerView.Adapter<UtilViewHolder> {

    private List<Campus> list;
    private Context context;
    private OnItemSelectedListener onItemSelectedListener;

    public CampusAdapter(List<Campus> campuses,
                         Context context,
                         OnItemSelectedListener onItemSelectedListener) {
        this.list = campuses;
        this.context = context;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public UtilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lecturer_list, parent, false);
        return new UtilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilViewHolder holder, int position) {
        holder.txtId.setText(list.get(position).getCampusId());
        holder.txtName.setText(list.get(position).getCampusName());
        holder.itemView.setOnClickListener(view -> {
            onItemSelectedListener.onItemSelected(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Campus> filteredList) {
        list = filteredList;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Campus campus);
    }
}
