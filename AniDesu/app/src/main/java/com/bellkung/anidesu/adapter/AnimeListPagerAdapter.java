package com.bellkung.anidesu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.bellkung.anidesu.fragment.AnimeListFragment;
import com.bellkung.anidesu.utils.KeyUtils;

/**
 * Created by BellKunG on 4/11/2017 AD.
 */

public class AnimeListPagerAdapter extends FragmentPagerAdapter {

    private final int ANIME_LIST_PAGER = 4;

    public AnimeListPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position < ANIME_LIST_PAGER) {
            return AnimeListFragment.newInstance(KeyUtils.SEASON[position]);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return ANIME_LIST_PAGER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return KeyUtils.SEASON[position];
    }
}
