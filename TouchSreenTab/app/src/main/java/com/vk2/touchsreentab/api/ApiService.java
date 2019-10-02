package com.vk2.touchsreentab.api;

import com.vk2.touchsreentab.model.ResultSinger;
import com.vk2.touchsreentab.model.ResultSong;
import com.vk2.touchsreentab.model.ResultSoundCloud;
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

    @GET("tracks?")
    Observable<ResultSoundCloud> searchSongs(@Query("q") String query,
                                             @Query("client_id") String client_id,
                                             @Query("limit") String limit,
                                             @Query("linked_partitioning") String linked_partitioning,
                                             @Query("offset") String offset);

    @GET("media/song/getHotSongs")
    Observable<ResultSong> getListSongs();

    @GET("media/singer/getHotSingers")
    Observable<ResultSinger> getListSingers();
}
