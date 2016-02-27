package com.juztoss.dancemaker;

/**
 * Created by Kirill on 2/27/2016.
 */
public class DanceElement {

    String mName;
    String mId;
    int mLength;

    DanceElement(String id, String name, int length)
    {
        mId = id;
        mName = name;
        mLength = length;
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
