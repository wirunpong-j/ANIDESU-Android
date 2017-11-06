package com.bellkung.anidesu.controller;

import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bellkung.anidesu.AnimeListInfoFragment;
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

    @BindView(R.id.anime_list_overview_pager) ViewPager anime_list_overview_pager;
    @BindView(R.id.anime_list_tab) SmartTabLayout anime_list_tab;
    @BindView(R.id.anime_list_cover_image) ImageView anime_list_cover_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_list);
        ButterKnife.bind(this);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(KeyUtils.OVERVIEWS[0], AnimeListInfoFragment.class)
                .add(KeyUtils.OVERVIEWS[1], AnimeListInfoFragment.class)
                .add(KeyUtils.OVERVIEWS[2], AnimeListInfoFragment.class)
                .add(KeyUtils.OVERVIEWS[3], AnimeListInfoFragment.class)
                .add(KeyUtils.OVERVIEWS[4], AnimeListInfoFragment.class)
                .create());

        anime_list_overview_pager.setAdapter(adapter);
        anime_list_tab.setViewPager(anime_list_overview_pager);

        initializeUI();


    }

    private void initializeUI() {
        Series series = getIntent().getParcelableExtra("series");
        if (series.getImage_url_banner() == null) {
            Glide.with(this).load(series.getImage_url_lge()).into(this.anime_list_cover_image);
        } else {
            Glide.with(this).load(series.getImage_url_banner()).into(this.anime_list_cover_image);
        }

    }


    @OnClick(R.id.back_btn)
    public void backBtnPressed() {
        finish();
    }

}
