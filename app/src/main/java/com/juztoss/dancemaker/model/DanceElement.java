package com.juztoss.dancemaker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kirill on 2/27/2016.
 */
public class DanceElement implements Parcelable {

    public static final String ALIAS = "DanceElement";
    private String mName;
    private int mId;
    private int mLength;
    private boolean mIsNew = false;

    private InOutMatrix mInOutMatrix;

    public DanceElement(String name, int length, InOutMatrix matrix) {
        this(0, name, length, matrix);
        mIsNew = true;
    }

    public DanceElement(int id, String name, int length, InOutMatrix matrix) {
        mId = id;
        mName = name;
        mLength = length;
        mInOutMatrix = matrix;
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
        dest.writeParcelable(mInOutMatrix, flags);
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
                        (InOutMatrix) source.readParcelable(InOutMatrix.class.getClassLoader())
                );
            } else {
                return new DanceElement(
                        source.readInt(),
                        source.readString(),
                        source.readInt(),
                        (InOutMatrix) source.readParcelable(InOutMatrix.class.getClassLoader())
                );
            }
        }

        @Override
        public DanceElement[] newArray(int size) {
            return new DanceElement[size];
        }
    };

    public InOutMatrix getInOutMatrix() {
        return mInOutMatrix;
    }

    public void setInOutMatrix(InOutMatrix inOutMatrix) {
        this.mInOutMatrix = inOutMatrix;
    }
}
