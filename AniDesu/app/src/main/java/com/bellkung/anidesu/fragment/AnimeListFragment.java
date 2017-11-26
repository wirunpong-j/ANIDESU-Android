package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.view.AnimeListAdapter;
import com.bellkung.anidesu.api.ApiConfig;
import com.bellkung.anidesu.api.NetworkConnectionManager;
import com.bellkung.anidesu.api.OnNetworkCallbackListener;
import com.bellkung.anidesu.api.model.Series;
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

    private final int ANIME_SEASON_ROW = 2;

    @BindView(R.id.anime_list_recyclerView) RecyclerView anime_list_recyclerView;
    @BindView(R.id.statusLoadingView) ConstraintLayout statusLoadingView;
    @BindView(R.id.anime_list_no_data_view) ConstraintLayout anime_list_no_data_view;

    public static AnimeListFragment newInstance(String season) {
        AnimeListFragment fragment = new AnimeListFragment();
        Bundle args = new Bundle();
        args.putString(KeyUtils.SEASON_TEXT, season);
        fragment.setArguments(args);
        return fragment;
    }

    public AnimeListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.season = getArguments().getString(KeyUtils.SEASON_TEXT).toLowerCase();
        this.allSeries = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_anime_list, container, false);
        ButterKnife.bind(this, view);

        showIndicatorView();

        new NetworkConnectionManager().fetchAnimeList(this, this.season);
        return view;
    }

    private void setupUI() {

        if (this.allSeries.isEmpty()) {
            this.anime_list_no_data_view.setVisibility(View.VISIBLE);
            this.anime_list_recyclerView.setVisibility(View.GONE);

        } else {
            AnimeListAdapter adapter = new AnimeListAdapter(getActivity(), getContext());
            adapter.setData(this.allSeries);
            this.anime_list_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), ANIME_SEASON_ROW));
            this.anime_list_recyclerView.setAdapter(adapter);
        }

        hideIndicatorView();
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
        setupUI();
    }

    @Override
    public void onBodyErrorIsNull() {
        setupUI();
    }

    @Override
    public void onFailure(Throwable t) {
        setupUI();
    }

    private void showIndicatorView() {
        this.statusLoadingView.setVisibility(View.VISIBLE);
        this.statusLoadingView.setClickable(false);
    }

    private void hideIndicatorView() {
        this.statusLoadingView.setVisibility(View.GONE);
        this.statusLoadingView.setClickable(true);
    }
}
