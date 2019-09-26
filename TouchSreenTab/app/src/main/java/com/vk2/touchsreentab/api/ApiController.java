package com.vk2.touchsreentab.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.vk2.touchsreentab.model.YouTubeApiObject;
import com.vk2.touchsreentab.utils.SaveDataUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiController {
    private static ApiController instance = null;

    public static ApiController getInstance() {
        if (instance == null) {
            instance = new ApiController();
        }
        return instance;
    }

    public void getKeyApiYouTube(final Context context, int quota) {

        String ktvId = "98851";
        String smartListId = "sl6a9s72";
        String boxId = "px06u1e7";
        String apiKey = SaveDataUtils.getInstance(context).getKeyYouTube();
        String sign = "8da2cb53bf6ea1a80fb85a7bab436fea";
        DataUtils.getDataOnline()
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
}