package com.juztoss.dancemaker.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.activities.MainActivity;
import com.juztoss.dancemaker.adapters.DanceElementForSeqListAdapter;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceSequence;

/**
 * Created by Kirill on 2/27/2016.
 */
public class ElementsListForSeqFragment extends ListFragment implements DanceElementForSeqListAdapter.ElementListListener{

    private DanceSequence mSequence;
    private DanceElementForSeqListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            savedInstanceState = getArguments();

        if (savedInstanceState != null)
            mSequence = (DanceSequence) savedInstanceState.get(DanceSequence.ALIAS);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_elements_list_for_seq, container, false);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Select an element");
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new DanceElementForSeqListAdapter(getActivity(), ((MainActivity) getActivity()).getDanceSpace().getAllElements());
        mAdapter.setElementListener(this);
        setListAdapter(mAdapter);
    }

    @Override
    public void onClick(DanceElement element) {
        mSequence.getElements().add(element);
        mSequence.save();
        MainActivity activity = (MainActivity) getActivity();
        activity.showSequence(mSequence);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
}