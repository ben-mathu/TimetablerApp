package com.example.timetablerapp.registerunits.register_adapter_util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 11/06/19 -bernard
 */
public class RegisterUnitsAdapter extends RecyclerView.Adapter<RegisterUnitViewHolder> {
    private final List<Unit> units;

    public RegisterUnitsAdapter(List<Unit> units) {
        this.units = units;
    }

    @NonNull
    @Override
    public RegisterUnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterUnitViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return units.size();
    }
}
