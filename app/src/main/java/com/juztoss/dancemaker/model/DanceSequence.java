package com.juztoss.dancemaker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kirill on 2/28/2016.
 */
public class DanceSequence extends ArrayList<DanceElement> {

    private int mLength;
    private String mId;
    private String mName;
    private List<DanceElement> mElements;
    private boolean mIsNew = false;

    public DanceSequence(String name, List<DanceElement> elements) {
        mElements = elements;
        mName = name;
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
        return mLength;
    }

    public List<DanceElement> getmElements() {
        return mElements;
    }
}
