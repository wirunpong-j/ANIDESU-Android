package com.bellkung.anidesu.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.fragment.HomeFragment;
import com.bellkung.anidesu.model.DefaultStatePagerAdapter;

/**
 * Created by BellKunG on 21/11/2017 AD.
 */

public class HomePagerAdapter extends DefaultStatePagerAdapter {

    public HomePagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, context);
        mTitles = context.getResources().getStringArray(R.array.home_title);
    }

    @Override
    public Fragment getItem(int position) {
        return HomeFragment.newInstance();
    }
}
