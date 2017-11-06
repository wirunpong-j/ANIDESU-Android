package com.bellkung.anidesu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bellkung.anidesu.fragment.AnimeListEpisodesFragment;
import com.bellkung.anidesu.fragment.AnimeListExtrasFragment;
import com.bellkung.anidesu.fragment.AnimeListInfoFragment;
import com.bellkung.anidesu.fragment.AnimeListReviewsFragment;
import com.bellkung.anidesu.fragment.AnimeListStatsFragment;
import com.bellkung.anidesu.utils.KeyUtils;

/**
 * Created by BellKunG on 6/11/2017 AD.
 */

public class AnimeListOverviewPagerAdapter extends FragmentPagerAdapter {

    public AnimeListOverviewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return AnimeListInfoFragment.newInstance();
            case 1:
                return AnimeListStatsFragment.newInstance();
            case 2:
                return AnimeListEpisodesFragment.newInstance();
            case 3:
                return AnimeListExtrasFragment.newInstance();
            case 4:
                return AnimeListReviewsFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return KeyUtils.ANIME_OVERVIEWS_PAGER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return KeyUtils.OVERVIEWS[position];
    }
}
