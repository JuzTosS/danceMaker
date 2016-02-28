package com.juztoss.dancemaker.model;

import java.util.ArrayList;

/**
 * Created by Kirill on 2/28/2016.
 */
public class DanceSequence extends ArrayList<DanceElement> {

    private int mLength;
    private String mId;
    private boolean mIsNew = false;

    public DanceSequence(String id, int length) {
        mLength = length;
    }

    public boolean isNew() {
        return mIsNew;
    }

    public String id() {
        return mId;
    }
}
