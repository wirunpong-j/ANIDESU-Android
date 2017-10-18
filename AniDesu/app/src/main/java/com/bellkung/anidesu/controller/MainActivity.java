package com.bellkung.anidesu.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.AnilistAPI;
import com.bellkung.anidesu.model.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAccessToken();
    }

    private void getAccessToken() {
        Retrofit retrofit = getRetrofitBuild();
        Log.v("Status : ", "In!!!!!!!!!!!");

        AnilistAPI anilistAPI = retrofit.create(AnilistAPI.class);
        Log.v("Status : ", "In2!!!!!!!!!!!");
        Call<AccessToken> call = anilistAPI.postAccessToken(AnilistAPI.GRANT_TYPE,
                AnilistAPI.CLIENT_ID,
                AnilistAPI.CLIENT_SECRET);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()){
                    Log.v("Status : ", "Success");
                    Log.v("Status : ", response.body().getAccess_token());
                    Log.v("Status : ", response.body().getToken_type());
                    Log.v("Status : ", String.valueOf(response.body().getExpires()));
                    Log.v("Status : ", String.valueOf(response.body().getExpires_in()));
                } else {
                    Log.v("Status : ", "Failed");
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.v("Status : ", t.getMessage());
            }
        });
    }

    private Retrofit getRetrofitBuild() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return new Retrofit
                .Builder()
                .client(client)
                .baseUrl(AnilistAPI.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
