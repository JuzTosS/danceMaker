package com.juztoss.dancemaker.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.activities.MainActivity;
import com.juztoss.dancemaker.views.DragListView;
import com.juztoss.dancemaker.adapters.DanceSequenceViewListAdapter;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceSequence;

/**
 * Created by Kirill on 2/27/2016.
 */
public class SequenceViewFragment extends ListFragment implements DanceSequenceViewListAdapter.SequenceViewDeleteListener {

    private DanceSequenceViewListAdapter mAdapter;
    private DanceSequence mSequence;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            savedInstanceState = getArguments();

        mSequence = (DanceSequence) savedInstanceState.get(DanceSequence.ALIAS);
        mAdapter = new DanceSequenceViewListAdapter(getActivity(), mSequence);
        mAdapter.setDeleteListener(this);
        setListAdapter(mAdapter);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(DanceSequence.ALIAS, mSequence);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sequences_view, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        final MainActivity activity = (MainActivity) getActivity();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showAddNewElementToSequence(mSequence);
            }
        });

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("");

        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(mSequence.getName());

        TextView length = (TextView) rootView.findViewById(R.id.length);

        String original = getString(R.string.length_with_number);
        length.setText(String.format(original, mSequence.getLength()));


        DragListView list = (DragListView)rootView.findViewById(android.R.id.list);
        list.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onDrop() {
                activity.getDanceSpace().save(mSequence);
            }
        });


        return rootView;
    }

    @Override
    public void onClick(DanceElement el) {
        ((MainActivity) getActivity()).showElement(el);
    }

    @Override
    public void onDelete(DanceElement element) {
        //TODO: remove onDelete, action is implemented in the adapter
//        try {
//            ((MainActivity) getActivity()).getDanceSpace().delete(element);
//        } catch (DanceException e) {
//            Toast.makeText(getActivity(), "Unfortunately element hasn't been deleted!", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ((MainActivity) getActivity()).showAllSequences(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
}