package com.juztoss.dancemaker.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kirill on 2/28/2016.
 */
public class DanceSequence implements Parcelable {

    public static final String ALIAS = "DanceSequence";
    private int mId;
    private String mName;
    private List<DanceElement> mElements;
    private boolean mIsNew = false;

    public DanceSequence(String name, List<DanceElement> elements) {
        this(0, name, elements);
        mIsNew = true;
    }

    public DanceSequence(int id, String name, List<DanceElement> elements) {
        mElements = elements;
        mName = name;
        mId = id;
    }

    public boolean isNew() {
        return mIsNew;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getLength() {
        int length = 0;
        for (DanceElement el : mElements) {
            length += el.getLength();
        }

        return length;
    }

    public List<DanceElement> getElements() {
        return mElements;
    }

    public static DanceSequence generateNew(String name, int length, List<DanceElement> allElements) {
        DanceSequence seq = new DanceSequence(name, new ArrayList<DanceElement>());
        while(seq.getLength() < length)
        {
            int elementIndex = (int)(Math.random() * allElements.size());
            seq.getElements().add(allElements.get(elementIndex));
        }
        return seq;
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
        dest.writeArray(mElements.toArray());
    }

    public void delete(DanceElement element) {
        mElements.remove(element);
    }

    public void delete() throws DanceException {
        if (isNew())
            throw new DanceException("Element doesn't exist");

        DatabaseHelper.db().delete(DatabaseHelper.TABLE_SEQUENCES, DatabaseHelper._ID + "= ?", new String[]{Integer.toString(getId())});
    }

    public static final Creator<DanceSequence> CREATOR
            = new Creator<DanceSequence>() {
        @Override
        public DanceSequence createFromParcel(Parcel source) {
            Boolean isNew = source.readByte() != 0;
            int id = source.readInt();
            DanceElement[] elements = (DanceElement[]) source.readArray(DanceElement.class.getClassLoader());
            if (isNew) {

                return new DanceSequence(
                        source.readString(),
                        Arrays.asList(elements)
                );
            } else {
                return new DanceSequence(
                        id,
                        source.readString(),
                        Arrays.asList(elements)
                );
            }
        }

        @Override
        public DanceSequence[] newArray(int size) {
            return new DanceSequence[size];
        }
    };
}
