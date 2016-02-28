package com.juztoss.dancemaker.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.Toast;

import com.juztoss.dancemaker.activities.MainActivity;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceException;

/**
 * Created by Kirill on 2/27/2016.
 */
public class ElementsListFragment extends ListFragment implements ElementDeleteListener {

    private DanceElementListAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new DanceElementListAdapter(getActivity(), ((MainActivity) getActivity()).getDanceSpace().getAllElements());
        mAdapter.setDeleteListener(this);
        setListAdapter(mAdapter);
    }

    @Override
    public void onDelete(DanceElement element) {
        try {
            ((MainActivity) getActivity()).getDanceSpace().delete(element);
        } catch (DanceException e) {
            Toast.makeText(getActivity(), "Unfortunately element hasn't been deleted!", Toast.LENGTH_SHORT).show();
        }
    }
}