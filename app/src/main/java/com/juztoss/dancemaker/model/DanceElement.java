package com.juztoss.dancemaker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kirill on 2/27/2016.
 */
public class DanceElement implements Parcelable {

    private String mName;
    private String mId;
    private int mLength;
    private boolean mIsNew = false;

    public DanceElement(String name, int length) {
        mIsNew = true;
        mName = name;
        mLength = length;
    }

    public DanceElement(String id, String name, int length) {
        mId = id;
        mName = name;
        mLength = length;
    }

    public boolean isNew() {
        return mIsNew;
    }

    public String getName() {
        return mName;
    }

    public int getLength() {
        return mLength;
    }

    public String getId() {
        return mId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mIsNew ? 1 : 0));
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeInt(mLength);
    }

    public static final Parcelable.Creator<DanceElement> CREATOR
            = new Parcelable.Creator<DanceElement>() {
        @Override
        public DanceElement createFromParcel(Parcel source) {
            Boolean isNew = source.readByte() != 0;
            if (isNew) {
                source.readString();//Skipping id
                return new DanceElement(
                        source.readString(),
                        source.readInt()
                );
            } else {
                return new DanceElement(
                        source.readString(),
                        source.readString(),
                        source.readInt()
                );
            }
        }

        @Override
        public DanceElement[] newArray(int size) {
            return new DanceElement[size];
        }
    };
}
