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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.controller.HomeActivity;
import com.bellkung.anidesu.model.MyAnimeList;
import com.bellkung.anidesu.model.MySeries;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.utils.KeyUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BellKunG on 13/11/2017 AD.
 */

public class AddListDialogFragment extends DialogFragment implements Spinner.OnItemSelectedListener, View.OnClickListener {

    private Series series;
    private String status;
    private MyAnimeList myAnimeList;
    private String anime_status;

    private final String ADD_TEXT = "ADD: ";
    private final String EDIT_TEXT = "EDIT : ";

    @BindView(R.id.statusSpinner) Spinner statusSpinner;
    @BindView(R.id.progressSpinner) Spinner progressSpinner;
    @BindView(R.id.scoreSpinner) Spinner scoreSpinner;
//    @BindView(R.id.addAnimeTextView) TextView addAnimeTextView;
    @BindView(R.id.notesEditText) EditText notesEditText;
//    @BindView(R.id.deleteFormThisBtn) Button deleteFormThisBtn;

    public static AddListDialogFragment newInstance(String status, Series series, MyAnimeList myAnimeList, String anime_status) {

        Bundle args = new Bundle();
        args.putString(KeyUtils.KEY_BMB_STATUS, status);
        args.putParcelable(KeyUtils.KEY_SERIES, series);
        args.putParcelable(KeyUtils.KEY_GET_MY_ANIME, myAnimeList);
        args.putString(KeyUtils.KEY_ANIME_STATUS, anime_status);
        AddListDialogFragment fragment = new AddListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = getArguments().getString(KeyUtils.KEY_BMB_STATUS);
        this.series = getArguments().getParcelable(KeyUtils.KEY_SERIES);
        this.myAnimeList = getArguments().getParcelable(KeyUtils.KEY_GET_MY_ANIME);
        this.anime_status = getArguments().getString(KeyUtils.KEY_ANIME_STATUS);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_add_list, null);
        ButterKnife.bind(this, view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
            .setPositiveButton(KeyUtils.SAVE_BTN, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveMyAnimeList();
                }
            })
            .setNegativeButton(KeyUtils.CANCEL_BTN, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        setupSpinner();
        setAddOrEditView();

        return builder.create();
    }

    private void setupSpinner() {

        ArrayAdapter<String> statusSpinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, KeyUtils.STATUS_ARRAY);
        statusSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.statusSpinner.setAdapter(statusSpinnerArrayAdapter);
        this.statusSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> progressSpinnerArrayAdapter = new ArrayAdapter<Integer>(
                getContext(), android.R.layout.simple_spinner_item, getIntegerArray(this.series.getTotal_episodes()));
        progressSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.progressSpinner.setAdapter(progressSpinnerArrayAdapter);
        this.progressSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> scoreSpinnerArrayAdapter = new ArrayAdapter<Integer>(
                getContext(), android.R.layout.simple_spinner_item, KeyUtils.SCORE_ARRAY);
        scoreSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.scoreSpinner.setAdapter(scoreSpinnerArrayAdapter);
        this.scoreSpinner.setOnItemSelectedListener(this);

        if (this.status.equals(KeyUtils.BMB_STATUS_EDIT)) {
            int anime_status_pos = ((ArrayAdapter) this.statusSpinner.getAdapter()).getPosition(this.anime_status);
            int anime_progress_pos = ((ArrayAdapter) this.progressSpinner.getAdapter()).getPosition(this.myAnimeList.getProgress());
            int anime_score_pos = ((ArrayAdapter) this.scoreSpinner.getAdapter()).getPosition(this.myAnimeList.getScore());

            this.statusSpinner.setSelection(anime_status_pos);
            this.progressSpinner.setSelection(anime_progress_pos);
            this.scoreSpinner.setSelection(anime_score_pos);
            this.notesEditText.setText(this.myAnimeList.getNote());
        }

    }

    private void setAddOrEditView() {
//        switch (this.status) {
//            case KeyUtils.BMB_STATUS_ADD:
//                this.deleteFormThisBtn.setVisibility(View.GONE);
////                this.addAnimeTextView.setText(ADD_TEXT + this.series.getTitle_romaji());
//                break;
//            case KeyUtils.BMB_STATUS_EDIT:
//                this.deleteFormThisBtn.setVisibility(View.VISIBLE);
////                this.addAnimeTextView.setText(EDIT_TEXT + this.series.getTitle_romaji());
//                break;
//        }
//        this.deleteFormThisBtn.setOnClickListener(this);
    }

    private void saveMyAnimeList() {
        int statusPos = this.statusSpinner.getSelectedItemPosition();
        int progress = (int) this.progressSpinner.getSelectedItem();
        int score = (int) this.scoreSpinner.getSelectedItem();
        String note = String.valueOf(this.notesEditText.getText());

        MyAnimeList myAnimeList = new MyAnimeList();
        myAnimeList.setAnime_id(this.series.getId());
        myAnimeList.setProgress(progress);
        myAnimeList.setScore(score);
        myAnimeList.setNote(note);

//        User.getInstance().saveToMyAnimeList(KeyUtils.MY_ANIME_LIST_PATH[statusPos], myAnimeList);

    }


    private ArrayList<Integer> getIntegerArray(int end) {
        ArrayList<Integer> intArray = new ArrayList<>();
        for (int start = 1; start <= end; start++) {
            intArray.add(start);
        }

        return intArray;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.statusSpinner:
                break;
            case R.id.progressSpinner:
                break;
            case R.id.scoreSpinner:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.deleteFormThisBtn:
//                Toast.makeText(getContext(), "In!!!!", Toast.LENGTH_SHORT).show();
//                HomeActivity.showLoadingView();
//                break;
//
//        }
    }
}
