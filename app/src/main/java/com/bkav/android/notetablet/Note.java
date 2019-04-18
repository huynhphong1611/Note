package com.bkav.android.notetablet;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Note implements Parcelable  {
    private int mImportant;
    private int mId;
    private String mTitle;
    private String mDetail;
    private String mDate;
    private String mPathImg;

    public String getmPathImg() {
        return mPathImg;
    }

    public void setmPathImg(String mPathImg) {
        this.mPathImg = mPathImg;
    }

    public Note(int mImportant, int mId, String mTitle, String mDetail, String mDate, String mPathImg) {
        this.mImportant = mImportant;
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
        this.mPathImg = mPathImg;
    }

    public Note() {
    }

    public Note(String mTitle, String mDetail) {
        this.mTitle = mTitle;
        this.mDetail = mDetail;
    }

    public Note(String mTitle, String mDetail, String mDate) {
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
    }

    public Note(int mImportant, String mTitle, String mDetail, String mDate) {
        this.mImportant = mImportant;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
    }

    public Note(int mImportant, String mTitle, String mDetail, String mDate, String mPathImg) {
        this.mImportant = mImportant;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
        this.mPathImg = mPathImg;
    }

    public Note(int mImportant, int mId, String mTitle, String mDetail, String mDate) {
        this.mImportant = mImportant;
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
    }

    public int getmImportant() {
        return mImportant;
    }

    public void setmImportant(int mImportant) {
        this.mImportant = mImportant;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDetail() {
        return mDetail;
    }

    protected Note(Parcel in) {
        mImportant = in.readInt();
        mId = in.readInt();
        mTitle = in.readString();
        mDetail = in.readString();
        mDate = in.readString();
        mPathImg = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImportant);
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mDetail);
        dest.writeString(mDate);
        dest.writeString(mPathImg);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
