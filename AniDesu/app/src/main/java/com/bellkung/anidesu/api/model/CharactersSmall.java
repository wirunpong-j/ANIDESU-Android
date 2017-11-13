package com.bellkung.anidesu.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bellkung.anidesu.custom.FormatCustomManager;
import com.bellkung.anidesu.utils.KeyUtils;

/**
 * Created by BellKunG on 10/11/2017 AD.
 */

public class CharactersSmall implements Parcelable {

    private int id;
    private String name_first;
    private String name_last;
    private String image_url_lge;
    private String image_url_med;
    private String role;

    private boolean isNull;

    public CharactersSmall(boolean isNull) {
        this.isNull = isNull;
    }

    public int getId() {
        return id;
    }

    public String getName_first() {
        return FormatCustomManager.parseString(name_first);
    }

    public String getName_last() {
        return FormatCustomManager.parseString(name_last);
    }

    public String getImage_url_lge() {
        return FormatCustomManager.parseString(image_url_lge);
    }

    public String getImage_url_med() {
        return FormatCustomManager.parseString(image_url_med);
    }

    public String getRole() {
        return FormatCustomManager.parseString(role);
    }

    public String getFull_Name() {
        return convertText(this.name_last) + " " + convertText(this.name_first);
    }

    public boolean isNull() {
        return isNull;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name_first);
        dest.writeString(this.name_last);
        dest.writeString(this.image_url_lge);
        dest.writeString(this.image_url_med);
        dest.writeString(this.role);
        dest.writeByte(this.isNull ? (byte) 1 : (byte) 0);
    }

    public CharactersSmall() {
    }

    protected CharactersSmall(Parcel in) {
        this.id = in.readInt();
        this.name_first = in.readString();
        this.name_last = in.readString();
        this.image_url_lge = in.readString();
        this.image_url_med = in.readString();
        this.role = in.readString();
        this.isNull = in.readByte() != 0;
    }

    public static final Creator<CharactersSmall> CREATOR = new Creator<CharactersSmall>() {
        @Override
        public CharactersSmall createFromParcel(Parcel source) {
            return new CharactersSmall(source);
        }

        @Override
        public CharactersSmall[] newArray(int size) {
            return new CharactersSmall[size];
        }
    };

    private String convertText(String text) {
        if (text == null) {
            return KeyUtils.EMPTY_STR;
        }
        return text;
    }
}
