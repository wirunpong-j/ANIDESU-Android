package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.AnimeListAdapter;
import com.bellkung.anidesu.adapter.MyAnimeListAdapter;
import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.MyAnimeList;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.utils.KeyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAnimeListFragment extends Fragment implements OnNetworkCallbackListener {

    private String status;
    private final int MY_ANIME_ROW = 2;
    private ArrayList<Series> allSeries;
    private int itemCount = 0;
    private ArrayList<MyAnimeList> myAnimeLists;

    @BindView(R.id.myAnimeListRecycleView) RecyclerView myAnimeListRecycleView;

    public static MyAnimeListFragment newInstance(String status) {
        Bundle args = new Bundle();
        args.putString(KeyUtils.STATUS, status);
        
        MyAnimeListFragment fragment = new MyAnimeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MyAnimeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = getArguments().getString(KeyUtils.STATUS);
        this.allSeries = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_anime_list, container, false);
        ButterKnife.bind(this, view);
        fetchMyAnimeList();

        this.myAnimeListRecycleView.setLayoutManager(new GridLayoutManager(view.getContext(), MY_ANIME_ROW));

        return view;
    }

    private void fetchMyAnimeList() {
        this.myAnimeLists = null;
        switch (status) {
            case KeyUtils.STATUS_PLAN_TO_WATCH:
                this.myAnimeLists = User.getInstance().getList_plan();
                break;
            case KeyUtils.STATUS_WATCHING:
                this.myAnimeLists = User.getInstance().getList_watching();
                break;
            case KeyUtils.STATUS_COMPLETED:
                this.myAnimeLists = User.getInstance().getList_completed();
                break;
            case KeyUtils.STATUS_DROPPED:
                this.myAnimeLists = User.getInstance().getList_dropped();
                break;
        }

        for (MyAnimeList myAnime: this.myAnimeLists) {
            new NetworkConnectionManager().fetchThisSeriesData(this, myAnime.getAnime_id(), ApiConfig.FETCH_MY_ANIME);
        }
    }

    private void setupUI() {
        MyAnimeListAdapter adapter = new MyAnimeListAdapter(getActivity(), getContext());
        switch (status) {
            case KeyUtils.STATUS_PLAN_TO_WATCH:
                adapter.setAllMyAnimeList(User.getInstance().getList_plan());
                break;
            case KeyUtils.STATUS_WATCHING:
                adapter.setAllMyAnimeList(User.getInstance().getList_watching());
                break;
            case KeyUtils.STATUS_COMPLETED:
                adapter.setAllMyAnimeList(User.getInstance().getList_completed());
                break;
            case KeyUtils.STATUS_DROPPED:
                adapter.setAllMyAnimeList(User.getInstance().getList_dropped());
                break;
        }
        adapter.setAllSeries(this.allSeries);
        this.myAnimeListRecycleView.setAdapter(adapter);
    }


    @Override
    public void onResponse(String action, Call call, Response response) {
        switch (action) {
            case ApiConfig.FETCH_MY_ANIME:
                Series aSeries = (Series) response.body();
                this.allSeries.add(aSeries);

                this.itemCount++;

                if (this.itemCount == this.myAnimeLists.size()) {
                    setupUI();
                    this.itemCount = 0;
                }
                break;
        }
    }

    @Override
    public void onBodyError(ResponseBody responseBodyError) {

    }

    @Override
    public void onBodyErrorIsNull() {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
