package com.bellkung.anidesu.api;

import android.util.Log;

import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.api.model.Token;
import com.bellkung.anidesu.controller.HomeActivity;
import com.bellkung.anidesu.utils.KeyUtils;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BellKunG on 4/11/2017 AD.
 */

public class NetworkConnectionManager {

    public static Token currentToken;

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiConfig.BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void callServer(final OnNetworkCallbackListener listener) {
        AnilistAPI anilistAPI = retrofit.create(AnilistAPI.class);
        Call call = anilistAPI.postAccessToken(ApiConfig.GRANT_TYPE, ApiConfig.CLIENT_ID, ApiConfig.CLIENT_SECRET);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();

                if (token == null) {
                    //404 or the response cannot be converted to User.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    currentToken = response.body();
                    listener.onResponse(ApiConfig.ACCESS_TOKEN, call, response);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public void fetchAnimeList(final OnNetworkCallbackListener listener, String season) {
        if (this.currentToken == null) {
            callServer(listener);
        } else {
            AnilistAPI anilistAPI = retrofit.create(AnilistAPI.class);
            Call call = anilistAPI.fetchSeriesPages(this.currentToken.getHeaderValuePresets(),
                    KeyUtils.ANIME_TYPE, KeyUtils.YEAR, season, KeyUtils.FULL_PAGE, KeyUtils.AIRING_DATA);
            call.enqueue(new Callback<ArrayList<Series>>() {
                @Override
                public void onResponse(Call<ArrayList<Series>> call, Response<ArrayList<Series>> response) {
                    ArrayList<Series> allSeries = response.body();

                    if (allSeries == null) {
                        ResponseBody responseBody = response.errorBody();
                        if (responseBody != null) {
                            listener.onBodyError(responseBody);
                        } else {
                            listener.onBodyErrorIsNull();
                        }
                    } else {
                        listener.onResponse(ApiConfig.FETCH_ANIME_LIST, call, response);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Series>> call, Throwable t) {
                    listener.onFailure(t);
                }
            });

        }

    }

    public void fetchThisSeriesData(final OnNetworkCallbackListener listener, int id, final String status) {
        if (this.currentToken == null) {
            callServer(listener);
        } else {
            AnilistAPI anilistAPI = retrofit.create(AnilistAPI.class);
            Call call = anilistAPI.fetchThisSeriesData(this.currentToken.getHeaderValuePresets(), KeyUtils.ANIME_TYPE, id);
            call.enqueue(new Callback<Series>() {
                @Override
                public void onResponse(Call<Series> call, Response<Series> response) {
                    Series series = response.body();
                    if (series == null) {
                        ResponseBody responseBody = response.errorBody();
                        if (responseBody != null) {
                            listener.onBodyError(responseBody);
                        } else {
                            listener.onBodyErrorIsNull();
                        }
                    } else {
                        listener.onResponse(status, call, response);
                    }
                }

                @Override
                public void onFailure(Call<Series> call, Throwable t) {
                    listener.onFailure(t);
                }
            });
        }
    }
}
