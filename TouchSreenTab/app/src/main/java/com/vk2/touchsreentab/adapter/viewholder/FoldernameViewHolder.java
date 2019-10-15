package com.vk2.touchsreentab.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.databinding.ItemRecyclerviewBinding;

public class FoldernameViewHolder extends RecyclerView.ViewHolder {
    private TextView tvNameFolder;

    public TextView getTvNameFolder() {
        return tvNameFolder;
    }

    public FoldernameViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNameFolder = itemView.findViewById(R.id.tv_name_folder);
    }


}
