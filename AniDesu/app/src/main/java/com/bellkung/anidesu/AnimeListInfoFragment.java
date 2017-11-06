package com.bellkung.anidesu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListInfoFragment extends Fragment {

    public AnimeListInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_list_info, container, false);

        return view;
    }

}
