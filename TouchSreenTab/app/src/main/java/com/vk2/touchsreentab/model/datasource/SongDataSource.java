package com.vk2.touchsreentab.model.datasource;import android.arch.lifecycle.MutableLiveData;import android.arch.paging.PageKeyedDataSource;import android.support.annotation.NonNull;import android.util.Log;import com.vk2.touchsreentab.api.DataUtils;import com.vk2.touchsreentab.database.entity.Song;import com.vk2.touchsreentab.model.ResultSong;import com.vk2.touchsreentab.utils.Constants;import io.reactivex.Observable;import io.reactivex.Observer;import io.reactivex.android.schedulers.AndroidSchedulers;import io.reactivex.disposables.Disposable;import io.reactivex.schedulers.Schedulers;public class SongDataSource extends PageKeyedDataSource<Integer, Song> {    private int firstPage = 1,nextPage = 0;    public MutableLiveData<String> getProgressLiveStatus() {        return progressLiveStatus;    }    private MutableLiveData<String> progressLiveStatus = new MutableLiveData<>();    @Override    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Song> callback) {        Observable<ResultSong> observable = DataUtils.getAPIOnline().getListSongs();        observable.observeOn(AndroidSchedulers.mainThread())                .subscribeOn(Schedulers.io())                .subscribe(new Observer<ResultSong>() {                    @Override                    public void onSubscribe(Disposable d) {                        d.toString();                        progressLiveStatus.postValue(Constants.LOADING);                    }                    @Override                    public void onNext(ResultSong songs) {                        firstPage = firstPage - 1;                         nextPage = firstPage + 1;                        if(songs != null ) {                            Log.d("TAG", "getListSongs onNext: "+songs.getData().size());                            progressLiveStatus.postValue(Constants.LOADED);                            callback.onResult(songs.getData(),firstPage,nextPage);                        }                    }                    @Override                    public void onError(Throwable e) {                        Log.d("TAG", "getListSongs onError: "+e.toString());                        progressLiveStatus.postValue(Constants.LOADED);                    }                    @Override                    public void onComplete() {                        Log.e("TAG", "getListSongs onComplete");                    }                });    }    @Override    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Song> callback) {    }    @Override    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Song> callback) {        Observable<ResultSong> observable = DataUtils.getAPIOnline().getListSongs();        observable.observeOn(AndroidSchedulers.mainThread())                .subscribeOn(Schedulers.io())                .subscribe(new Observer<ResultSong>() {                    @Override                    public void onSubscribe(Disposable d) {                        d.toString();                        progressLiveStatus.postValue(Constants.LOADING);                    }                    @Override                    public void onNext(ResultSong songs) {                        if(songs != null ) {                            Log.d("TAG", "getListSongs onNext: "+songs.getMsg());                            progressLiveStatus.postValue(Constants.LOADED);                            nextPage = firstPage + 1;                            callback.onResult(songs.getData(),nextPage);                        }                    }                    @Override                    public void onError(Throwable e) {                        Log.d("TAG", "getListSongs onError: "+e.toString());                        progressLiveStatus.postValue(Constants.LOADED);                    }                    @Override                    public void onComplete() {                        Log.e("TAG", "getListSongs onComplete");                    }                });    }}