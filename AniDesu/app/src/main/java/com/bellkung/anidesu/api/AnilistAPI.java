package com.bellkung.anidesu.api;

import com.bellkung.anidesu.model.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by BellKunG on 18/10/2017 AD.
 */

public interface AnilistAPI {
    String BASE = "https://anilist.co/api/";
    String GRANT_TYPE = "client_credentials";
    String CLIENT_ID = "bbellkungdesu-3ajkf";
    String CLIENT_SECRET = "UPLGpMvOBczIaLM2HqCNVhVekDpt";

    @FormUrlEncoded
    @POST("auth/access_token")
    Call<AccessToken> postAccessToken(@Field("grant_type") String grant_type,
                                  @Field("client_id") String client_id,
                                  @Field("client_secret") String client_secret);
}
