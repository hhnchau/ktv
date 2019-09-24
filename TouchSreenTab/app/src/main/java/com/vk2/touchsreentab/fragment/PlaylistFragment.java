package com.vk2.touchsreentab.fragment;import android.os.Bundle;import android.support.annotation.NonNull;import android.support.annotation.Nullable;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.adapter.PlaylistAdapter;import com.vk2.touchsreentab.database.entity.Song;import java.util.ArrayList;import java.util.List;public class PlaylistFragment extends BaseFragment {    private View view;    private RecyclerView recyclerViewSongs;    private PlaylistAdapter songAdapter;    private List<Song> listSongs;    @Nullable    @Override    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.fragment_playlist, container, false);        initView(view);        return view;    }    private void initView(View view) {        recyclerViewSongs = view.findViewById(R.id.rcvPlaylist);        listSongs = getListSong();        songAdapter = new PlaylistAdapter(listSongs);        recyclerViewSongs.setAdapter(songAdapter);        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);        recyclerViewSongs.addItemDecoration(getDivider(layoutManager));        recyclerViewSongs.setLayoutManager(layoutManager);    }    private List<Song> getListSong(){        String url = "https://api.androidhive.info/images/nature/";        List<Song> list = new ArrayList<>();        for(int i = 1; i < 20; i++){            //Song songObject = new Song(url + i + ".jpg", "Song" + i, "singerName "+i,0);            //list.add(songObject);        }        return list;    }}