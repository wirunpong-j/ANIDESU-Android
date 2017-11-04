package com.bellkung.anidesu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.bellkung.anidesu.fragment.AnimeListFragment;

/**
 * Created by BellKunG on 4/11/2017 AD.
 */

public class AnimeListPagerAdapter extends FragmentPagerAdapter {

    private final int NUM_ITEM = 4;

    public AnimeListPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AnimeListFragment.newInstance("WINTER", position);
            case 1:
                return AnimeListFragment.newInstance("SPRING", position);
            case 2:
                return AnimeListFragment.newInstance("SUMMER", position);
            case 3:
                return AnimeListFragment.newInstance("FALL", position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEM;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "WINTER";
            case 1:
                return "SPRING";
            case 2:
                return "SUMMER";
            case 3:
                return "FALL";
            default:
                return "";
        }
    }
}
