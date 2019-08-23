package com.example.timetablerapp.register_units.adapter_utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class UnitsAdapter extends RecyclerView.Adapter<UnitViewHolder> {

    private List<Unit> unitList;
    private OnItemCheckedListener onItemCheckedListener;

    public UnitsAdapter(List<Unit> unitList, OnItemCheckedListener onItemCheckedListener) {
        this.unitList = unitList;
        this.onItemCheckedListener = onItemCheckedListener;
    }

    @NonNull
    @Override
    public UnitViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_units, viewGroup, false);
        return new UnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitViewHolder holder, int i) {
        holder.checkBox.setText(unitList.get(i).getUnitName());
        if (unitList.get(i).isSelected()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        Unit unit = unitList.get(i);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.checkBox.setChecked(holder.checkBox.isChecked());

                if (!unitList.get(i).isSelected()) {
                    onItemCheckedListener.onItemChecked(unit);
                    holder.checkBox.setChecked(true);
                    unitList.get(i).setSelected(true);
                } else {
                    onItemCheckedListener.onItemUnchecked(unit);
                    holder.checkBox.setChecked(false);
                    unitList.get(i).setSelected(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public void removeUnitsFromList(List<Unit> units) {
        top:for (Unit unit : units) {
            for (Unit u : unitList) {
                if (u.getId().equals(unit.getId())) {
                    unitList.remove(u);
                    continue top;
                }
            }
        }
    }

    public interface OnItemCheckedListener {
        void onItemChecked(Unit unit);
        void onItemUnchecked(Unit unit);
    }
}
