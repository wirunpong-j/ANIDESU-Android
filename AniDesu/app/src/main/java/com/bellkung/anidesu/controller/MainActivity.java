package com.bellkung.anidesu.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.ApiManager;
import com.bellkung.anidesu.model.AccessToken;
import com.bellkung.anidesu.model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AccessToken accessToken;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Showwwwwwww", Toast.LENGTH_SHORT).show();

        if (savedInstanceState == null) {
            this.accessToken = new AccessToken();
            this.user = new User();
            Toast.makeText(MainActivity.this, "savedInstanceState is null", Toast.LENGTH_SHORT).show();
        }
        strictMode();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(ApiManager.REDIRECT_URI)) {
            final String code = uri.getQueryParameter("code");
            Call<AccessToken> call = ApiManager.getApiManagerInstance().getAnilistAPI().postAccessToken(ApiManager.GRANT_TYPE,
                    ApiManager.CLIENT_ID,
                    ApiManager.CLIENT_SECRET,
                    ApiManager.REDIRECT_URI,
                    code);

            try {
                Response<AccessToken> response = call.execute();
                if (response.isSuccessful()) {
                    this.accessToken = response.body();
                    Toast.makeText(MainActivity.this, this.accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
                    getUserProfileFromAPI();

                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUserProfileFromAPI() {
        Call<User> call = ApiManager.getApiManagerInstance().getAnilistAPI().getUserProfile("Bearer " + this.accessToken.getAccess_token());
        try {
            Response<User> response = call.execute();
            if (response.isSuccessful()) {
                this.user = response.body();
            } else {
                Toast.makeText(MainActivity.this, "Get Profile Failed", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Get Profile Error", Toast.LENGTH_SHORT).show();
        }

    }

    private void showHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("name", this.user.getDisplay_name());
        startActivity(intent);
        finish();
    }

    public void anilistLoginBtnPressed(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiManager.BASE+ "auth/authorize?" +
                "grant_type=" + ApiManager.GRANT_TYPE +
                "&client_id=" + ApiManager.CLIENT_ID +
                "&redirect_uri=" + ApiManager.REDIRECT_URI +
                "&response_type=" + ApiManager.RESPONSE_TYPE));
        startActivity(intent);
    }

    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

}
