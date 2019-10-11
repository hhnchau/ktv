package com.vk2.touchsreentab.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.ButtonViewHolder;
import com.vk2.touchsreentab.adapter.viewholder.RecyclerViewHolder;
import com.vk2.touchsreentab.adapter.viewholder.StickyHeaderViewHolder;
import com.vk2.touchsreentab.databinding.ItemButtonBinding;
import com.vk2.touchsreentab.databinding.ItemRecyclerviewBinding;
import com.vk2.touchsreentab.databinding.ItemStickyHeaderBinding;
import com.vk2.touchsreentab.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
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
        } else {
            final List<Category> list = map.get(String.valueOf(i));
            final ButtonAdapter adapter = new ButtonAdapter(list);
            ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setLayoutManager(new GridLayoutManager(context, 6, GridLayoutManager.VERTICAL, false));
            ((RecyclerViewHolder) viewHolder).recyclerViewBinding.rcvItem.setAdapter(adapter);

            adapter.setOnItemClick(new ButtonAdapter.OnItemClick() {
                @Override
                public void onItemClick(Category c, int p) {
                    List<Category> categories = new ArrayList<>();
                    for (Category category : list) {
                        Category ca = category.clone();
                        ca.setSelected(false);
                        categories.add(ca);
                    }
//                    for (Category category : categories){
//                        if (category.getSongTypeID().equals(c.getSongTypeID())){
//                            category.setSelected(true);
//                            break;
//                        }
//                    }
                    categories.get(p).setSelected(true);
                    adapter.setData(categories);
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
}