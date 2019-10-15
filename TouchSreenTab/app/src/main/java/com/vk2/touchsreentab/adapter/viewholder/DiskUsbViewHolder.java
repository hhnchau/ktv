package com.vk2.touchsreentab.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk2.touchsreentab.R;

public class DiskUsbViewHolder extends RecyclerView.ViewHolder {
    private ImageView btnSeeMore, imgFileType;
    private TextView tvFileName;


    public ImageView getBtnSeeMore() {
        return btnSeeMore;
    }

    public ImageView getImgFileType() {
        return imgFileType;
    }

    public TextView getTvFileName() {
        return tvFileName;
    }
    public DiskUsbViewHolder(@NonNull View itemView) {
        super(itemView);
        imgFileType = itemView.findViewById(R.id.img_file_type);
        btnSeeMore = itemView.findViewById(R.id.btn_see_more);
        tvFileName = itemView.findViewById(R.id.tv_file_root_name);
    }
}
