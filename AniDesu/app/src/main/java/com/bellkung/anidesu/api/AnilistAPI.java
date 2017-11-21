package com.bellkung.anidesu.api;

import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.api.model.Token;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by BellKunG on 18/10/2017 AD.
 */

public interface AnilistAPI {

    @FormUrlEncoded
    @POST("auth/access_token")
    Call<Token> postAccessToken(@Field("grant_type") String grant_type,
                                       @Field("client_id") String client_id,
                                       @Field("client_secret") String client_secret);

    @GET("browse/{series_type}")
    Call<ArrayList<Series>> fetchSeriesPages(@Header("Authorization") String authToken,
                                        @Path("series_type") String series_type,
                                        @Query("year") int year,
                                        @Query("season") String season,
                                        @Query("full_page") boolean full_page,
                                        @Query("airing_data") boolean airing_data);

    @GET("{series_type}/{id}/page")
    Call<Series> fetchThisSeriesData(@Header("Authorization") String authToken,
                                     @Path("series_type") String series_type,
                                     @Path("id") int id);


}
