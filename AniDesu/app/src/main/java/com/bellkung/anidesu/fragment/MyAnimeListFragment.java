package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.view.MyAnimeListAdapter;
import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.MyAnimeList;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.utils.KeyUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class
MyAnimeListFragment extends Fragment implements OnNetworkCallbackListener {

    private String status;
    private final int MY_ANIME_ROW = 2;
    private ArrayList<Series> allSeries;
    private int itemCount = 0;
    private HashMap<Integer, MyAnimeList> myAnimeLists;

    @BindView(R.id.myAnimeListRecycleView) RecyclerView myAnimeListRecycleView;

    public static MyAnimeListFragment newInstance(String status) {
        Bundle args = new Bundle();
        args.putString(KeyUtils.KEY_ANIME_STATUS, status);
        
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
        this.status = getArguments().getString(KeyUtils.KEY_ANIME_STATUS);
        this.allSeries = new ArrayList<>();
        this.myAnimeLists = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (fetchMyAnimeList()) {
            view =  inflater.inflate(R.layout.fragment_my_anime_list, container, false);
            ButterKnife.bind(this, view);
            this.myAnimeListRecycleView.setLayoutManager(new GridLayoutManager(view.getContext(), MY_ANIME_ROW));
        } else {
            view =  inflater.inflate(R.layout.content_no_data, container, false);
        }
        return view;
    }

    private boolean fetchMyAnimeList() {

        this.myAnimeLists = null;
        switch (this.status) {
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

        for (Integer id: this.myAnimeLists.keySet()) {
            new NetworkConnectionManager().fetchThisSeriesData(this, id, ApiConfig.FETCH_MY_ANIME);
        }

        if (this.myAnimeLists.isEmpty()) {
            return false;
        }
        return true;
    }

    private void setupUI() {
        MyAnimeListAdapter adapter = new MyAnimeListAdapter(getActivity(), getContext());
        switch (this.status) {
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
        adapter.setStatus(this.status);
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
