package com.vk2.touchsreentab.api;

import com.vk2.touchsreentab.model.YouTubeApiObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("pxo/KTV/smartlist/getYoutubeApiKey?")
    Observable<YouTubeApiObject> getApiKeyTouTube(@Query("KTVId") String ktvId,
                                                  @Query("smartListId") String smartListId,
                                                  @Query("boxId") String boxId,
                                                  @Query("apiKey") String apiKey,
                                                  @Query("quota") int quota,
                                                  @Query("sign") String sign);
}