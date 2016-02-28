package com.juztoss.dancemaker.model;

/**
 * Created by Kirill on 2/27/2016.
 */
public class DanceElement {

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

    public String name() {
        return mName;
    }

    public int length() {
        return mLength;
    }

    public String id() {
        return mId;
    }
}
