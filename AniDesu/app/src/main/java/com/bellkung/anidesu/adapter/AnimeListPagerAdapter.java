package com.bellkung.anidesu.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.fragment.AnimeListFragment;
import com.bellkung.anidesu.fragment.MyAnimeListFragment;
import com.bellkung.anidesu.model.DefaultStatePagerAdapter;
import com.bellkung.anidesu.utils.KeyUtils;

import java.util.ArrayList;

/**
 * Created by BellKunG on 4/11/2017 AD.
 */

public class AnimeListPagerAdapter extends DefaultStatePagerAdapter {

    public AnimeListPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, context);
        mTitles = context.getResources().getStringArray(R.array.seasons_titles);
    }

    @Override
    public Fragment getItem(int position) {
        return AnimeListFragment.newInstance(KeyUtils.SEASON[position]);
    }

}
