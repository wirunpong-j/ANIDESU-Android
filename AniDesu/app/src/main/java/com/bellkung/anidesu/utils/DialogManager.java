package com.bellkung.anidesu.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bellkung.anidesu.R;
import com.bellkung.anidesu.api.model.Series;
import com.bellkung.anidesu.model.MyAnimeList;
import com.bellkung.anidesu.model.Reviews;
import com.bellkung.anidesu.model.User;
import com.bellkung.anidesu.service.ReviewService;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by BellKunG on 20/11/2017 AD.
 */

public class DialogManager {

    private Context mContext;
    private final String ADD_COMPLETED = "Add Completed!";
    private final String ADD_COMPLETED_CONTENT = "You can see at My Anime List.";
    private final String EDIT_COMPLETED = "Edit Completed!";
    private final String EDIT_COMPLETED_CONTENT = "You can see at My Anime List.";
    private final String DELETE_COMPLETED = "Delete Completed!";
    private final String DELETE_COMPLETED_CONTENT = "My anime with you selected deleted.";
    private final String REVIEW_COMPLETED = "Review Completed!";
    private final String REVIEW_COMPLETED_CONTENT = "You can see your review at Anime Review.";
    private final String LOADING_TITLE = "Loading";

    private final String ADD_ANIME = "ADD : ";
    private final String EDIT_ANIME = "EDIT : ";
    private final String REVIEW_ANIME = "REVIEW : ";

    public DialogManager(Context context) {
        this.mContext = context;
    }


    public interface DialogManagerListener{
        void onDialogDismiss();
    }

    private DialogManagerListener listener;

    public void addMyAnimeListDialog(final Series series) {

        MaterialDialog.Builder addDialog = new MaterialDialog.Builder(this.mContext)
                .typeface(Typeface.SANS_SERIF,Typeface.SANS_SERIF)
                .title(ADD_ANIME + series.getTitle_romaji())
                .autoDismiss(false)
                .iconRes(R.drawable.ic_add_black)
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

                final SweetAlertDialog progressDialog = getLoadingDialog();
                progressDialog.show();

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
                User.getInstance().setMyAnimeAddedListener(new User.MyAnimeAddedListener() {
                    @Override
                    public void onAddedSuccess() {
                        dia.dismiss();
                        progressDialog.dismissWithAnimation();

                        getCompletedDialog(ADD_COMPLETED, ADD_COMPLETED_CONTENT).show();

                        if (listener != null) {
                            listener.onDialogDismiss();
                        }
                    }

                    @Override
                    public void onAddedFailed(String error) {

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
                .title(EDIT_ANIME + series.getTitle_romaji())
                .autoDismiss(false)
                .iconRes(R.drawable.ic_edit_black)
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
                final SweetAlertDialog progressDialog = getLoadingDialog();
                progressDialog.show();

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

                User.getInstance().setMyAnimeEditedListener(new User.MyAnimeEditedListener() {
                    @Override
                    public void onEditedSuccess() {
                        dia.dismiss();
                        progressDialog.dismissWithAnimation();

                        getCompletedDialog(EDIT_COMPLETED, EDIT_COMPLETED_CONTENT).show();

                        if (listener != null) {
                            listener.onDialogDismiss();
                        }
                    }

                    @Override
                    public void onEditedFailed(String error) {

                    }
                });
                User.getInstance().editMyAnimeList(old_status, KeyUtils.MY_ANIME_LIST_PATH[statusPos], newMyAnimeList);
            }
        });

        editDialog.onNeutral(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                final SweetAlertDialog progressDialog = getLoadingDialog();
                progressDialog.show();

                final MaterialDialog dia = dialog;
                User.getInstance().setMyAnimeDeletedListener(new User.MyAnimeDeletedListener() {
                    @Override
                    public void onDeletedSuccess() {
                        dia.dismiss();
                        progressDialog.dismissWithAnimation();

                        getCompletedDialog(DELETE_COMPLETED, DELETE_COMPLETED_CONTENT).show();

                        if (listener != null) {
                            listener.onDialogDismiss();
                        }
                    }

                    @Override
                    public void onDeletedFailed(String error) {

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

    public void reviewDialog(final Series series) {
        MaterialDialog.Builder reviewDialog = new MaterialDialog.Builder(this.mContext)
                .typeface(Typeface.SANS_SERIF,Typeface.SANS_SERIF)
                .title(REVIEW_ANIME + series.getTitle_romaji())
                .autoDismiss(false)
                .iconRes(R.drawable.ic_star_black)
                .customView(R.layout.dialog_fragment_review, true)
                .buttonRippleColorRes(R.color.colorAccent)
                .positiveColorRes(R.color.colorStateBlue)
                .positiveText(R.string.dia_edit_btn)
                .negativeText(R.string.dia_cancel_btn);

        MaterialDialog reviewDialogMD = reviewDialog.build();
        final RatingBar ratingBar = (RatingBar) reviewDialogMD.findViewById(R.id.ratingBar);
        final EditText reviewEditText = (EditText) reviewDialogMD.findViewById(R.id.reviewEditText);

        reviewDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                final SweetAlertDialog progressDialog = getLoadingDialog();
                progressDialog.show();

                Reviews review = new Reviews();
                review.setUid(User.getInstance().getUid());
                review.setRating(ratingBar.getRating());
                review.setReview_date(FormatCustomManager.getCurrentDateTime());
                review.setText(String.valueOf(reviewEditText.getText()));
                review.setAnime_id(String.valueOf(series.getId()));

                final MaterialDialog dia = dialog;
                ReviewService reviewService = new ReviewService();
                reviewService.setCreateReviewListener(new ReviewService.CreateReviewListener() {
                    @Override
                    public void onCreateReviewCompleted() {
                        dia.dismiss();
                        progressDialog.dismissWithAnimation();

                        getCompletedDialog(REVIEW_COMPLETED, REVIEW_COMPLETED_CONTENT).show();

                        if (listener != null) {
                            listener.onDialogDismiss();
                        }
                    }

                    @Override
                    public void onCreateReviewFailed(String errorText) {

                    }
                });
                reviewService.createReview(review);
            }
        });

        reviewDialog.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });


        reviewDialog.show();
    }

    private SweetAlertDialog getLoadingDialog() {
        SweetAlertDialog progressDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(LOADING_TITLE);
        progressDialog.setCancelable(true);
        return progressDialog;
    }

    private SweetAlertDialog getCompletedDialog(String title, String content) {
        return new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(content);
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
