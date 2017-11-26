package com.bellkung.anidesu.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by BellKunG on 4/11/2017 AD.
 */

public interface OnNetworkCallbackListener {
    void onResponse(String action, Call call, Response response);
    void onBodyError(ResponseBody responseBodyError);
    void onBodyErrorIsNull();
    void onFailure(Throwable t);
}
