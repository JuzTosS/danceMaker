package com.juztoss.dancemaker.fragments;

import android.app.ListFragment;
import android.os.Bundle;

import com.juztoss.dancemaker.model.DanceElement;

/**
 * Created by Kirill on 2/27/2016.
 */
public class SequenceListFragment extends ListFragment implements ElementDeleteListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDelete(DanceElement element) {

    }
}