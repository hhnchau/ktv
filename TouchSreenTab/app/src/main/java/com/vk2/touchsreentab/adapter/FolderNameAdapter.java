package com.vk2.touchsreentab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.viewholder.FoldernameViewHolder;
import com.vk2.touchsreentab.model.viewmodel.FileObject;

import java.util.ArrayList;

public class FolderNameAdapter extends RecyclerView.Adapter<FoldernameViewHolder> {
    private ArrayList<FileObject> listFiles;
    private Context context;
    public FolderNameAdapter(Context context, ArrayList<FileObject> listFiles){
        this.context = context;
        this.listFiles = listFiles;
    }
    public FolderNameAdapter(Context context, ArrayList<FileObject> listFiles, OnNameFolderClick onNameFolderClick){
        this.context = context;
        this.listFiles = listFiles;
        this.onNameFolderClick = onNameFolderClick;
    }
    @NonNull
    @Override
    public FoldernameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_folder_name, viewGroup, false);
        return new FoldernameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoldernameViewHolder folderNameViewHolder, final int i) {
        final FileObject fileObject = listFiles.get(i);
        folderNameViewHolder.getTvNameFolder().setText(String.valueOf(fileObject.getName()));
        folderNameViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNameFolderClick != null){
                    onNameFolderClick.onNameFolderClick(fileObject, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFiles.size();
    }
    private OnNameFolderClick onNameFolderClick;
    public void setOnNameFolderClick(OnNameFolderClick onNameFolderClick){
        this.onNameFolderClick = onNameFolderClick;
    }
    public interface OnNameFolderClick{
        void onNameFolderClick(FileObject fileObject, int position);
    }

}
