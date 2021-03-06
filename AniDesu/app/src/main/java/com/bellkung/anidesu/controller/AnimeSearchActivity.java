package com.bellkung.anidesu.controller;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.view.AnimeListAdapter;
import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.utils.KeyUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AnimeSearchActivity extends AppCompatActivity implements OnNetworkCallbackListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.anime_list_search_recycleView) RecyclerView anime_list_search_recycleView;
    @BindView(R.id.searchLoadingView) ConstraintLayout mSearchLoadingView;
    @BindView(R.id.avi) AVLoadingIndicatorView mAvi;
    @BindView(R.id.search_no_data_view) ConstraintLayout search_no_data_view;

    private ArrayList<Series> allSeries;

    private final int ANIME_SEARCH_ROW = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_search);
        ButterKnife.bind(this, this);

        showLoadingView();

        this.allSeries = new ArrayList<>();

        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String mSearchText = getIntent().getStringExtra(KeyUtils.KEY_SEARCH_TEXT);
        setTitle(mSearchText);

        if (!mSearchText.equals("")) {
            new NetworkConnectionManager().fetchSeriesSearchData(this, mSearchText, ApiConfig.FETCH_ANIME_SEARCH);
        } else {
            initialView();
        }

    }

    private void initialView() {

        if (this.allSeries.isEmpty()) {
            this.search_no_data_view.setVisibility(View.VISIBLE);
            this.anime_list_search_recycleView.setVisibility(View.GONE);

        } else {
            AnimeListAdapter adapter = new AnimeListAdapter(this, this);
            adapter.setData(this.allSeries);

            this.anime_list_search_recycleView.setLayoutManager(new GridLayoutManager(this, ANIME_SEARCH_ROW));
            this.anime_list_search_recycleView.setAdapter(adapter);
        }
        hideLoadingView();
    }

    @Override
    public void onResponse(String action, Call call, Response response) {
        switch (action) {
            case ApiConfig.FETCH_ANIME_SEARCH:
                this.allSeries = (ArrayList<Series>) response.body();

                // remove adult content
                for (Iterator<Series> iterator = this.allSeries.iterator(); iterator.hasNext(); ) {
                    Series series = iterator.next();
                    if (series.isAdult()) {
                        iterator.remove();
                    }
                }

                initialView();
                break;
        }
    }

    @Override
    public void onBodyError(ResponseBody responseBodyError) {
        initialView();
    }

    @Override
    public void onBodyErrorIsNull() {
        initialView();
    }

    @Override
    public void onFailure(Throwable t) {
        initialView();
    }

    public void showLoadingView() {
        this.mAvi.show();
        this.mSearchLoadingView.setVisibility(View.VISIBLE);
        this.mSearchLoadingView.setClickable(false);
    }

    public void hideLoadingView() {
        this.mSearchLoadingView.setVisibility(View.INVISIBLE);
        this.mSearchLoadingView.setClickable(true);
    }
}
