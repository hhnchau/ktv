package com.vk2.touchsreentab.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.DownloadAdapter;
import com.vk2.touchsreentab.database.entity.Song;

import java.util.ArrayList;
import java.util.List;

public class DownloadFragment extends BaseFragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_download, container, false);
        initView();
        return view;
    }

    private void initView() {

        RecyclerView rcv = view.findViewById(R.id.rcv);
        final DownloadAdapter adapter = new DownloadAdapter(new ArrayList<Song>());
        rcv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv.addItemDecoration(getDivider(layoutManager));
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);

        if (getActivity() == null) return;
        getPlaylistModelView().getPlaylist().observe(getActivity(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                adapter.update(songs);
            }
        });

    }

    @Override
    protected void onResumeFragment() {
        showBackButton();
    }
}
