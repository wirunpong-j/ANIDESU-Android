package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.AnimeListAdapter;
import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.controller.HomeActivity;
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
public class AnimeListFragment extends Fragment implements OnNetworkCallbackListener {

    private String season;
    private ArrayList<Series> allSeries;

    @BindView(R.id.anime_list_recyclerView) RecyclerView anime_list_recyclerView;


    public static AnimeListFragment newInstance(String season) {
        AnimeListFragment fragment = new AnimeListFragment();
        Bundle args = new Bundle();
        args.putString("season", season);
        fragment.setArguments(args);
        return fragment;
    }

    public AnimeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.season = getArguments().getString("season").toLowerCase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_anime_list, container, false);
        ButterKnife.bind(this, view);
        this.anime_list_recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), KeyUtils.ANIME_SEASON_ROW));
        new NetworkConnectionManager().fetchAnimeList(this, this.season);
        return view;
    }

    private void setupUI() {
        AnimeListAdapter adapter = new AnimeListAdapter(getActivity(), getContext());
        adapter.setData(this.allSeries);
        this.anime_list_recyclerView.setAdapter(adapter);
    }

    // Call back
    @Override
    public void onResponse(String action, Call call, Response response) {
        switch (action) {
            case ApiConfig.FETCH_ANIME_LIST:
                this.allSeries = (ArrayList<Series>) response.body();
                setupUI();
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
