package com.bellkung.anidesu.controller;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bellkung.anidesu.adapter.AnimeListOverviewPagerAdapter;
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

public class AnimeListActivity extends AppCompatActivity {

    @BindView(R.id.anime_list_overview_pager) ViewPager mOverviewPager;
    @BindView(R.id.anime_list_tab) SmartTabLayout mTabStrip;
    @BindView(R.id.anime_list_cover_image) ImageView mBannerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_list);
        ButterKnife.bind(this);

        initializeUI();

    }

    private void initializeUI() {
        AnimeListOverviewPagerAdapter adapter = new AnimeListOverviewPagerAdapter(getSupportFragmentManager());
        this.mOverviewPager.setAdapter(adapter);
        this.mTabStrip.setViewPager(this.mOverviewPager);

        Series series = getIntent().getParcelableExtra(KeyUtils.KEY_SERIES);
        if (series.getImage_url_banner() == null) {
            Glide.with(this).load(series.getImage_url_lge()).into(this.mBannerImage);
        } else {
            Glide.with(this).load(series.getImage_url_banner()).into(this.mBannerImage);
        }

    }


    @OnClick(R.id.back_btn)
    public void backBtnPressed() {
        finish();
    }

}
