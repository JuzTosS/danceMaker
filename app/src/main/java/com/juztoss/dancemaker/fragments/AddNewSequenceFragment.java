package com.juztoss.dancemaker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.activities.MainActivity;

/**
 * Created by Kirill on 2/27/2016.
 */
public class AddNewSequenceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("New sequence");

        return inflater.inflate(R.layout.fragment_add_new_sequence, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_save, menu);
    }
}