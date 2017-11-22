package com.bellkung.anidesu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.PostsAdapter;
import com.bellkung.anidesu.controller.PostActivity;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.model.Posts;
import com.bellkung.anidesu.utils.KeyUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment implements PostsListener  {

    @BindView(R.id.posts_recycleView) RecyclerView posts_recycleView;
    @BindView(R.id.bmb_posts) BoomMenuButton boomMenuBtn;

    private final int POSTS_ROW = 1;

    private HashMap<String, Posts> allPost;
    private HashMap<String, AnotherUser> allAnotherUser;
    private ArrayList<String> allKeySet;

    private PostsListener listener;

    public static PostsFragment newInstance() {
        
        Bundle args = new Bundle();

        PostsFragment fragment = new PostsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this, view);

        this.listener = this;
        fetchAllPosts();
        setBoomMenuButton();
        return view;
    }

    private void fetchAllPosts() {



        DatabaseReference mPostsRef = FirebaseDatabase.getInstance().getReference("posts");
        mPostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // clear data when call this method.
                allPost = new HashMap<>();
                allAnotherUser = new HashMap<>();
                allKeySet = new ArrayList<>();

                for (DataSnapshot parent: dataSnapshot.getChildren()) {
                    final Posts post = parent.getValue(Posts.class);
                    allKeySet.add(parent.getKey());
                    allPost.put(parent.getKey(), post);
                }

                if (listener != null) {
                    listener.onFetchPosts();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onFetchPosts() {
        Iterator it = this.allPost.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            final Posts post = (Posts) pair.getValue();

            DatabaseReference mPostsRef = FirebaseDatabase.getInstance()
                    .getReference("users/" + post.getUid());
            mPostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    AnotherUser aUser = dataSnapshot.child("/profile").getValue(AnotherUser.class);
                    aUser.setUid(dataSnapshot.getKey());

                    allAnotherUser.put(post.getPost_key(), aUser);

                    if (allAnotherUser.size() == allPost.size()) {
                        listener.onFetchUser();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onFetchUser() {
        setupView();
    }

    private void setupView() {
        PostsAdapter adapter = new PostsAdapter(getActivity(), getContext());
        adapter.setAllPosts(this.allPost);
        adapter.setAllAnotherUser(this.allAnotherUser);
        adapter.setAllKeySet(this.allKeySet);

        this.posts_recycleView.setLayoutManager(new GridLayoutManager(getContext(), POSTS_ROW));
        this.posts_recycleView.setAdapter(adapter);
    }

    private void setBoomMenuButton() {
        this.boomMenuBtn.setButtonEnum(ButtonEnum.TextOutsideCircle);
        this.boomMenuBtn.setPiecePlaceEnum(PiecePlaceEnum.DOT_1);
        this.boomMenuBtn.setButtonPlaceEnum(ButtonPlaceEnum.SC_1);

        for (int i = 0; i < this.boomMenuBtn.getPiecePlaceEnum().pieceNumber(); i++) {

            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .isRound(false)
                    .shadowCornerRadius(Util.dp2px(20))
                    .buttonCornerRadius(Util.dp2px(20))
                    .normalImageRes(KeyUtils.BMB_DRAWABLE[0])
                    .normalText(KeyUtils.BMB_POST);
            builder.listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    Intent intent = new Intent(getActivity(), PostActivity.class);
                    startActivity(intent);
                }
            });

            this.boomMenuBtn.addBuilder(builder);
        }
    }


}

interface PostsListener {
    void onFetchPosts();
    void onFetchUser();
}
