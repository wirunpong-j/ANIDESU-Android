package com.bellkung.anidesu.adapter;

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
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BellKunG on 5/11/2017 AD.
 */

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.ViewHolder> {

    private Activity activity;
    private Context mContext;
    private ArrayList<Series> data;

    public AnimeListAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.data = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anime_list, null);

        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Series series = data.get(position);

        Glide.with(this.activity).load(series.getImage_url_lge()).into(viewHolder.anime_model_image);
        viewHolder.anime_model_name.setText(series.getTitle_english());
        viewHolder.anime_model_rating.setText(String.valueOf(series.getAverage_score()));
        viewHolder.anime_model_type.setText(series.getType());
        viewHolder.anime_model_airing.setText(series.getAiring_status());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public void setData(ArrayList<Series> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.anime_model_image) ImageView anime_model_image;
        @BindView(R.id.anime_model_name) TextView anime_model_name;
        @BindView(R.id.anime_model_rating) TextView anime_model_rating;
        @BindView(R.id.anime_model_type) TextView anime_model_type;
        @BindView(R.id.anime_model_airing) TextView anime_model_airing;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            anime_model_image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Series series = data.get(getAdapterPosition());
            Intent intent = new Intent(mContext, AnimeListActivity.class);
            intent.putExtra(KeyUtils.KEY_SERIES, series);
            mContext.startActivity(intent);
        }
    }


}
