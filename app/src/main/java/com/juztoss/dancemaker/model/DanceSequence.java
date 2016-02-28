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
    private ArrayList<DanceElement> mElements;
    private boolean mIsNew = false;

    public DanceSequence(String id, String name, ArrayList<DanceElement> elements) {
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

    public void add()
    {

    }

    public List<DanceElement> getmElements() {
        return Collections.unmodifiableList(mElements);
    }
}
