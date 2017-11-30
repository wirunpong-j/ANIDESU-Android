package com.bellkung.anidesu.adapter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.controller.AnimeListActivity;
import com.bellkung.anidesu.model.MyAnimeList;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BellKunG on 20/11/2017 AD.
 */

public class MyAnimeListAdapter extends RecyclerView.Adapter<MyAnimeListAdapter.ViewHolder> {

    private Activity activity;
    private Context mContext;
    private ArrayList<Series> allSeries;
    private HashMap<Integer, MyAnimeList> allMyAnimeList;
    private String status;
    private final String EP_TEXT = "EP : %d / %d";
    private final String SCORE_TEXT = "%d / 10";

    public MyAnimeListAdapter(Activity activity, Context mContext) {
        this.activity = activity;
        this.mContext = mContext;
        this.allSeries = new ArrayList<>();
        this.allMyAnimeList = new HashMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_anime_list, null);

        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Series series = this.allSeries.get(position);
        MyAnimeList myAnimeList = this.allMyAnimeList.get(series.getId());

        holder.my_anime_name_textView.setText(series.getTitle_romaji());
        holder.my_anime_ep_textView.setText(String.format(EP_TEXT, myAnimeList.getProgress(), series.getTotal_episodes()));
        holder.my_anime_score_textView.setText(String.format(SCORE_TEXT, myAnimeList.getScore()));
        Glide.with(this.activity).load(series.getImage_url_lge()).into(holder.my_anime_list_img);

    }

    @Override
    public int getItemCount() {
        return this.allMyAnimeList.size();
    }

    public void setAllSeries(ArrayList<Series> allSeries) {
        this.allSeries = allSeries;
    }

    public void setAllMyAnimeList(HashMap<Integer, MyAnimeList> allMyAnimeList) {
        this.allMyAnimeList = allMyAnimeList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.my_anime_list_img) ImageView my_anime_list_img;
        @BindView(R.id.my_anime_name_textView) TextView my_anime_name_textView;
        @BindView(R.id.my_anime_ep_textView) TextView my_anime_ep_textView;
        @BindView(R.id.my_anime_score_textView) TextView my_anime_score_textView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            my_anime_list_img.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Series series = allSeries.get(getAdapterPosition());
            Intent intent = new Intent(mContext, AnimeListActivity.class);
            intent.putExtra(KeyUtils.KEY_SERIES, series);
            intent.putExtra(KeyUtils.KEY_BMB_STATUS, KeyUtils.BMB_STATUS_EDIT);
            intent.putExtra(KeyUtils.KEY_ANIME_STATUS, status);
            mContext.startActivity(intent);
        }
    }
}
