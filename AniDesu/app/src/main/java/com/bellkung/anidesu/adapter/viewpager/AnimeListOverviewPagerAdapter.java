package com.bellkung.anidesu.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.fragment.AnimeListExtrasFragment;
import com.bellkung.anidesu.fragment.AnimeListInfoFragment;
import com.bellkung.anidesu.fragment.AnimeListReviewsFragment;
import com.bellkung.anidesu.fragment.AnimeListStatsFragment;
import com.bellkung.anidesu.model.DefaultStatePagerAdapter;
import com.bellkung.anidesu.utils.KeyUtils;

/**
 * Created by BellKunG on 6/11/2017 AD.
 */

public class AnimeListOverviewPagerAdapter extends DefaultStatePagerAdapter {

    private Series series;

    public AnimeListOverviewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, context);
        mTitles = context.getResources().getStringArray(R.array.overviews_titles);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
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

}
