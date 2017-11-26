package com.bellkung.anidesu.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by BellKunG on 4/11/2017 AD.
 */

public class Token implements Parcelable {

    private String access_token;
    private String token_type;
    private long expires;
    private long expires_in;

    public Token(String access_token, String token_type, long expires, long expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires = expires;
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public long getExpires() {
        return expires;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public String getHeaderValuePresets(){
        return String.format("%s %s", token_type, access_token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeString(this.token_type);
        dest.writeLong(this.expires);
        dest.writeLong(this.expires_in);
    }

    protected Token(Parcel in) {
        this.access_token = in.readString();
        this.token_type = in.readString();
        this.expires = in.readLong();
        this.expires_in = in.readLong();
    }

    public static final Creator<Token> CREATOR = new Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel source) {
            return new Token(source);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}
