package com.bellkung.anidesu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bellkung.anidesu.R;
import com.bellkung.anidesu.controller.CommentPostsActivity;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Posts;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.utils.KeyUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.HashMap;

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
    public void onBindViewHolder(final Holder holder, int position) {
        final Posts posts = this.allPosts.get(this.allKeySet.get(position));
        final AnotherUser aUser = this.allAnotherUser.get(this.allKeySet.get(position));

        if (posts.getAllLike().containsKey(User.getInstance().getUid())) {
            holder.likeBtn.setChecked(true);
        }

        DatabaseReference mLikeRef = FirebaseDatabase.getInstance()
                .getReference("posts/" + posts.getPost_key());
        mLikeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Posts currentPost = dataSnapshot.getValue(Posts.class);

                Glide.with(mActivity).load(aUser.getImage_url_profile()).into(holder.posts_profile_image);
                holder.posts_name_TextView.setText(aUser.getDisplay_name());
                holder.dateTimeTextView.setText(posts.getPost_date());
                holder.statusTextView.setText(posts.getStatus());
                holder.countTextView.setText(currentPost.getLike_count() + " Likes");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
            this.commentBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            final Posts thisPost = allPosts.get(allKeySet.get(getAdapterPosition()));
            DatabaseReference mLikeRef = FirebaseDatabase.getInstance()
                    .getReference("posts/" + thisPost.getPost_key());
            final AnotherUser aUser = allAnotherUser.get(allKeySet.get(getAdapterPosition()));

            switch(v.getId()) {
                case R.id.likeBtn:
                    manageLikePost(mLikeRef);
                    break;

                case R.id.commentBtn:
                    showCommentThisPost(thisPost, aUser);
                    break;
            }
        }

        private void showCommentThisPost(Posts post, AnotherUser aUser) {
            Intent intent = new Intent(mContext, CommentPostsActivity.class);
            intent.putExtra(KeyUtils.COMMENT_POST, post);
            intent.putExtra(KeyUtils.COMMENT_USER, aUser);
            mContext.startActivity(intent);
        }

        private void manageLikePost(DatabaseReference mLikeRef) {
            mLikeRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    Posts posts = mutableData.getValue(Posts.class);
                    HashMap<String, Boolean> allLike = (HashMap<String, Boolean>) mutableData.child("like").getValue();

                    if (allLike == null) {
                        allLike = new HashMap<>();
                    }

                    if (allLike.containsKey(User.getInstance().getUid())) {
                        allLike.remove(User.getInstance().getUid());
                        posts.setLike_count(posts.getLike_count() - 1);
                    } else {
                        allLike.put(User.getInstance().getUid(), true);
                        posts.setLike_count(posts.getLike_count() + 1);
                    }

                    mutableData.child("like_count").setValue(posts.getLike_count());
                    mutableData.child("like").setValue(allLike);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                    if (databaseError != null) {
                        Log.i("Status", databaseError.getMessage());
                    } else {
                        Log.i("Status", "Transaction is success.");
                    }
                }
            });

        }
    }
}
