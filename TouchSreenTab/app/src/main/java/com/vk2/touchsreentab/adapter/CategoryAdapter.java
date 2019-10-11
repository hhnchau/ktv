package com.vk2.touchsreentab.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.RecyclerViewHolder;
import com.vk2.touchsreentab.adapter.viewholder.StickyHeaderViewHolder;
import com.vk2.touchsreentab.databinding.ItemRecyclerviewBinding;
import com.vk2.touchsreentab.databinding.ItemStickyHeaderBinding;
import com.vk2.touchsreentab.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ButtonAdapter adapterCategory, adapterLanguage;
    private List<Category> lstCategory, lstLanguage;
    private Map<String, List<Category>> map;

    public CategoryAdapter(Map<String, List<Category>> map) {
        this.map = map;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType == 0 || viewType == 2) {
            ItemStickyHeaderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_sticky_header, parent, false);
            return new StickyHeaderViewHolder(binding);
        } else {
            ItemRecyclerviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recyclerview, parent, false);
            return new RecyclerViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (i == 0) {
            ((StickyHeaderViewHolder) viewHolder).stickyBinding.setValue(context.getString(R.string.category));
        } else if (i == 2) {
            ((StickyHeaderViewHolder) viewHolder).stickyBinding.setValue(context.getString(R.string.language));
        } else if (i == 1) {
            lstCategory = map.get(String.valueOf(i));
            adapterCategory = new ButtonAdapter(lstCategory);
            RecyclerView rcvCategory = ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem;
            rcvCategory.setLayoutManager(new GridLayoutManager(context, 6, GridLayoutManager.VERTICAL, false));
            rcvCategory.setAdapter(adapterCategory);
            adapterCategory.setOnItemClick(new ButtonAdapter.OnItemClick() {
                @Override
                public void onItemClick(Category c, int p) {
                    if (lstCategory != null) {
                        List<Category> categories = new ArrayList<>();
                        for (Category category : lstCategory) {
                            Category ca = category.clone();
                            ca.setSelected(false);
                            categories.add(ca);
                        }
                        categories.get(p).setSelected(true);
                        adapterCategory.updateData(categories);

                        if (adapterLanguage != null) adapterLanguage.clearItem();
                        if (onItemClick != null) onItemClick.onClick(c.getSongTypeID(), c.getSongTypeName());
                    }
                }
            });
        } else if (i == 3) {
            lstLanguage = map.get(String.valueOf(i));
            adapterLanguage = new ButtonAdapter(lstLanguage);
            RecyclerView rcvLanguage = ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem;
            rcvLanguage.setLayoutManager(new GridLayoutManager(context, 6, GridLayoutManager.VERTICAL, false));
            rcvLanguage.setAdapter(adapterLanguage);
            adapterLanguage.setOnItemClick(new ButtonAdapter.OnItemClick() {
                @Override
                public void onItemClick(Category c, int p) {
                    if (lstLanguage != null) {
                        List<Category> categories = new ArrayList<>();
                        for (Category category : lstLanguage) {
                            Category ca = category.clone();
                            ca.setSelected(false);
                            categories.add(ca);
                        }
                        categories.get(p).setSelected(true);
                        adapterLanguage.updateData(categories);

                        if (adapterCategory != null) adapterCategory.clearItem();
                        if (onItemClick != null) onItemClick.onClick(c.getLangNum(), c.getLangDis());
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnItemClick {
        void onClick(int id, String title);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}