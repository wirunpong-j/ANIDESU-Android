package com.bellkung.anidesu.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.MyAnimeList;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BellKunG on 20/11/2017 AD.
 */

public class MyAnimeListAdapter extends RecyclerView.Adapter<MyAnimeListAdapter.ViewHolder> {

    private Activity activity;
    private Context mContext;
    private ArrayList<Series> allSeries;
    private ArrayList<MyAnimeList> allMyAnimeList;

    public MyAnimeListAdapter(Activity activity, Context mContext) {
        this.activity = activity;
        this.mContext = mContext;
        this.allSeries = new ArrayList<>();
        this.allMyAnimeList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_anime_list, null);

        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Series series = allSeries.get(position);
        MyAnimeList myAnimeList = allMyAnimeList.get(position);

        holder.my_anime_name_textView.setText(series.getTitle_romaji());
        holder.my_anime_ep_textView.setText("EP : " + myAnimeList.getProgress() + " / " + series.getTotal_episodes());
        holder.my_anime_score_textView.setText(myAnimeList.getScore() + " / 10");
        Glide.with(this.activity).load(series.getImage_url_lge()).into(holder.my_anime_list_img);

        Log.i("Status", "onBindViewHolder");

    }

    @Override
    public int getItemCount() {
        return this.allMyAnimeList.size();
    }

    public void setAllSeries(ArrayList<Series> allSeries) {
        this.allSeries = allSeries;
    }

    public void setAllMyAnimeList(ArrayList<MyAnimeList> allMyAnimeList) {
        this.allMyAnimeList = allMyAnimeList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.my_anime_list_img) ImageView my_anime_list_img;
        @BindView(R.id.my_anime_name_textView) TextView my_anime_name_textView;
        @BindView(R.id.my_anime_ep_textView) TextView my_anime_ep_textView;
        @BindView(R.id.my_anime_score_textView) TextView my_anime_score_textView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
