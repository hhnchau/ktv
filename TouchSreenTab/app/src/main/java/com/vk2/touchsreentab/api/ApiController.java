package com.vk2.touchsreentab.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.vk2.touchsreentab.model.YouTubeApiObject;
import com.vk2.touchsreentab.model.api.SingerForm;
import com.vk2.touchsreentab.model.api.SongForm;
import com.vk2.touchsreentab.model.api.Token;
import com.vk2.touchsreentab.model.api.TokenForm;
import com.vk2.touchsreentab.utils.SaveDataUtils;
import com.vk2.touchsreentab.utils.Utils;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ApiController {
    public static final int SUCCESS = 0;
    public static final int EXPIRE = -100;
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
                        if (songForm != null && songForm.getErr() == EXPIRE) {
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getHotSongs(context, page, limit, callback);
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
    public void getListSongs(final Context context, final int page, final int limit, final Callback callback) {
        CompositeManager.add(Api.apiService.getListSongs(SaveDataUtils.getInstance(context).getApiToken(), Utils.getDeviceId(), page, limit)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SongForm>() {

                    @Override
                    public void onNext(SongForm songForm) {
                        if (songForm != null && songForm.getErr() == EXPIRE) {
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
                        if (singerForm != null && singerForm.getErr() == EXPIRE) {
                            getApiToken(context, new FCallback() {
                                @Override
                                public void reCall() {
                                    getHotSongs(context, page, limit, callback);
                                }
                            });
                            return;
                        }
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