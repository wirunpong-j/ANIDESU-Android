package com.bellkung.anidesu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Posts;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.model.list_post.Like;
import com.bumptech.glide.Glide;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by BellKunG on 22/11/2017 AD.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Holder> {

    private HashMap<String, Posts> allPosts;
    private HashMap<String, AnotherUser> allAnotherUser;
    private ArrayList<String> allKeySet;
    private Activity mActivity;
    private Context mContext;

    public PostsAdapter(Activity mActivity, Context mContext) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.allPosts = new HashMap<>();
        this.allAnotherUser = new HashMap<>();
        this.allKeySet = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_post, null);

        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Posts posts = this.allPosts.get(this.allKeySet.get(position));
        AnotherUser aUser = this.allAnotherUser.get(this.allKeySet.get(position));

        for (Like like: posts.getAllLike()) {
            if (User.getInstance().getUid().equals(like.getUid())) {
                holder.likeBtn.setChecked(true);
                break;
            }
        }

        Glide.with(this.mActivity).load(aUser.getImage_url_profile()).into(holder.posts_profile_image);
        holder.posts_name_TextView.setText(aUser.getDisplay_name());
        holder.dateTimeTextView.setText(posts.getPost_date());
        holder.statusTextView.setText(posts.getStatus());

    }

    @Override
    public int getItemCount() {
        return this.allKeySet.size();
    }

    public void setAllPosts(HashMap<String, Posts> allPosts) {
        this.allPosts = allPosts;
    }

    public void setAllAnotherUser(HashMap<String, AnotherUser> allAnotherUser) {
        this.allAnotherUser = allAnotherUser;
    }

    public void setAllKeySet(ArrayList<String> allKeySet) {
        this.allKeySet = allKeySet;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.posts_profile_image) CircleImageView posts_profile_image;
        @BindView(R.id.posts_name_TextView) TextView posts_name_TextView;
        @BindView(R.id.dateTimeTextView) TextView dateTimeTextView;
        @BindView(R.id.statusTextView) TextView statusTextView;
        @BindView(R.id.countTextView) TextView countTextView;
        @BindView(R.id.likeBtn) ShineButton likeBtn;
        @BindView(R.id.commentBtn) ImageButton commentBtn;

        public Holder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            this.likeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
