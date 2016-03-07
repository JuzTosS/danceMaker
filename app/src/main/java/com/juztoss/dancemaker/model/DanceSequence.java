package com.juztoss.dancemaker.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kirill on 2/28/2016.
 */
public class DanceSequence implements Parcelable {

    public static final String ALIAS = "DanceSequence";
    private String mId;
    private String mName;
    private List<DanceElement> mElements;
    private boolean mIsNew = false;

    public DanceSequence(String name, List<DanceElement> elements) {
        this("", name, elements);
        mIsNew = true;
    }

    public DanceSequence(String id, String name, List<DanceElement> elements) {
        mElements = elements;
        mName = name;
        mId = id;
    }

    public boolean isNew() {
        return mIsNew;
    }

    public String getId() {
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

    public List<DanceElement> getmElements() {
        return mElements;
    }

    public static DanceSequence generateNew(String name, int length, List<DanceElement> allElements) {
        return new DanceSequence(name, allElements);
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
        dest.writeArray(mElements.toArray());
    }

    public void delete(DanceElement element) {
        mElements.remove(element);
        save();
    }

    public void save() {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SEQUENCES_NAME_COLUMN, getName());
        String elements = "";
        List<DanceElement> danceElements = getmElements();
        for (DanceElement el : danceElements) {
            if (!elements.isEmpty())
                elements += ",";

            elements += el.getId();
        }
        values.put(DatabaseHelper.SEQUENCES_ELEMENTS_COLUMN, elements);

        if (isNew()) {
            DatabaseHelper.db().insert(DatabaseHelper.TABLE_SEQUENCES, DatabaseHelper.ELEMENT_NAME_COLUMN, values);
        } else {
            DatabaseHelper.db().update(DatabaseHelper.TABLE_SEQUENCES, values, DatabaseHelper._ID + "= ?", new String[]{getId()});
        }
    }

    public void delete() throws DanceException {
        if (isNew())
            throw new DanceException("Element doesn't exist");

        DatabaseHelper.db().delete(DatabaseHelper.TABLE_SEQUENCES, DatabaseHelper._ID + "= ?", new String[]{getId()});
    }

    public static final Creator<DanceSequence> CREATOR
            = new Creator<DanceSequence>() {
        @Override
        public DanceSequence createFromParcel(Parcel source) {
            Boolean isNew = source.readByte() != 0;
            String id = source.readString();
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
