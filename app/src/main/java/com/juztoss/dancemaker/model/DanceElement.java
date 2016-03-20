package com.juztoss.dancemaker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kirill on 2/27/2016.
 */
public class DanceElement implements Parcelable {

    public static int IN_HELLO = 1;
    public static int IN_LEFT = 2;
    public static int IN_BOTH = 4;
    public static int OUT_HELLO = 8;
    public static int OUT_LEFT = 16;
    public static int OUT_BOTH = 32;

    public static final String ALIAS = "DanceElement";
    private String mName;
    private int mId;
    private int mLength;
    private boolean mIsNew = false;

    private int mInOut;

    public DanceElement(String name, int length, int inOut) {
        this(0, name, length, inOut);
        mIsNew = true;
    }

    public DanceElement(int id, String name, int length, int inOut) {
        mId = id;
        mName = name;
        mLength = length;
        mInOut = inOut;
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

    public int getId() {
        return mId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mIsNew ? 1 : 0));
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mLength);
        dest.writeInt(mInOut);
    }

    public void delete() throws DanceException {
        if (isNew())
            throw new DanceException("Element doesn't exist");

        DatabaseHelper.db().delete(DatabaseHelper.TABLE_ELEMENTS, DatabaseHelper._ID + "= ?", new String[]{Integer.toString(getId())});
    }

    public static final Parcelable.Creator<DanceElement> CREATOR
            = new Parcelable.Creator<DanceElement>() {
        @Override
        public DanceElement createFromParcel(Parcel source) {
            Boolean isNew = source.readByte() != 0;
            if (isNew) {
                source.readInt();//Skipping id
                return new DanceElement(
                        source.readString(),
                        source.readInt(),
                        source.readInt()
                );
            } else {
                return new DanceElement(
                        source.readInt(),
                        source.readString(),
                        source.readInt(),
                        source.readInt()
                );
            }
        }

        @Override
        public DanceElement[] newArray(int size) {
            return new DanceElement[size];
        }
    };

    public int getInOut() {
        return mInOut;
    }

    public int getIn() {
        return mInOut & (IN_LEFT | IN_BOTH | IN_HELLO);
    }

    public int getOut() {
        return mInOut & (OUT_LEFT | OUT_BOTH | OUT_HELLO);
    }
}
