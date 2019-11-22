package com.vk2.touchsreentab.fragment;import android.arch.lifecycle.Observer;import android.content.Context;import android.os.AsyncTask;import android.os.Bundle;import android.os.Handler;import android.os.Looper;import android.support.annotation.NonNull;import android.support.annotation.Nullable;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.ProgressBar;import com.google.api.client.googleapis.json.GoogleJsonResponseException;import com.google.api.client.http.HttpRequest;import com.google.api.client.http.HttpRequestInitializer;import com.google.api.client.http.javanet.NetHttpTransport;import com.google.api.client.json.jackson2.JacksonFactory;import com.google.api.services.youtube.YouTube;import com.google.api.services.youtube.model.ResourceId;import com.google.api.services.youtube.model.SearchListResponse;import com.google.api.services.youtube.model.SearchResult;import com.google.api.services.youtube.model.Thumbnail;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.adapter.YoutubeAdapter;import com.vk2.touchsreentab.api.ApiController;import com.vk2.touchsreentab.database.entity.Song;import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;import com.vk2.touchsreentab.model.TextSearch;import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;import com.vk2.touchsreentab.utils.SaveDataUtils;import java.io.IOException;import java.util.ArrayList;import java.util.Iterator;import java.util.List;public class SearchYoutubeFragment extends BaseFragment {    private View view;    private boolean loading = true;    private int pastVisiblesItems, visibleItemCount, totalItemCount;    private List<Song> mListSong;    private ProgressBar progressBar;    private String mFullTextSearch = "";    private String youtubePageToken = null;    public static boolean isShowSort;    public static final String YOUTUBE_LINK_START = "https://www.youtube.com/watch?v=";    private YoutubeAdapter songAdapter;    private int countItem = 0, currentItem = 0;    @Nullable    @Override    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.fragment_search_youtube, container, false);        initView();        showBackButton();        showEnterButton();        return view;    }    private void initView() {        if (getActivity() == null) return;        mListSong = new ArrayList<>();        RecyclerView recyclerView = view.findViewById(R.id.rcvSongs);        recyclerView.setHasFixedSize(true);        recyclerView.setNestedScrollingEnabled(true);        songAdapter = new YoutubeAdapter(mListSong);        recyclerView.setAdapter(songAdapter);        progressBar = view.findViewById(R.id.progressBar);        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);        recyclerView.setLayoutManager(mLayoutManager);        recyclerView.addItemDecoration(getDivider(mLayoutManager));        getTextSearchViewModel().getTextSearch().observe(getActivity(), new Observer<TextSearch>() {            @Override            public void onChanged(TextSearch textSearch) {                if (textSearch.getFrg() == Fragmentez.SEARCH_YOUTUBE_FRAGMENT) {                    clearRecyclerView();                    mFullTextSearch = textSearch.getTextSearch();                    new Handler().post(new Runnable() {                        @Override                        public void run() {                            new SearchYoutube(getActivity(), progressBar, mFullTextSearch).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);                        }                    });                }            }        });        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {            @Override            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {                if (dy > 0) {                    visibleItemCount = mLayoutManager.getChildCount();                    totalItemCount = mLayoutManager.getItemCount();                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();                    if (loading) {                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {                            loading = false;                            Log.v("...", "Last Item Wow !");                            new Handler().post(new Runnable() {                                @Override                                public void run() {                                    new SearchYoutube(getActivity(), progressBar, mFullTextSearch).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);                                }                            });                        }                    }                }            }        });        songAdapter.setOnItemClick(new YoutubeAdapter.OnItemClick() {            @Override            public void onItemClick(Song youtube) {                updatePlaylist(youtube, PlaylistModelView.ADD);            }            @Override            public void onIconClick() {            }        });    }    private class SearchYoutube extends AsyncTask<String, Integer, List<Song>> {        private Context mContext;        private ProgressBar progressBar;        private SearchYoutube(Context context, ProgressBar pb, String keyword) {            mContext = context;            progressBar = pb;        }        @Override        protected void onPreExecute() {            super.onPreExecute();            progressBar.setVisibility(View.VISIBLE);        }        protected List<Song> doInBackground(String... urls) {            return searchSongFromYouTube(mContext, mFullTextSearch, 10);        }        protected void onProgressUpdate(Integer... progress) {        }        protected void onPostExecute(List<Song> result) {            if (result != null) {                mListSong.addAll(result);                loading = true;                updateUI(progressBar);            }        }    }    private void updateUI(final ProgressBar progressBar) {        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {            @Override            public void run() {                songAdapter.notifyItemRangeChanged(currentItem, countItem);                progressBar.setVisibility(View.GONE);            }        }, 100);    }    private List<Song> searchSongFromYouTube(Context mContext, final String keyword, final long maxResult) {        ArrayList<Song> output = new ArrayList<>();        try {            isShowSort = true;            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {                public void initialize(HttpRequest request) throws IOException {                }            }).setApplicationName("youtube-cmdline-search-sample").build();            YouTube.Search.List search = youtube.search().list("id,snippet");            String apiKey = SaveDataUtils.getInstance(mContext).getKeyYouTube();            search.setKey(apiKey);            if (isShowSort) {                isShowSort = false;                search.setQ(keyword);            } else {                isShowSort = true;                search.setQ("karaoke " + keyword + "|" + "beat " + keyword);            }            search.setType("video");            search.setSafeSearch("moderate");            search.setFields("nextPageToken,items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url,snippet/channelTitle)");            search.setMaxResults(maxResult);            search.setPageToken(youtubePageToken);            Log.d("TAG-yt search", "youtubePageToken: " + youtubePageToken + " getYoutubeSafeSearch: moderate" + " \n" + "key youtube: " + apiKey);            SearchListResponse searchResponse = search.execute();            List<SearchResult> searchResultList = searchResponse.getItems();            if (searchResultList != null) {                Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();                if (!iteratorSearchResults.hasNext()) {                    Log.d("TAG"," There aren't any results for your query.");                }                while (iteratorSearchResults.hasNext()) {                    SearchResult singleVideo = iteratorSearchResults.next();                    ResourceId rId = singleVideo.getId();                    if (rId.getKind().equals("youtube#video")) {                        Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();                        Song song = new Song();                        song.setVideoPath(YOUTUBE_LINK_START.concat(rId.getVideoId()));                        song.setFileName("Y".concat(rId.getVideoId()));                        song.setImage(thumbnail.getUrl());                        song.setSongName(singleVideo.getSnippet().getTitle());                        output.add(song);                    }                }                countItem = output.size();                currentItem = mListSong.size() + 1;                youtubePageToken = searchResponse.getNextPageToken();                Log.d("TAG", "youtubePageToken: " + youtubePageToken);                return output;            }        } catch (GoogleJsonResponseException e) {            Log.d("TAG","There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());        } catch (IOException e) {            Log.d("TAG","There was an IO error: " + e.getCause() + " : " + e.getMessage());        } catch (Throwable t) {            t.printStackTrace();        }        ApiController.getInstance().getKeyApiYouTube(mContext, 1);        return output;    }    private void clearRecyclerView() {        youtubePageToken = null;        mListSong.clear();    }    @Override    protected void onResumeFragment() {        showBackButton();        showEnterButton();    }}