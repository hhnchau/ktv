package com.vk2.touchsreentab.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.DiskUsbViewHolder;
import com.vk2.touchsreentab.model.viewmodel.FileObject;
import com.vk2.touchsreentab.utils.Constants;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<DiskUsbViewHolder> {
    private ArrayList<FileObject> listFiles;
    private Context context;
    private OnFileClickListener onFileClickListener;
    private int mCurrentFile = -1;
    public void setCurrentSelectedFile(int position){
        this.mCurrentFile = position;
        notifyDataSetChanged();
    }
    public FolderAdapter(Context context, ArrayList<FileObject> listFiles){
        this.context = context;
        this.listFiles = listFiles;
    }

    public FolderAdapter(Context context, ArrayList<FileObject> listFiles, OnFileClickListener onFileClickListener){
        this.context = context;
        this.listFiles = listFiles;
        this.onFileClickListener = onFileClickListener;
    }
    @NonNull
    @Override
    public DiskUsbViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_disk_usb, viewGroup, false);
        return new DiskUsbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DiskUsbViewHolder diskUsbViewHolder, final int i) {
        final FileObject file = listFiles.get(i);
        diskUsbViewHolder.getTvFileName().setText(file.getName());
        if (i != mCurrentFile){
            diskUsbViewHolder.getTvFileName().setTextColor(context.getResources().getColor(R.color.white));
        }
        else {
            diskUsbViewHolder.getTvFileName().setTextColor(context.getResources().getColor(R.color.yellow));
        }
        diskUsbViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFileClickListener != null){
                    onFileClickListener.onFileClick(file, i);
                    if (file.getType() == Constants.TYPE_FILE_MP4 ||file.getType() == Constants.TYPE_FILE_MP3){
                        mCurrentFile = i;
                        diskUsbViewHolder.getTvFileName().setTextColor(context.getResources().getColor(R.color.yellow));
                        notifyDataSetChanged();
                    }
                }
            }
        });
//        đổi icon theo file type, và chỉ có file Folder mới hiển thị button >
        switch (file.getType()){
            case Constants.TYPE_FOLDER:{
                diskUsbViewHolder.getImgFileType().setImageResource(R.drawable.folder_1);
                diskUsbViewHolder.getBtnSeeMore().setVisibility(View.VISIBLE);
                break;
            }
            case Constants.TYPE_FILE_NORMAL:{
                diskUsbViewHolder.getImgFileType().setImageResource(R.drawable.file_default);
                diskUsbViewHolder.getBtnSeeMore().setVisibility(View.GONE);
                break;
            }
            case Constants.TYPE_FILE_MP3:{
                diskUsbViewHolder.getImgFileType().setImageResource(R.drawable.mp3_1);
                diskUsbViewHolder.getBtnSeeMore().setVisibility(View.GONE);
                break;
            }
            case Constants.TYPE_FILE_MP4:{
                diskUsbViewHolder.getImgFileType().setImageResource(R.drawable.mp4_1);
                diskUsbViewHolder.getBtnSeeMore().setVisibility(View.GONE);
                break;
            }
        }

    }
    public void setOnFileClickListener(OnFileClickListener onFileClickListener){
        this.onFileClickListener = onFileClickListener;
    }
    public interface OnFileClickListener{
        void onFileClick(FileObject fileObject, int position);
    }
    @Override
    public int getItemCount() {
        return listFiles.size();
    }
}
