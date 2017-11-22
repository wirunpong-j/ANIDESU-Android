package com.bellkung.anidesu.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.bellkung.anidesu.R;

/**
 * Created by BellKunG on 22/11/2017 AD.
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    public MySwipeRefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setColorSchemeResources(R.color.colorAccent_dark, R.color.colorLightBlue, R.color.colorLightGreen);
        setProgressBackgroundColorSchemeResource(R.color.polo_blue);
        setSize(LARGE);
    }

    public void startRefresh() {
        setRefreshing(true);
    }

    public void refreshComplete() {
        setRefreshing(false);
    }
}
