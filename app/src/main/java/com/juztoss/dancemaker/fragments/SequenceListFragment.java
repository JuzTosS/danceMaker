package com.juztoss.dancemaker.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.Toast;

import com.juztoss.dancemaker.activities.MainActivity;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceException;
import com.juztoss.dancemaker.model.DanceSequence;

/**
 * Created by Kirill on 2/27/2016.
 */
public class SequenceListFragment extends ListFragment implements SequenceDeleteListener {

    private DanceSequenceListAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new DanceSequenceListAdapter(getActivity(), ((MainActivity) getActivity()).getDanceSpace().getSequences());
        mAdapter.setDeleteListener(this);
        setListAdapter(mAdapter);
    }

    @Override
    public void onDelete(DanceSequence element) {
        try {
            ((MainActivity) getActivity()).getDanceSpace().delete(element);
        } catch (DanceException e) {
            Toast.makeText(getActivity(), "Unfortunately element hasn't been deleted!", Toast.LENGTH_SHORT).show();
        }
    }
}