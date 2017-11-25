package com.bellkung.anidesu.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.fragment.AnimeListFragment;
import com.bellkung.anidesu.utils.DefaultStatePagerAdapter;
import com.bellkung.anidesu.utils.KeyUtils;

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
