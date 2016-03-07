package com.juztoss.dancemaker.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.activities.MainActivity;
import com.juztoss.dancemaker.model.DanceException;
import com.juztoss.dancemaker.model.DanceSequence;
import com.juztoss.dancemaker.model.DanceSpace;

/**
 * Created by Kirill on 2/27/2016.
 */
public class AddNewSequenceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("New sequence");

        return inflater.inflate(R.layout.fragment_add_new_sequence, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        TextView nameField = (TextView) getActivity().findViewById(R.id.sequence_name);
        TextView lengthField = (TextView) getActivity().findViewById(R.id.sequence_length);

        MainActivity activity = (MainActivity) getActivity();


        try {
            String name = nameField.getText().toString();
            if (name.length() <= 0)
                throw new DanceException("Empty name!");

            DanceSpace space = activity.getDanceSpace();
            DanceSequence sequence = DanceSequence.generateNew(name, Integer.parseInt(lengthField.getText().toString()),space.getAllElements());
            sequence.save();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "The sequence hasn't been saved!", Toast.LENGTH_SHORT).show();
        }

        activity.showAllSequences(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        return false;
    }
}