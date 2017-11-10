package com.bellkung.anidesu.controller;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bellkung.anidesu.adapter.AnimeListOverviewPagerAdapter;
import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.fragment.AnimeListInfoFragment;
import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AnimeListActivity extends AppCompatActivity implements OnNetworkCallbackListener {

    @BindView(R.id.anime_list_overview_pager) ViewPager mOverviewPager;
    @BindView(R.id.anime_list_tab) SmartTabLayout mTabStrip;
    @BindView(R.id.anime_list_cover_image) ImageView mBannerImage;

    private Series thisSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_list);
        ButterKnife.bind(this);

        Series series = getIntent().getParcelableExtra(KeyUtils.KEY_SERIES);
        new NetworkConnectionManager().fetchThisSeriesData(this, series.getId());

    }

    private void initializeUI() {
        AnimeListOverviewPagerAdapter adapter = new AnimeListOverviewPagerAdapter(getSupportFragmentManager());
        adapter.setSeries(this.thisSeries);
        this.mOverviewPager.setAdapter(adapter);
        this.mTabStrip.setViewPager(this.mOverviewPager);

        Glide.with(this).load(this.thisSeries.getImage_url_banner()).into(this.mBannerImage);

    }


    @OnClick(R.id.back_btn)
    public void backBtnPressed() {
        finish();
    }

    @Override
    public void onResponse(String action, Call call, Response response) {
        switch (action) {
            case ApiConfig.FETCH_THIS_SERIES:
                this.thisSeries = (Series) response.body();
                initializeUI();
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
        Log.i("Status", "onBodyErrorIsNull");
    }
}
