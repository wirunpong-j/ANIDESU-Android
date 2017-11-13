package com.bellkung.anidesu.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.MySeries;
import com.bellkung.anidesu.utils.KeyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BellKunG on 13/11/2017 AD.
 */

public class AddListDialogFragment extends DialogFragment {

    private Series series;
    private MySeries mySeries;
    private String action;

    private final String ADD_TEXT = "ADD: ";

    @BindView(R.id.statusSpinner) Spinner statusSpinner;
    @BindView(R.id.progressSpinner) Spinner progressSpinner;
    @BindView(R.id.scoreSpinner) Spinner scoreSpinner;
    @BindView(R.id.addAnimeTextView) TextView addAnimeTextView;

    public static AddListDialogFragment newInstance(Series series) {

        Bundle args = new Bundle();
        args.putParcelable(KeyUtils.KEY_SERIES, series);
        AddListDialogFragment fragment = new AddListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.series = getArguments().getParcelable(KeyUtils.KEY_SERIES);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_fragment_add_list, null);
        ButterKnife.bind(this, view);

        builder.setView(view)
            .setPositiveButton(KeyUtils.SAVE_BTN, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setNegativeButton(KeyUtils.CANCEL_BTN, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        setupSpinner();
        this.addAnimeTextView.setText(ADD_TEXT + this.series.getTitle_romaji());

        return builder.create();
    }

    private void setupSpinner() {

        ArrayAdapter<String> statusSpinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, KeyUtils.STATUS_ARRAY);
        statusSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.statusSpinner.setAdapter(statusSpinnerArrayAdapter);

        ArrayAdapter<Integer> progressSpinnerArrayAdapter = new ArrayAdapter<Integer>(
                getContext(), android.R.layout.simple_spinner_item, getIntegerArray(this.series.getTotal_episodes()));
        progressSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.progressSpinner.setAdapter(progressSpinnerArrayAdapter);

        ArrayAdapter<Integer> scoreSpinnerArrayAdapter = new ArrayAdapter<Integer>(
                getContext(), android.R.layout.simple_spinner_item, KeyUtils.SCORE_ARRAY);
        scoreSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.scoreSpinner.setAdapter(scoreSpinnerArrayAdapter);

    }


    private ArrayList<Integer> getIntegerArray(int end) {
        ArrayList<Integer> intArray = new ArrayList<>();
        for (int start = 1; start <= end; start++) {
            intArray.add(start);
        }

        return intArray;
    }
}
