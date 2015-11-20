package com.kaleido.cesmarttracker.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Review implements Parcelable {
    private int rate;
    private String comment;

    public Review(int rate, String comment) {
        this.rate = rate;
        this.comment = comment;
    }

    protected Review(Parcel in) {
        rate = in.readInt();
        comment = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rate);
        dest.writeString(comment);
    }

    public int getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
