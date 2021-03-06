package com.vk2.touchsreentab.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.ButtonViewHolder;;
import com.vk2.touchsreentab.databinding.ItemButtonBinding;
import com.vk2.touchsreentab.model.Category;


import java.util.List;


public class ButtonAdapter extends RecyclerView.Adapter<ButtonViewHolder> {
    private List<Category> lists;

    public ButtonAdapter(List<Category> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_button, parent, false);
        return new ButtonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ButtonViewHolder holder, final int position) {
        final Category category = lists.get(holder.getAdapterPosition());
        if (category == null) return;
        holder.buttonBinding.setCategory(category);
        holder.buttonBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null)
                    onItemClick.onItemClick(category, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public interface OnItemClick {
        void onItemClick(Category category, int p);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    public void updateData(List<Category> list) {
        lists.clear();
        lists.addAll(list);
        notifyItemRangeChanged(0, list.size(), true);
    }

    public void clearItem(){
        for (Category category: lists){
            category.setSelected(false);
        }
        notifyItemRangeChanged(0, lists.size(), true);
    }
}