package com.example.timetablerapp.dashboard.dialog.program;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.timetablerapp.dashboard.dialog.util.UtilViewHolder;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.programmes.model.Programme;

import java.util.List;

/**
 * 09/09/19 -bernard
 */
public class ProgrammeAdapter extends RecyclerView.Adapter<UtilViewHolder> {
    private Context context;
    private List<Programme> programmes;

    public ProgrammeAdapter(Context context, List<Programme> programmes) {
        this.context = context;
        this.programmes = programmes;
    }

    @NonNull
    @Override
    public UtilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UtilViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setList(List<Programme> filteredList) {
        this.programmes = filteredList;
    }
}
