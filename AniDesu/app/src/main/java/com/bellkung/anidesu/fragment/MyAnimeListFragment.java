package com.bellkung.anidesu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bellkung.anidesu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAnimeListFragment extends Fragment {


    public static MyAnimeListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MyAnimeListFragment fragment = new MyAnimeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MyAnimeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getContext(), "In!!!", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_my_anime_list, container, false);
    }

}
