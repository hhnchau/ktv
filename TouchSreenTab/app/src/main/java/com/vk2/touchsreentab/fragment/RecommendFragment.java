package com.vk2.touchsreentab.fragment;import android.os.Bundle;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import androidx.fragment.app.FragmentActivity;import androidx.lifecycle.Observer;import androidx.lifecycle.ViewModelProviders;import androidx.paging.PagedList;import androidx.recyclerview.widget.LinearLayoutManager;import androidx.recyclerview.widget.RecyclerView;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ProgressBar;import com.vk2.touchsreentab.R;import com.vk2.touchsreentab.adapter.MultiViewRecommendAdapter;import com.vk2.touchsreentab.database.entity.Singer;import com.vk2.touchsreentab.database.entity.Song;import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;import com.vk2.touchsreentab.model.Ablum;import com.vk2.touchsreentab.model.PageControl;import com.vk2.touchsreentab.model.viewmodel.AblumViewModel;import com.vk2.touchsreentab.model.viewmodel.PlaylistModelView;import com.vk2.touchsreentab.model.viewmodel.SongViewModel;import com.vk2.touchsreentab.utils.Constants;import com.vk2.touchsreentab.view.DiscreteScrollView.custom.CustomDiscreteScrollLayoutManager;import com.vk2.touchsreentab.view.DiscreteScrollView.CustomDiscreteScrollView;import com.vk2.touchsreentab.view.DiscreteScrollView.custom.adapter.CustomInfiniteScrollAdapter;import com.vk2.touchsreentab.view.DiscreteScrollView.custom.transform.CustomScaleTransformer;import com.vk2.touchsreentab.view.DiscreteScrollView.demodata.Item;import com.vk2.touchsreentab.view.DiscreteScrollView.demodata.Shop;import com.vk2.touchsreentab.view.DiscreteScrollView.demodata.ShopAdapter;import java.util.List;public class RecommendFragment extends BaseFragment {    private View view;    private int position;    private int currentPage = 1;    private int numberItem = 6;    private int totalPage = 0;    private boolean isVisible;    private ProgressBar progressBar;    private int totalItem = 0;    private CustomDiscreteScrollView rcvAlbum;    private CustomDiscreteScrollLayoutManager layoutManager;    private List<Item> data;    @Nullable    @Override    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {        view = inflater.inflate(R.layout.fragment_recommend, container, false);        initView();        initAlbum();        handleBackButton();        return view;    }    private void initAlbum(){//        final AblumAdapter ablumAdapter = new AblumAdapter();//        final CustomInfiniteScrollAdapter infiniteScrollAdapter = CustomInfiniteScrollAdapter.wrap(ablumAdapter);//        demo data for CustomDiscreteScrollView        Shop shop = Shop.get();        data = shop.getData();        final ShopAdapter shopAdapter = new ShopAdapter(data);        final  CustomInfiniteScrollAdapter infiniteScrollAdapter = CustomInfiniteScrollAdapter.wrap(shopAdapter);//        rcvAlbum.setAdapter(infiniteScrollAdapter);//        scale height 0 - 1f, default scale height = 0.5f        rcvAlbum.setScaleHeight(0.8f);        rcvAlbum.setOffscreenItems(4);        rcvAlbum.setItemTransitionTimeMillis(500);        rcvAlbum.setOverScrollEnabled(false);        rcvAlbum.setItemTransformer(new CustomScaleTransformer.Builder().build());        AblumViewModel ablumViewModel = ViewModelProviders.of((FragmentActivity) view.getContext()).get(AblumViewModel.class);        ablumViewModel.getAllListAblum();//        ablumViewModel.listAblumOnline.observe((FragmentActivity) view.getContext(), new Observer<PagedList<Ablum>>() {//            @Override//            public void onChanged(@Nullable PagedList<Ablum> ablums) {//                ablumAdapter.submitList(ablums);//            }//        });////        ablumAdapter.setOnItemClick(new AblumAdapter.OnItemClick() {//            @Override//            public void onItemClick(Ablum ablum) {//                handleBanner(ablum);//            }//        });    }    private void initView() {        rcvAlbum = view.findViewById(R.id.rcvAlbum);        progressBar = view.findViewById(R.id.progressBar);        if (getActivity() == null) return;        final RecyclerView recyclerView = view.findViewById(R.id.rcvSongs);        final MultiViewRecommendAdapter multiViewRecommendAdapter = new MultiViewRecommendAdapter();        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);        recyclerView.setLayoutManager(layoutManager);        recyclerView.addItemDecoration(getDivider(layoutManager));        recyclerView.setAdapter(multiViewRecommendAdapter);        SongViewModel songViewModel = ViewModelProviders.of(this).get(SongViewModel.class);        songViewModel.getAllListSong(getActivity());        songViewModel.listSongOnline.observe(this, new Observer<PagedList<Song>>() {            @Override            public void onChanged(@Nullable PagedList<Song> songs) {                if (songs != null) {                    totalPage = (int) Math.ceil((double) Constants.MAX_ITEM_SONG / numberItem);                    updateCurrentPage(Fragmentez.RECOMMEND_FRAGMENT, currentPage, totalPage);                }                multiViewRecommendAdapter.submitList(songs);            }        });        multiViewRecommendAdapter.setOnItemClick(new MultiViewRecommendAdapter.OnItemClick() {            @Override            public void onBannerClick(Ablum album) {                handleBanner(album);            }            @Override            public void onImageSinger(Singer singer) {                gotoSinger(singer.getId(), singer.getName());            }            @Override            public void onImageClick(Song song) {                gotoSinger(song.getSingerId1(), song.getSingerName1());            }            @Override            public void onItemClick(Song song) {                updatePlaylist(song, PlaylistModelView.ADD);            }            @Override            public void onIconClick(Song song) {                showDialog(song);            }        });        getPageControlViewModel().getPageControl().observe(getActivity(), new Observer<PageControl>() {            @Override            public void onChanged(PageControl pageControl) {                if (pageControl.getFrg() == Fragmentez.RECOMMEND_FRAGMENT) {                    if (pageControl.getDirection() == PageControl.NEXT) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage++;                                if (currentPage > totalPage) {                                    currentPage = 1;                                    layoutManager.scrollToPositionWithOffset(0, 0);                                    recyclerView.smoothScrollToPosition(0);                                } else {                                    int nextPosition = currentPage * numberItem - 1;                                    recyclerView.smoothScrollToPosition(nextPosition);                                }                                int nextPosition = currentPage * numberItem - 1;                                recyclerView.smoothScrollToPosition(nextPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.PREVIOUS) {                        recyclerView.post(new Runnable() {                            @Override                            public void run() {                                currentPage--;                                int prevPosition = currentPage * numberItem - numberItem;                                recyclerView.smoothScrollToPosition(prevPosition);                            }                        });                    } else if (pageControl.getDirection() == PageControl.TOP) {                        if (position > 0) {                            recyclerView.post(new Runnable() {                                @Override                                public void run() {                                    currentPage = 1;                                    layoutManager.scrollToPositionWithOffset(0, 0);                                    recyclerView.smoothScrollToPosition(0);                                }                            });                        }                    }                }            }        });        songViewModel.progressLoadStatus.observe(this, new Observer<Boolean>() {            @Override            public void onChanged(final Boolean status) {                if (getActivity() != null)                    getActivity().runOnUiThread(new Runnable() {                        @Override                        public void run() {                            if (status) {                                progressBar.setVisibility(View.VISIBLE);                            } else {                                progressBar.setVisibility(View.GONE);                            }                        }                    });            }        });        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {            @Override            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {                super.onScrollStateChanged(recyclerView, newState);                if (newState == RecyclerView.SCROLL_STATE_IDLE) {                    int p;                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();                    if (linearLayoutManager != null) {                        position = linearLayoutManager.findFirstVisibleItemPosition();                        if (position == 0 && isVisible) {                            hideBackButton();                            showTopBar();                            isVisible = false;                        } else if (position > 0 && !isVisible) {                            hideTopBar();                            showBackButton();                            isVisible = true;                        }                        p = linearLayoutManager.findLastVisibleItemPosition();                        currentPage = p / numberItem;                        if (currentPage <= 0) return;                        int current = p % (currentPage * numberItem);                        if (current > 0) currentPage++;                        updateCurrentPage(Fragmentez.RECOMMEND_FRAGMENT, currentPage, totalPage);                    }                }            }        });    }    private void handleBackButton() {        if (position == 0) {            hideBackButton();            showTopBar();            isVisible = false;        } else {            showBackButton();            hideTopBar();            isVisible = true;        }    }    @Override    protected void onResumeFragment() {        handleBackButton();        clearTextSearch();    }}