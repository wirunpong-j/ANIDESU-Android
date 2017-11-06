package com.bellkung.anidesu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bellkung.anidesu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeListInfoFragment extends Fragment {

    public AnimeListInfoFragment() {
    }

    public static AnimeListInfoFragment newInstance() {
        Bundle args = new Bundle();
        
        AnimeListInfoFragment fragment = new AnimeListInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_list_info, container, false);

        return view;
    }

}
