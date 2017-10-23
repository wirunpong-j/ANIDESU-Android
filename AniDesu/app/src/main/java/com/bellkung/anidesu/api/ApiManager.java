package com.bellkung.anidesu.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BellKunG on 23/10/2017 AD.
 */

public class ApiManager {
    private static ApiManager apiManagerInstance;
    private AnilistAPI anilistAPI;

    public static final String BASE = "https://anilist.co/api/";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String CLIENT_ID = "bbellkungdesu-vstku";
    public static final String CLIENT_SECRET = "L5gVawdnzKUYaRWD3WioXZRq0rz";
    public static final String REDIRECT_URI = "anidesu://callback";
    public static final String RESPONSE_TYPE = "code";

    public static ApiManager getApiManagerInstance() {
        if (apiManagerInstance == null) {
            apiManagerInstance = new ApiManager();
        }
        return apiManagerInstance;
    }

    private ApiManager() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.anilistAPI = retrofit.create(AnilistAPI.class);
    }

    public AnilistAPI getAnilistAPI() {
        return anilistAPI;
    }
}
