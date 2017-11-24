package com.bellkung.anidesu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.fragment.AnimeListExtrasFragment;
import com.bellkung.anidesu.fragment.AnimeListInfoFragment;
import com.bellkung.anidesu.fragment.AnimeListReviewsFragment;
import com.bellkung.anidesu.fragment.AnimeListStatsFragment;
import com.bellkung.anidesu.utils.KeyUtils;

/**
 * Created by BellKunG on 6/11/2017 AD.
 */

public class AnimeListOverviewPagerAdapter extends FragmentPagerAdapter {

    private Series series;

    private final int ANIME_OVERVIEWS_PAGER = 4;

    public AnimeListOverviewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return AnimeListInfoFragment.newInstance(this.series);
            case 1:
                return AnimeListStatsFragment.newInstance(this.series);
            case 2:
                return AnimeListExtrasFragment.newInstance(this.series);
            case 3:
                return AnimeListReviewsFragment.newInstance();
        }
        return null;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    @Override
    public int getCount() {
        return ANIME_OVERVIEWS_PAGER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return KeyUtils.OVERVIEWS[position];
    }
}
