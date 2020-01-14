package com.vk2.touchsreentab.adapter.viewholder;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.R;

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
