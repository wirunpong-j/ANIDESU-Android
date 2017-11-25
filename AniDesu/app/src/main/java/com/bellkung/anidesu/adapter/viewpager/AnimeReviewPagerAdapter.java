package com.bellkung.anidesu.adapter.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.fragment.AnimeReviewFragment;
import com.bellkung.anidesu.utils.DefaultStatePagerAdapter;

/**
 * Created by BellKunG on 24/11/2017 AD.
 */

public class AnimeReviewPagerAdapter extends DefaultStatePagerAdapter {

    public AnimeReviewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, context);
        mTitles = context.getResources().getStringArray(R.array.review_title);
    }

    @Override
    public Fragment getItem(int position) {
        return AnimeReviewFragment.newInstance();
    }
}
