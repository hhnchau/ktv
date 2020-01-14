package com.vk2.touchsreentab.model;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class CategoryDiffUtil extends DiffUtil.Callback {
    private List<Category> newList;
    private List<Category> oldList;

    public CategoryDiffUtil(List<Category> newList, List<Category> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return newList.get(i1).isSelected() == oldList.get(i).isSelected();
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        int rsl = newList.get(i1).compareTo(oldList.get(i));
        return rsl == 0;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Category newCategory = newList.get(newItemPosition);
        Category oldCategory = newList.get(oldItemPosition);

        Bundle bundle = new Bundle();

        if (newCategory.isSelected() != oldCategory.isSelected())
            bundle.putBoolean("selected", newCategory.isSelected());

        if (bundle.size() == 0) return null;
        return bundle;
    }
}
