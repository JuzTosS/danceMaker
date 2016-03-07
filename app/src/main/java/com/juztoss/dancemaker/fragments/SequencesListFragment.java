package com.juztoss.dancemaker.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.activities.MainActivity;
import com.juztoss.dancemaker.model.DanceException;
import com.juztoss.dancemaker.model.DanceSequence;

/**
 * Created by Kirill on 2/27/2016.
 */
public class SequencesListFragment extends ListFragment implements SequenceListListener {

    private DanceSequenceListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sequences_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        final MainActivity activity = (MainActivity) getActivity();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showAddNewSequence();
            }
        });

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Sequences");



        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        DanceSequence seq = ((MainActivity) getActivity()).getDanceSpace().getSequences().get(position);
        ((MainActivity) getActivity()).showSequence(seq);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new DanceSequenceListAdapter(getActivity(), ((MainActivity) getActivity()).getDanceSpace().getSequences());
        mAdapter.setListener(this);
        setListAdapter(mAdapter);
    }

    @Override
    public void onDelete(DanceSequence sequence) {
        try {
            ((MainActivity) getActivity()).getDanceSpace().delete(sequence);
        } catch (DanceException e) {
            Toast.makeText(getActivity(), "Unfortunately sequence hasn't been deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(DanceSequence sequence) {
        ((MainActivity) getActivity()).showSequence(sequence);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
}