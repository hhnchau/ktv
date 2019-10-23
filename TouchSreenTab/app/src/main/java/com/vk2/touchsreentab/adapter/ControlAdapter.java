package com.vk2.touchsreentab.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;

import java.util.ArrayList;
import java.util.List;


public class ControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int CONTROL = 0;
    public static final int FUN = 1;
    private int type;

    public ControlAdapter(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == CONTROL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_control, parent, false);
            return new ControlViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fun, parent, false);
            return new FunViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (type == CONTROL) {
        } else {
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private class ControlViewHolder extends RecyclerView.ViewHolder {
        ControlViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class FunViewHolder extends RecyclerView.ViewHolder {
        FunViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClick {
        void onClick(int id, String title);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}