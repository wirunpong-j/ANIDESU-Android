package com.bellkung.anidesu.controller;

import android.accounts.Account;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.model.AccountRegister;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AccountRegister.AccountRegisterListener {

    @BindView(R.id.login_button) LoginButton fbLoginBtn;
    @BindView(R.id.loginBtn) Button loginBtn;


    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private AccountRegister ar;

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

                            ar = new AccountRegister(user.getUid());
                            ar.setAbout("Welcome To AniDesu");
                            ar.setDisplay_name(user.getDisplayName());
                            ar.setEmail(user.getEmail());
                            ar.setImage_url_profile(user.getPhotoUrl().toString());
                            ar.setListener(MainActivity.this);
                            ar.isCurrentAccount();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Status : ", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showHomeActivity() {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
    }

    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @OnClick(R.id.logoutBtn)
    public void logoutBtnClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        Toast.makeText(MainActivity.this, "Logout!!!",
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCurrentAccount(boolean status) {
        if (status) {
            Toast.makeText(MainActivity.this, "onCurrentAccount is TRUE",
                    Toast.LENGTH_SHORT).show();
            showHomeActivity();

        } else {
            Toast.makeText(MainActivity.this, "onCurrentAccount is FALSE",
                    Toast.LENGTH_SHORT).show();
            if (this.ar.registerNewAccount()) {
                Toast.makeText(MainActivity.this, "Register Success",
                        Toast.LENGTH_SHORT).show();
                showHomeActivity();
            }
        }

    }
}
