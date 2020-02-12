package com.vk2.touchsreentab.api;

import com.vk2.touchsreentab.model.ResultAlbum;
import com.vk2.touchsreentab.model.ResultSinger;
import com.vk2.touchsreentab.model.ResultSong;
import com.vk2.touchsreentab.model.ResultSoundCloud;
import com.vk2.touchsreentab.model.YouTubeApiObject;
import com.vk2.touchsreentab.model.api.AlbumForm;
import com.vk2.touchsreentab.model.api.SingerForm;
import com.vk2.touchsreentab.model.api.SongForm;
import com.vk2.touchsreentab.model.api.TokenForm;
import com.vk2.touchsreentab.model.api.YoutubeForm;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @FormUrlEncoded
    @POST("/token")
    Observable<TokenForm> getApiToken(@Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST("/media/song/get-hot-songs")
    Observable<SongForm> getHostSongs(@Header("Authorization") String authorization, @Field("deviceId") String deviceId, @Field("page") int page, @Field("limit") int limit);

    @FormUrlEncoded
    @POST("/media/song/get-list-song")
    Observable<SongForm> getListSongs(@Header("Authorization") String authorization, @Field("deviceId") String deviceId, @Field("page") int page, @Field("limit") int limit);

    @FormUrlEncoded
    @POST("/media/singer/get-hot-singers")
    Observable<SingerForm> getHostSinger(@Header("Authorization") String authorization, @Field("deviceId") String deviceId, @Field("page") int page, @Field("limit") int limit);

    @FormUrlEncoded
    @POST("media/album/get-list-album")
    Observable<AlbumForm> getListAlbums(@Header("Authorization") String authorization, @Field("deviceId") String deviceId, @Field("page") int page, @Field("limit") int limit);



    @GET()
    Observable<YoutubeForm> getLinkYoutube(@Url String url, @Query("link") String link);

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
    Observable<ResultSong> getListSongs(@Query("page") int page, @Query("limit") int limit);

    @GET("media/singer/getHotSingers")
    Observable<ResultSinger> getListSingers(@Query("page") int page, @Query("limit") int limit);


}
