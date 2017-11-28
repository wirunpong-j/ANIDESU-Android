package com.bellkung.anidesu.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.adapter.view.PostsAdapter;
import com.bellkung.anidesu.controller.PostActivity;
import com.bellkung.anidesu.model.AnotherUser;
import com.bellkung.anidesu.service.PostService;
import com.bellkung.anidesu.model.Posts;
import com.bellkung.anidesu.utils.KeyUtils;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.widget.CircularProgressDrawable.LARGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment implements PostService.FetchPostListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.posts_recycleView) RecyclerView posts_recycleView;
    @BindView(R.id.bmb_posts) BoomMenuButton boomMenuBtn;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.statusLoadingView) ConstraintLayout statusLoadingView;
    @BindView(R.id.posts_no_data_view) ConstraintLayout posts_no_data_view;

    private final int POSTS_ROW = 1;

    private HashMap<String, Posts> allPost;
    private HashMap<String, AnotherUser> allWriter;
    private ArrayList<String> allKeySet;

    private final PostService postService = new PostService();

    public static PostsFragment newInstance() {
        
        Bundle args = new Bundle();

        PostsFragment fragment = new PostsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PostsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.allPost = new HashMap<>();
        this.allWriter = new HashMap<>();
        this.allKeySet = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this, view);

        showIndicatorView();

        this.postService.setFetchPostListener(this);
        this.postService.fetchAllPostData();

        setBoomMenuButton();
        setupSwipeRefreshLayout();

        return view;
    }

    private void setupView() {

        if (this.allPost.isEmpty()) {
            this.posts_no_data_view.setVisibility(View.VISIBLE);
            this.posts_recycleView.setVisibility(View.GONE);

        } else {

            this.posts_no_data_view.setVisibility(View.GONE);
            this.posts_recycleView.setVisibility(View.VISIBLE);

            Collections.reverse(this.allKeySet);
            PostsAdapter adapter = new PostsAdapter(getActivity(), getContext());
            adapter.setAllPosts(this.allPost);
            adapter.setAllWriter(this.allWriter);
            adapter.setAllKeySet(this.allKeySet);

            this.posts_recycleView.setLayoutManager(new GridLayoutManager(getContext(), POSTS_ROW));
            this.posts_recycleView.setAdapter(adapter);

            this.swipeRefreshLayout.setRefreshing(false);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideIndicatorView();
            }
        }, 1000);

    }

    private void setupSwipeRefreshLayout() {
        this.swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4183D7"),
                Color.parseColor("#F62459"),
                Color.parseColor("#03C9A9"),
                Color.parseColor("#F4D03F"));
        this.swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.black);
        this.swipeRefreshLayout.setSize(LARGE);
        this.swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showIndicatorView();
                postService.fetchAllPostData();
            }
        }, 1000);
    }

    @Override
    public void onFetchAllPostCompleted(HashMap<String, Posts> allPost, HashMap<String, AnotherUser> allAnotherUser, ArrayList<String> allKeySet) {
        this.allPost = allPost;
        this.allWriter = allAnotherUser;
        this.allKeySet = allKeySet;

        setupView();
    }

    @Override
    public void onFetchAllPostFailed(String errorText) {
        Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
        setupView();
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

    private void showIndicatorView() {
        this.statusLoadingView.setVisibility(View.VISIBLE);
        this.statusLoadingView.setClickable(false);
    }

    private void hideIndicatorView() {
        this.statusLoadingView.setVisibility(View.GONE);
        this.statusLoadingView.setClickable(true);
    }

}
