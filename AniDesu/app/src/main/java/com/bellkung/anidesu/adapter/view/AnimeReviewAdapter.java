package com.bellkung.anidesu.adapter.view;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.model.Reviews;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by BellKunG on 26/11/2017 AD.
 */

public class AnimeReviewAdapter extends RecyclerView.Adapter<AnimeReviewAdapter.Holder> {

    private ArrayList<Reviews> allReviews;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anime_review, null);

        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.allReviews.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.a_review_view) ConstraintLayout a_review_view;
        @BindView(R.id.a_reviewer_imageView) CircleImageView a_reviewer_imageView;
        @BindView(R.id.a_reviwer_name_textView) TextView a_reviwer_name_textView;
        @BindView(R.id.a_review_date) TextView a_review_date;
        @BindView(R.id.a_review_textView) TextView a_review_textView;
        @BindView(R.id.a_review_ratingbar) RatingBar a_review_ratingbar;

        public Holder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
