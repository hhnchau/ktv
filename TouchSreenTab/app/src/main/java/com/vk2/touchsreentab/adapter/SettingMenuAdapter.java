package com.vk2.touchsreentab.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.SettingMenuViewHolder;
import com.vk2.touchsreentab.databinding.ItemSettingMenuBinding;
import com.vk2.touchsreentab.model.setting.Menu;

import java.util.List;

public abstract class SettingMenuAdapter extends RecyclerView.Adapter<SettingMenuViewHolder> {
    private List<Menu> menus;

    public SettingMenuAdapter(List<Menu> menus) {
        this.menus = menus;
    }

    public abstract void OnItemClickListener(int p);

    @NonNull
    @Override
    public SettingMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSettingMenuBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_menu, viewGroup, false);
        return new SettingMenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SettingMenuViewHolder holder, int i) {
        final Menu menu = menus.get(holder.getAdapterPosition());
        if (menu != null) {
            holder.menuBinding.setMenu(menu);
            holder.menuBinding.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUnActive();
                    menu.active.set(true);
                    OnItemClickListener(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    private void setUnActive() {
        for (Menu menu : menus) {
            menu.active.set(false);
        }
    }

    public void updateStatus(int p, String status) {
        if (menus != null && menus.size() > p) {
            menus.get(p).status.set(status);
        }
    }

}
