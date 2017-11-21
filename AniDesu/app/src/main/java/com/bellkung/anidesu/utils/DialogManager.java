package com.bellkung.anidesu.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.MyAnimeList;
import com.bellkung.anidesu.model.User;

import java.util.ArrayList;


/**
 * Created by BellKunG on 20/11/2017 AD.
 */

public class DialogManager {

    private Context mContext;
    private final ProgressDialog progress;

    public DialogManager(Context context) {
        this.mContext = context;
        this.progress = new ProgressDialog(this.mContext);
        this.progress.setMessage(this.mContext.getString(R.string.progress_text));
    }


    public interface DialogManagerListener{
        void onDialogDismiss();
    }

    private DialogManagerListener listener;

    public void addMyAnimeListDialog(final Series series) {

        MaterialDialog.Builder addDialog = new MaterialDialog.Builder(this.mContext)
                .typeface(Typeface.SANS_SERIF,Typeface.SANS_SERIF)
                .title(series.getTitle_romaji())
                .autoDismiss(false)
                .iconRes(R.drawable.ic_add)
                .customView(R.layout.dialog_fragment_add_list, true)
                .buttonRippleColorRes(R.color.colorAccent)
                .positiveColorRes(R.color.colorStateBlue)
                .positiveText(R.string.dia_add_btn)
                .negativeText(R.string.dia_cancel_btn);

        MaterialDialog addDialogMD = addDialog.build();
        final Spinner statusSpinner = (Spinner) addDialogMD.findViewById(R.id.statusSpinner);
        final Spinner progressSpinner = (Spinner) addDialogMD.findViewById(R.id.progressSpinner);
        final Spinner scoreSpinner = (Spinner) addDialogMD.findViewById(R.id.scoreSpinner);
        final EditText notesEditText = (EditText) addDialogMD.findViewById(R.id.notesEditText);

        ArrayAdapter<String> statusSpinnerArrayAdapter = new ArrayAdapter<String>(
                this.mContext, android.R.layout.simple_spinner_item, KeyUtils.STATUS_ARRAY);
        statusSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusSpinnerArrayAdapter);

        ArrayAdapter<Integer> progressSpinnerArrayAdapter = new ArrayAdapter<Integer>(
                this.mContext, android.R.layout.simple_spinner_item, getIntegerArray(series.getTotal_episodes()));
        progressSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        progressSpinner.setAdapter(progressSpinnerArrayAdapter);

        ArrayAdapter<Integer> scoreSpinnerArrayAdapter = new ArrayAdapter<Integer>(
                this.mContext, android.R.layout.simple_spinner_item, KeyUtils.SCORE_ARRAY);
        scoreSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scoreSpinner.setAdapter(scoreSpinnerArrayAdapter);

        addDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                progress.show();

                int statusPos = statusSpinner.getSelectedItemPosition();
                int progressEP = (int) progressSpinner.getSelectedItem();
                int score = (int) scoreSpinner.getSelectedItem();
                String note = String.valueOf(notesEditText.getText());

                MyAnimeList myAnimeList = new MyAnimeList();
                myAnimeList.setAnime_id(series.getId());
                myAnimeList.setProgress(progressEP);
                myAnimeList.setScore(score);
                myAnimeList.setNote(note);

