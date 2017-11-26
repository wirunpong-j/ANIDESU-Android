package com.bellkung.anidesu.controller;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.service.AccountService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AccountService.CurrentAccountListener {

    @BindView(R.id.login_button) LoginButton fbLoginBtn;
    @BindView(R.id.main_loadingView) ConstraintLayout main_loadingView;
    @BindView(R.id.main_avi) AVLoadingIndicatorView main_avi;
    @BindView(R.id.mainActivityView) ConstraintLayout mainActivityView;

    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private final AccountService accountService = new AccountService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mCallbackManager = CallbackManager.Factory.create();
        fbLoginBtn.setReadPermissions("email", "public_profile");
        fbLoginBtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showIndicatorView();
                Log.d("Status : ", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Status : ", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Status : ", "facebook:onError", error);
            }
        });

        this.mAuth = FirebaseAuth.getInstance();
        if (this.mAuth.getCurrentUser() != null) {
            Log.i("Status", this.mAuth.getCurrentUser().getDisplayName());
        } else {
            LoginManager.getInstance().logOut();
            Log.i("Status", "Null");
        }

        strictMode();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("Status : ", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        this.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Status : ", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            AnotherUser anotherUser = new AnotherUser();
                            anotherUser.setUid(user.getUid());
                            anotherUser.setAbout("Welcome To AniDesu");
                            anotherUser.setDisplay_name(user.getDisplayName());
                            anotherUser.setEmail(user.getEmail());
                            anotherUser.setImage_url_profile(user.getPhotoUrl().toString());

                            accountService.setCurrentAccountListener(MainActivity.this);
                            accountService.isCurrentAccount(anotherUser);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Status : ", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void showHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        hideIndicatorView();
        finish();
    }

    @Override
    public void onCurrentAccount() {
        showHomeActivity();
    }

    @Override
    public void onFailed(String errorText) {
        Log.i("Status : ", "onFailed" + errorText);
    }

    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            showHomeActivity();
        }
    }

    private void showIndicatorView() {
        this.main_avi.show();
        this.main_loadingView.setVisibility(View.VISIBLE);
        this.mainActivityView.setClickable(false);
    }

    private void hideIndicatorView() {
        this.main_loadingView.setVisibility(View.INVISIBLE);
        this.mainActivityView.setClickable(true);
    }

}
