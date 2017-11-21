package com.bellkung.anidesu.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bellkung.anidesu.utils.KeyUtils;

/**
 * Created by BellKunG on 9/11/2017 AD.
 */

public class Airing implements Parcelable {

    private String time;
    private int countdown;
    private int next_episode;

    private final int second = 3600;
    private final String next_ep_text = "EP %s Airing in %sh";

    public String getTime() {
        if (this.time == null) {
            return KeyUtils.NULL_TEXT;
        }
        return time;
    }

    public int getCountdown() {
        return countdown;
    }

    public int getNext_episode() {
        return next_episode;
    }

    public String getNextEPandTime() {
        if (next_episode == KeyUtils.NUM_DEFAULT) {
            return KeyUtils.NULL_TEXT;
        }

        return String.format(next_ep_text, next_episode, String.valueOf(countdown / second));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeInt(this.countdown);
        dest.writeInt(this.next_episode);
    }

    public Airing() {
    }

    protected Airing(Parcel in) {
        this.time = in.readString();
        this.countdown = in.readInt();
        this.next_episode = in.readInt();
    }

    public static final Parcelable.Creator<Airing> CREATOR = new Parcelable.Creator<Airing>() {
        @Override
        public Airing createFromParcel(Parcel source) {
            return new Airing(source);
        }

        @Override
        public Airing[] newArray(int size) {
            return new Airing[size];
        }
    };
}