                final MaterialDialog dia = dialog;
                User.getInstance().setMyAnimeListListener(new User.MyAnimeListListener() {
                    @Override
                    public void onSuccess() {
                        dia.dismiss();
                        progress.dismiss();
                        Toast.makeText(mContext, "ADDED", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onDialogDismiss();
                        }
                    }

                    @Override
                    public void onFailed(String error) {

                    }
                });
                User.getInstance().addMyAnimeList(KeyUtils.MY_ANIME_LIST_PATH[statusPos], myAnimeList);


            }
        });

        addDialog.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });

        addDialog.show();
    }

    public void EditMyAnimeListDialog(final String old_status, final Series series, final MyAnimeList myAnimeList) {

        MaterialDialog.Builder editDialog = new MaterialDialog.Builder(this.mContext)
                .typeface(Typeface.SANS_SERIF,Typeface.SANS_SERIF)
                .title(series.getTitle_romaji())
                .autoDismiss(false)
                .iconRes(R.drawable.ic_add)
                .customView(R.layout.dialog_fragment_add_list, true)
                .buttonRippleColorRes(R.color.colorAccent)
                .positiveColorRes(R.color.colorStateBlue)
                .neutralColorRes(R.color.colorStateOrange)
                .positiveText(R.string.dia_edit_btn)
                .negativeText(R.string.dia_cancel_btn)
                .neutralText(R.string.dia_delete_btn);

        MaterialDialog editDialogMD = editDialog.build();
        final Spinner statusSpinner = (Spinner) editDialogMD.findViewById(R.id.statusSpinner);
        final Spinner progressSpinner = (Spinner) editDialogMD.findViewById(R.id.progressSpinner);
        final Spinner scoreSpinner = (Spinner) editDialogMD.findViewById(R.id.scoreSpinner);
        final EditText notesEditText = (EditText) editDialogMD.findViewById(R.id.notesEditText);

        ArrayAdapter<String> statusSpinnerArrayAdapter = new ArrayAdapter<String>(
                this.mContext, android.R.layout.simple_spinner_item, KeyUtils.STATUS_ARRAY);
        statusSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusSpinnerArrayAdapter);

        ArrayAdapter<Integer> progressSpinnerArrayAdapter = new ArrayAdapter<Integer>(
                this.mContext, android.R.layout.simple_spinner_item, getIntegerArray(series.getTotal_episodes()));
        progressSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        progressSpinner.setAdapter(progressSpinnerArrayAdapter);

        ArrayAdapter<Integer> scoreSpinnerArrayAdapter = new ArrayAdapter<Integer>(
                this.mContext, android.R.layout.simple_spinner_item, KeyUtils.SCORE_ARRAY);
        scoreSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scoreSpinner.setAdapter(scoreSpinnerArrayAdapter);

        int anime_status_pos = ((ArrayAdapter) statusSpinner.getAdapter()).getPosition(getOldStatusTextOfSpinner(old_status));
        int anime_progress_pos = ((ArrayAdapter) progressSpinner.getAdapter()).getPosition(myAnimeList.getProgress());
        int anime_score_pos = ((ArrayAdapter) scoreSpinner.getAdapter()).getPosition(myAnimeList.getScore());

        statusSpinner.setSelection(anime_status_pos);
        progressSpinner.setSelection(anime_progress_pos);
        scoreSpinner.setSelection(anime_score_pos);
        notesEditText.setText(myAnimeList.getNote());

        editDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                progress.show();

                int statusPos = statusSpinner.getSelectedItemPosition();
                int progressEP = (int) progressSpinner.getSelectedItem();
                int score = (int) scoreSpinner.getSelectedItem();
                String note = String.valueOf(notesEditText.getText());

                MyAnimeList newMyAnimeList = new MyAnimeList();
                newMyAnimeList.setAnime_id(series.getId());
                newMyAnimeList.setProgress(progressEP);
                newMyAnimeList.setScore(score);
                newMyAnimeList.setNote(note);

                final MaterialDialog dia = dialog;

                User.getInstance().setMyAnimeListListener(new User.MyAnimeListListener() {
                    @Override
                    public void onSuccess() {
                        dia.dismiss();
                        progress.dismiss();
                        Toast.makeText(mContext, "EDITED", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onDialogDismiss();
                        }
                    }

                    @Override
                    public void onFailed(String error) {

                    }
                });

                User.getInstance().editMyAnimeList(old_status, KeyUtils.MY_ANIME_LIST_PATH[statusPos], newMyAnimeList);
            }
        });

        editDialog.onNeutral(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                progress.show();

                final MaterialDialog dia = dialog;
                User.getInstance().setMyAnimeListListener(new User.MyAnimeListListener() {
                    @Override
                    public void onSuccess() {
                        dia.dismiss();
                        progress.dismiss();
                        Toast.makeText(mContext, "DELETED", Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onDialogDismiss();
                        }
                    }

                    @Override
                    public void onFailed(String error) {

                    }
                });

                User.getInstance().deleteMyAnimeList(old_status, myAnimeList);
            }
        });

        editDialog.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });

        editDialog.show();
    }

    private ArrayList<Integer> getIntegerArray(int end) {
        ArrayList<Integer> intArray = new ArrayList<>();
        for (int start = 1; start <= end; start++) {
            intArray.add(start);
        }

        return intArray;
    }

    private String getOldStatusTextOfSpinner(String oldStatus) {
        switch (oldStatus) {
            case KeyUtils.STATUS_PLAN_TO_WATCH:
                return KeyUtils.STATUS_ARRAY[0];
            case KeyUtils.STATUS_WATCHING:
                return KeyUtils.STATUS_ARRAY[1];
            case KeyUtils.STATUS_COMPLETED:
                return KeyUtils.STATUS_ARRAY[2];
            case KeyUtils.STATUS_DROPPED:
                return KeyUtils.STATUS_ARRAY[3];
        }
        return null;
    }

    public void setListener(DialogManagerListener listener) {
        this.listener = listener;
    }
}
