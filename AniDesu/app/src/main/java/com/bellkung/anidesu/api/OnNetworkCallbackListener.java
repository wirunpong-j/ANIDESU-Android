package com.bellkung.anidesu.api;

import com.bellkung.anidesu.api.model.Token;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by BellKunG on 4/11/2017 AD.
 */

public interface OnNetworkCallbackListener {
    void onResponse(Token token, Retrofit retrofit);
    void onBodyError(ResponseBody responseBodyError);
    void onBodyErrorIsNull();
    void onFailure(Throwable t);
}