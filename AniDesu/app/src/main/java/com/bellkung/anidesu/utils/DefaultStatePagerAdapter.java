package com.bellkung.anidesu.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by BellKunG on 18/11/2017 AD.
 */

public abstract class DefaultStatePagerAdapter extends FragmentStatePagerAdapter {

    protected String[] mTitles;
    protected Context mContext;

    public DefaultStatePagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.mContext = context;
    }

    @Override
    public abstract Fragment getItem(int position);

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
