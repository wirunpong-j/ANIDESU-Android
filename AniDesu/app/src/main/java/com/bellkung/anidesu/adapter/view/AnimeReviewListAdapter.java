package com.bellkung.anidesu.adapter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.controller.ReviewActivity;
import com.bellkung.anidesu.custom.FormatCustomManager;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Reviews;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by BellKunG on 24/11/2017 AD.
 */

public class AnimeReviewListAdapter extends RecyclerView.Adapter<AnimeReviewListAdapter.Holder>  {

    private ArrayList<Reviews> allReviews;
    private ArrayList<AnotherUser> allReviewer;
    private ArrayList<Series> allSeries;

    private Activity mActivity;
    private Context mContext;

    private final String WRITE_BY = "By : ";
    private final int[] HOVER_ITEM_ID = {R.id.reviewer_imageView, R.id.reviewer_name_textView, R.id.review_textView,
                                        R.id.readMoreBtn, R.id.review_ratingbar, R.id.review_date_textView};

    public AnimeReviewListAdapter(Activity activity, Context context) {
        this.mActivity = activity;
        this.mContext = context;
        this.allReviews = new ArrayList<>();
        this.allReviewer = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_review, null);

        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final Reviews review = this.allReviews.get(position);
        final AnotherUser reviewer = this.allReviewer.get(position);
        final Series series = this.allSeries.get(position);

        View hover = LayoutInflater.from(mContext).inflate(R.layout.hover_review, null);
        holder.blurLayout.setHoverView(hover);

        for (int item_id: HOVER_ITEM_ID) {
            setHoverAnimation(hover, holder, item_id);
        }

        Glide.with(this.mContext).load(series.getImage_url_banner()).into(holder.review_imageView);
        CircleImageView reviewer_imageView = hover.findViewById(R.id.reviewer_imageView);
        TextView reviewer_name_textView = hover.findViewById(R.id.reviewer_name_textView);
        TextView review_textView = hover.findViewById(R.id.review_textView);
        TextView review_date_textView = hover.findViewById(R.id.review_date_textView);
        RatingBar review_ratingbar = hover.findViewById(R.id.review_ratingbar);
        Button readMoreBtn = hover.findViewById(R.id.readMoreBtn);

        Glide.with(this.mContext).load(reviewer.getImage_url_profile()).into(reviewer_imageView);
        reviewer_name_textView.setText(WRITE_BY + reviewer.getDisplay_name());
        review_textView.setText(review.getText());
        review_ratingbar.setRating(review.getRating());
        review_date_textView.setText(FormatCustomManager.parseOnFirebaseDateTime(review.getReview_date()));

        readMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReviewActivity.class);
                intent.putExtra(KeyUtils.KEY_REVIEW_SERIES, series);
                intent.putExtra(KeyUtils.KEY_REVIEW_REVIEW, review);
                intent.putExtra(KeyUtils.KEY_REVIEW_REVIEWER, reviewer);

                mContext.startActivity(intent);
            }
        });

    }

    private void setHoverAnimation(View hover, Holder holder, int item_id) {
        holder.blurLayout.addChildAppearAnimator(hover, item_id, Techniques.Landing);
        holder.blurLayout.addChildDisappearAnimator(hover, item_id, Techniques.TakingOff);
    }


    @Override
    public int getItemCount() {
        return this.allReviews.size();
    }

    public void setAllReviews(ArrayList<Reviews> allReviews) {
        this.allReviews = allReviews;
    }

    public void setAllReviewer(ArrayList<AnotherUser> allReviewer) {
        this.allReviewer = allReviewer;
    }

    public void setAllSeries(ArrayList<Series> allSeries) {
        this.allSeries = allSeries;
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.blurLayout) BlurLayout blurLayout;
        @BindView(R.id.review_imageView) ImageView review_imageView;

        public Holder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
