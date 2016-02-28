package com.juztoss.dancemaker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juztoss.dancemaker.R;

/**
 * Created by Kirill on 2/27/2016.
 */
public class AddNewSequenceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_new_sequence, container, false);
    }
}