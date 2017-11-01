package com.bellkung.anidesu.controller;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.bellkung.anidesu.R;
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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.login_button) LoginButton fbLoginBtn;
    @BindView(R.id.loginBtn) Button loginBtn;


    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;

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
        showHomeActivity(currentUser);

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
                            showHomeActivity(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Status : ", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            showHomeActivity(null);
                        }
                    }
                });
    }

    private void showHomeActivity(final FirebaseUser currentUser) {
        if (currentUser != null) {
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference mUsersRef = mRootRef.child("users");

            if (!mUsersRef.equals(currentUser.getUid())) {
                mUsersRef.child(currentUser.getUid()).child("display_name").setValue(currentUser.getDisplayName());
                mUsersRef.child(currentUser.getUid()).child("about").setValue("Welcome to AniDesu");
                mUsersRef.child(currentUser.getUid()).child("email").setValue(currentUser.getEmail());
                mUsersRef.child(currentUser.getUid()).child("image_url_profile").setValue(currentUser.getPhotoUrl().toString());
            } else {
                mUsersRef.child(currentUser.getUid()).child("display_name").setValue("BiBiBoBo");
            }

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {

        }
    }

    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

}
