package com.bellkung.anidesu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.bellkung.anidesu.fragment.AnimeListFragment;
import com.bellkung.anidesu.fragment.MyAnimeListFragment;
import com.bellkung.anidesu.utils.KeyUtils;

import java.util.ArrayList;

/**
 * Created by BellKunG on 4/11/2017 AD.
 */

public class AnimeListPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fm;

    private ArrayList<AnimeListFragment> allSeasonFragment;
    private ArrayList<MyAnimeListFragment> allMyFragment;
    private int displayMode;

    public AnimeListPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fm = fragmentManager;
        initalizeAdapterDatasourceForMe();
        initalizeAdapterDatasourceForSeason();
    }

    public void initalizeAdapterDatasourceForSeason() {
        allSeasonFragment = new ArrayList<>();
        allSeasonFragment.add(AnimeListFragment.newInstance(KeyUtils.SEASON[0]));
        allSeasonFragment.add(AnimeListFragment.newInstance(KeyUtils.SEASON[1]));
        allSeasonFragment.add(AnimeListFragment.newInstance(KeyUtils.SEASON[2]));
        allSeasonFragment.add(AnimeListFragment.newInstance(KeyUtils.SEASON[3]));
    }

    public void initalizeAdapterDatasourceForMe() {
        allMyFragment = new ArrayList<>();
        allMyFragment.add(MyAnimeListFragment.newInstance());
        allMyFragment.add(MyAnimeListFragment.newInstance());
        allMyFragment.add(MyAnimeListFragment.newInstance());
        allMyFragment.add(MyAnimeListFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        if (displayMode == 0) {
            return allSeasonFragment.get(position);
        } else {
            return allMyFragment.get(position);
        }
    }

    @Override
    public int getCount() {
        if (displayMode == 0) {
            return allSeasonFragment.size();
        } else {
            return allMyFragment.size();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (displayMode == 0) {
            return KeyUtils.SEASON[position];
        } else {
            return KeyUtils.STATUS_ARRAY[position];
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return AnimeListPagerAdapter.POSITION_NONE;
    }

    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }

}
