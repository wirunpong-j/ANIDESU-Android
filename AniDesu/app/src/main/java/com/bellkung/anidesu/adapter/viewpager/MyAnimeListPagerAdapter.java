package com.bellkung.anidesu.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.fragment.MyAnimeListFragment;
import com.bellkung.anidesu.utils.DefaultStatePagerAdapter;
import com.bellkung.anidesu.utils.KeyUtils;

/**
 * Created by BellKunG on 18/11/2017 AD.
 */

public class MyAnimeListPagerAdapter extends DefaultStatePagerAdapter {

    public MyAnimeListPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, context);
        mTitles = context.getResources().getStringArray(R.array.status_titles);
    }

    @Override
    public Fragment getItem(int position) {
        return MyAnimeListFragment.newInstance(KeyUtils.MY_ANIME_LIST_PATH[position]);
    }

}
