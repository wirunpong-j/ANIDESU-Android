package com.bellkung.anidesu.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bellkung.anidesu.adapter.viewpager.AnimeListPagerAdapter;
import com.bellkung.anidesu.adapter.viewpager.AnimeReviewPagerAdapter;
import com.bellkung.anidesu.adapter.viewpager.MyAnimeListPagerAdapter;
import com.bellkung.anidesu.adapter.viewpager.PostsPagerAdapter;
import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.api.model.Token;
import com.bellkung.anidesu.R;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import top.wefor.circularanim.CircularAnim;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, User.UserDataListener,
        MaterialSearchBar.OnSearchActionListener, OnNetworkCallbackListener {

    private FirebaseAuth mAuth;

    @BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.searchBar) MaterialSearchBar mSearchBar;
    @BindView(R.id.anime_list_pager) ViewPager mAnimePager;
    @BindView(R.id.nts_center) SmartTabLayout mSmartTabStrip;

    private static DrawerLayout mDrawer;
    private static ConstraintLayout mLoadingView;
    private static AVLoadingIndicatorView mAvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        this.mDrawer = findViewById(R.id.drawer_layout);
        this.mLoadingView = findViewById(R.id.loadingView);
        this.mAvi = findViewById(R.id.avi);

        showLoadingView();

        this.mNavigationView.setNavigationItemSelectedListener(this);

        this.mSearchBar.setOnSearchActionListener(this);
        this.mSearchBar.inflateMenu(R.menu.activity_home_drawer);
        this.mSearchBar.setCardViewElevation(10);
        this.mSearchBar.setPlaceHolder(getString(R.string.nav_search));

        this.mAuth = FirebaseAuth.getInstance();
        this.mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbUser = firebaseAuth.getCurrentUser();
                if (fbUser != null) {
                    User.getInstance().setUid(fbUser.getUid());
                    User.getInstance().setListener(HomeActivity.this);
                    User.getInstance().fetchUserProfile();
                    User.getInstance().fetchMyAnimeList();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

//        FirebaseAuth.getInstance().signOut();
    }

    private void updateUI() {
        if (User.getInstance() != null) {

            // Access Token
            new NetworkConnectionManager().callServer(this);

            TextView fullnameTextView = this.mNavigationView.getHeaderView(0).findViewById(R.id.fullnameTextView);
            TextView emailTextView = this.mNavigationView.getHeaderView(0).findViewById(R.id.emailTextView);
            CircleImageView profileImage = this.mNavigationView.getHeaderView(0).findViewById(R.id.profileImage);

            fullnameTextView.setText(User.getInstance().getDisplay_name());
            emailTextView.setText(User.getInstance().getEmail());
            Glide.with(getApplicationContext()).load(User.getInstance().getImage_url_profile()).into(profileImage);
        }

    }

    private void Display(int id) {

        switch(id) {
            case R.id.nav_home:
                PostsPagerAdapter postsPagerAdapter = new PostsPagerAdapter(getSupportFragmentManager(), this);
                this.mAnimePager.setAdapter(postsPagerAdapter);
                this.mSmartTabStrip.setViewPager(this.mAnimePager);
                this.mNavigationView.setCheckedItem(R.id.nav_home);
                break;

            case R.id.nav_discover:
                AnimeListPagerAdapter animeListPagerAdapter = new AnimeListPagerAdapter(getSupportFragmentManager(), this);
                this.mAnimePager.setAdapter(animeListPagerAdapter);
                this.mSmartTabStrip.setViewPager(this.mAnimePager);
                this.mNavigationView.setCheckedItem(R.id.nav_discover);
                break;

            case R.id.nav_anime_list:
                MyAnimeListPagerAdapter myAnimeListPagerAdapter = new MyAnimeListPagerAdapter(getSupportFragmentManager(), this);
                this.mAnimePager.setAdapter(myAnimeListPagerAdapter);
                this.mSmartTabStrip.setViewPager(this.mAnimePager);
                this.mNavigationView.setCheckedItem(R.id.nav_anime_list);
                break;

            case R.id.nav_anime_review:
                AnimeReviewPagerAdapter animeReviewPagerAdapter = new AnimeReviewPagerAdapter(getSupportFragmentManager(), this);
                this.mAnimePager.setAdapter(animeReviewPagerAdapter);
                this.mSmartTabStrip.setViewPager(this.mAnimePager);
                this.mNavigationView.setCheckedItem(R.id.nav_anime_review);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                break;
        }

    }


    // User.UserDataListener : Change when login is success.
    @Override
    public void onDataChanged() {
        updateUI();
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Display(item.getItemId());

        this.mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        final String mSearchText = String.valueOf(text);
        CircularAnim.fullActivity(this, this.mSearchBar)
                .colorOrImageRes(R.color.colorDarkKnight)
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        Intent intent = new Intent(HomeActivity.this, AnimeSearchActivity.class);
                        intent.putExtra(KeyUtils.KEY_SEARCH_TEXT, mSearchText);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                this.mDrawer.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                this.mSearchBar.disableSearch();
                break;
        }
    }

    @Override
    public void onResponse(String action, Call call, Response response) {
        switch(action) {
            case ApiConfig.ACCESS_TOKEN:
                onNavigationItemSelected(this.mNavigationView.getMenu().getItem(0));
                Token token = (Token) response.body();
                Toast.makeText(this, token.getAccess_token(),
                        Toast.LENGTH_SHORT).show();
                hideLoadingView();
                break;
        }
    }

    @Override
    public void onBodyError(ResponseBody responseBodyError) {
        Log.i("Status", "onBodyError");
    }

    @Override
    public void onBodyErrorIsNull() {
        Log.i("Status", "onBodyErrorIsNull");
    }

    @Override
    public void onFailure(Throwable t) {
        Log.i("Status", "onFailure");
    }

    public void showLoadingView() {
        mAvi.show();
        mLoadingView.setVisibility(View.VISIBLE);
        mDrawer.setClickable(false);
    }

    public void hideLoadingView() {
        mLoadingView.setVisibility(View.INVISIBLE);
        mDrawer.setClickable(true);
    }

}
