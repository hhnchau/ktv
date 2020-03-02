package com.vk2.touchsreentab.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.download.FileTask;
import com.vk2.touchsreentab.model.YouTubeApiObject;
import com.vk2.touchsreentab.model.api.AlbumForm;
import com.vk2.touchsreentab.model.api.KeyForm;
import com.vk2.touchsreentab.model.api.SingerDetailForm;
import com.vk2.touchsreentab.model.api.SingerForm;
import com.vk2.touchsreentab.model.api.SongDetailForm;
import com.vk2.touchsreentab.model.api.SongForm;
import com.vk2.touchsreentab.model.api.Token;
import com.vk2.touchsreentab.model.api.TokenForm;
import com.vk2.touchsreentab.model.api.UrlForm;
import com.vk2.touchsreentab.model.api.VerDetailForm;
import com.vk2.touchsreentab.model.api.VerForm;
import com.vk2.touchsreentab.model.api.Youtube;
import com.vk2.touchsreentab.model.api.YoutubeForm;
import com.vk2.touchsreentab.utils.SaveDataUtils;
import com.vk2.touchsreentab.utils.Utils;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ApiController {
    public static final int SUCCESS = 0;
    public static final int EXPIRE = -102;
    public static final int TIMEOUT = -100;
    private static ApiController instance = null;

    public static ApiController getInstance() {
        if (instance == null) {
            instance = new ApiController();
        }
        return instance;
    }

    public static ApiService getDataOnline() {
        return Api.getInstance().create(Host.API_SOUNDCLOUD);
    }

    public static ApiService getAPIOnline() {
        return Api.getInstance().create(Host.API_VIETKTV);
    }

    private void getApiToken(final Context context, final FCallback callback) {
        CompositeManager.add(Api.apiService.getApiToken(Utils.getDeviceId())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TokenForm>() {

                    @Override
                    public void onNext(TokenForm tokenForm) {
                        if (tokenForm != null && tokenForm.getErr() == SUCCESS) {
                            Token token = tokenForm.getData();
                            if (token != null) {
                                SaveDataUtils.getInstance(context).setApiToken(token.getToken());
                                if (callback != null) callback.reCall();
                            } else {
                                Log.d("API Token: ", "null");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getHotSongs(final Context context, final int page, final int limit, final Callback callback) {
        CompositeManager.add(Api.apiService.getHostSongs(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), page, limit)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SongForm>() {

                    @Override
                    public void onNext(SongForm songForm) {
                        if (songForm != null && (songForm.getErr() == EXPIRE || songForm.getErr() == TIMEOUT)) {
                            Log.d("API-Hot-Song: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getHotSongs(context, page, limit, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API-Hot-Song: ", "Success!");
                        if (callback != null) callback.response(songForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getListSongs(final Context context, final int page, final int limit, final Callback callback) {
        CompositeManager.add(Api.apiService.getListSongs(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), page, limit)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SongForm>() {

                    @Override
                    public void onNext(SongForm songForm) {
                        if (songForm != null && (songForm.getErr() == EXPIRE || songForm.getErr() == TIMEOUT)) {
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getListSongs(context, page, limit, callback);
                                }
                            });
                            return;
                        }
                        if (callback != null) callback.response(songForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getHotSingers(final Context context, final int page, final int limit, final Callback callback) {
        CompositeManager.add(Api.apiService.getHostSinger(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), page, limit)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SingerForm>() {

                    @Override
                    public void onNext(SingerForm singerForm) {
                        if (singerForm != null && (singerForm.getErr() == EXPIRE || singerForm.getErr() == TIMEOUT)) {
                            Log.d("API-Hot-Singer: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getHotSingers(context, page, limit, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API-Hot-Singer: ", "Success!");
                        if (callback != null) callback.response(singerForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getHotAlbums(final Context context, final int page, final int limit, final Callback callback) {
        CompositeManager.add(Api.apiService.getListAlbums(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), page, limit)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AlbumForm>() {

                    @Override
                    public void onNext(AlbumForm albumForm) {
                        if (albumForm != null && (albumForm.getErr() == EXPIRE || albumForm.getErr() == TIMEOUT)) {
                            Log.d("API-Hot-Album: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getHotAlbums(context, page, limit, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API-Hot-Album: ", "Success!");
                        if (callback != null) callback.response(albumForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getYoutubeKey(final Context context, final Callback callback) {
        CompositeManager.add(Api.apiService.getYoutubeKey(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<KeyForm>() {

                    @Override
                    public void onNext(KeyForm keyForm) {
                        if (keyForm != null && (keyForm.getErr() == EXPIRE || keyForm.getErr() == TIMEOUT)) {
                            Log.d("API GetYoutubeKey: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getYoutubeKey(context, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API GetYoutubeKey: ", "Success!");
                        if (callback != null) callback.response(keyForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getSoundCloudKey(final Context context, final Callback callback) {
        CompositeManager.add(Api.apiService.getSoundCloudKey(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<KeyForm>() {

                    @Override
                    public void onNext(KeyForm keyForm) {
                        if (keyForm != null && (keyForm.getErr() == EXPIRE || keyForm.getErr() == TIMEOUT)) {
                            Log.d("API GetSoundCloudKey: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getSoundCloudKey(context, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API GetSoundCloudKey: ", "Success!");
                        if (callback != null) callback.response(keyForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void getLinkYoutube(@NonNull final Song song) {
        CompositeManager.add(Api.apiService.getLinkYoutube(Host.API_LINK_YOUTUBE, song.getVideoPath())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<YoutubeForm>() {

                    @Override
                    public void onNext(YoutubeForm youtubeForm) {
                        if (youtubeForm != null) {
                            Log.d("API LINK YT: ", "Success!");
                            Youtube yt = youtubeForm.getData();
                            if (yt != null) {
                                Log.d("API LINK YT: ", "Update Link Success!");
                                //song.setFileName(song.getFileName().substring(1));
                                song.setVideoPath(yt.getLink());
                                song.setAudioPath(yt.getAudio());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getUrlSinger(final Context context, final String fileId, final Callback callback) {
        CompositeManager.add(Api.apiService.getUrlSinger(fileId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UrlForm>() {

                    @Override
                    public void onNext(UrlForm urlForm) {
                        if (urlForm != null && (urlForm.getErr() == EXPIRE || urlForm.getErr() == TIMEOUT)) {
                            Log.d("API GetSoundCloudKey: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getUrlSinger(context, fileId, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API GetSoundCloudKey: ", "Success!");
                        if (callback != null) callback.response(urlForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getLatestTableSong(final Context context, final Callback callback) {
        CompositeManager.add(Api.apiService.getLatestTableSong(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UrlForm>() {

                    @Override
                    public void onNext(UrlForm urlForm) {
                        if (urlForm != null && (urlForm.getErr() == EXPIRE || urlForm.getErr() == TIMEOUT)) {
                            Log.d("API LatestTableSong: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getLatestTableSong(context, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API LatestTableSong: ", "Success!");
                        if (callback != null) callback.response(urlForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void downloadDB(String url, final Callback callback) {
        CompositeManager.add(Api.apiService.downloadDB(url)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse != null) {
                            if (callback != null) callback.response(responseBodyResponse.body());
                        } else if (callback != null) callback.response(null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getListVer(final Context context, final String lastUpdateVer, final Callback callback) {
        CompositeManager.add(Api.apiService.getListVer(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), lastUpdateVer)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<VerForm>() {

                    @Override
                    public void onNext(VerForm verForm) {
                        if (verForm != null && (verForm.getErr() == EXPIRE || verForm.getErr() == TIMEOUT)) {
                            Log.d("API getListVer: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getListVer(context,lastUpdateVer, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API getListVer: ", "Success!");
                        if (callback != null) callback.response(verForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getByVer(final Context context, final String ver, final Callback callback) {
        CompositeManager.add(Api.apiService.getByVer(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), ver)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<VerDetailForm>() {

                    @Override
                    public void onNext(VerDetailForm verDetailForm) {
                        if (verDetailForm != null && (verDetailForm.getErr() == EXPIRE || verDetailForm.getErr() == TIMEOUT)) {
                            Log.d("API getByVer: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getByVer(context, ver, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API getByVer: ", "Success!");
                        if (callback != null) callback.response(verDetailForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getSongById(final Context context, final int fileName, final Callback callback){
        CompositeManager.add(Api.apiService.getSongById(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), fileName)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SongDetailForm>() {

                    @Override
                    public void onNext(SongDetailForm songDetailForm) {
                        if (songDetailForm != null && (songDetailForm.getErr() == EXPIRE || songDetailForm.getErr() == TIMEOUT)) {
                            Log.d("API getSongById: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getSongById(context, fileName, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API getSongById: ", "Success!");
                        if (callback != null) callback.response(songDetailForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getSingerById(final Context context, final int singerId, final Callback callback){
        CompositeManager.add(Api.apiService.getSingerById(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), singerId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SingerDetailForm>() {

                    @Override
                    public void onNext(SingerDetailForm singerDetailForm) {
                        if (singerDetailForm != null && (singerDetailForm.getErr() == EXPIRE || singerDetailForm.getErr() == TIMEOUT)) {
                            Log.d("API getSingerById: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getSingerById(context, singerId, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API getSingerById: ", "Success!");
                        if (callback != null) callback.response(singerDetailForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void getSongDownload(final Context context, final int fileName, final Callback callback){
        CompositeManager.add(Api.apiService.getDownloadSong(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), fileName)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UrlForm>() {

                    @Override
                    public void onNext(UrlForm urlForm) {
                        if (urlForm != null && (urlForm.getErr() == EXPIRE || urlForm.getErr() == TIMEOUT)) {
                            Log.d("API getSongDownload: ", "Expire!");
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getSongDownload(context, fileName, callback);
                                }
                            });
                            return;
                        }
                        Log.d("API getSongDownload: ", "Success!");
                        if (callback != null) callback.response(urlForm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @SuppressWarnings("unchecked")
    public void downloadSong(String url, final Callback callback){
        CompositeManager.add(Api.apiService.downloadSong(url)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ResponseBody>>() {

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse != null) {
                            if (callback != null) callback.response(responseBodyResponse.body());
                        } else if (callback != null) callback.response(null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API Error: ", e.toString());
                        if (callback != null) callback.response(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }









    public void getKeyApiYouTube(final Context context, int quota) {

        String ktvId = "98851";
        String smartListId = "sl6a9s72";
        String boxId = "px06u1e7";
        String apiKey = SaveDataUtils.getInstance(context).getKeyYouTube();
        String sign = "8da2cb53bf6ea1a80fb85a7bab436fea";
        ApiController.getDataOnline()
                .getApiKeyTouTube(ktvId, smartListId, boxId, apiKey, quota, sign)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<YouTubeApiObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // DBLog.e(TAG, "getKeyApiYouTube onSubscribe");
                    }

                    @Override
                    public void onNext(YouTubeApiObject youTubeObject) {

                        String youTubeApi = youTubeObject.getData();
                        if (!TextUtils.isEmpty(youTubeApi)) {
                            Log.e("nhu", "getKeyApiYouTube onNext: " + youTubeApi);
                            SaveDataUtils.getInstance(context).setKeyYouTube(youTubeApi);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("", "getKeyApiYouTube onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("", "getKeyApiYouTube onComplete");
                    }
                });
    }


    public static boolean checkInternet(Context mContext) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}