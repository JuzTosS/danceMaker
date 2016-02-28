package com.juztoss.dancemaker.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.juztoss.dancemaker.AddNewElementFragment;
import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.activities.MainActivity;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceException;

/**
 * Created by Kirill on 2/27/2016.
 */
public class ElementsListFragment extends ListFragment implements ElementDeleteListener {

    private DanceElementListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_elements_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        final Activity activity = getActivity();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddNewElementFragment();
                FragmentManager fragmentManager = activity.getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

            }
        });

        return rootView;
    }
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