package com.bellkung.anidesu.api;

import com.bellkung.anidesu.api.model.Token;

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

                    listener.onResponse(token, retrofit);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }
}
