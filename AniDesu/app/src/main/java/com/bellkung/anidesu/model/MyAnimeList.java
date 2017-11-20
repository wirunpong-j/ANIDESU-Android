package com.bellkung.anidesu.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BellKunG on 19/11/2017 AD.
 */

public class MyAnimeList implements Parcelable {
    private int anime_id;
    private int score;
    private int progress;
    private String note;

    public int getAnime_id() {
        return anime_id;
    }

    public void setAnime_id(int anime_id) {
        this.anime_id = anime_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.anime_id);
        dest.writeInt(this.score);
        dest.writeInt(this.progress);
        dest.writeString(this.note);
    }

    public MyAnimeList() {
    }

    protected MyAnimeList(Parcel in) {
        this.anime_id = in.readInt();
        this.score = in.readInt();
        this.progress = in.readInt();
        this.note = in.readString();
    }

    public static final Parcelable.Creator<MyAnimeList> CREATOR = new Parcelable.Creator<MyAnimeList>() {
        @Override
        public MyAnimeList createFromParcel(Parcel source) {
            return new MyAnimeList(source);
        }

        @Override
        public MyAnimeList[] newArray(int size) {
            return new MyAnimeList[size];
        }
    };
}
