package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.utils.KeyUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAnimeListFragment extends Fragment {

    private String status;

    public static MyAnimeListFragment newInstance(String status) {
        Bundle args = new Bundle();
        args.putString(KeyUtils.STATUS, status);
        
        MyAnimeListFragment fragment = new MyAnimeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MyAnimeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = getArguments().getString(KeyUtils.STATUS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_anime_list, container, false);
        return view;
    }

}
