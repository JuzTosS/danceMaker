package com.juztoss.dancemaker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.activities.MainActivity;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceException;

/**
 * Created by Kirill on 2/27/2016.
 */
public class AddNewElementFragment extends Fragment {

    private static final Integer[] LENGTHS = {2, 4, 8, 12, 16};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_new_element, container, false);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, LENGTHS);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner lengthSpinner = (Spinner) view.findViewById(R.id.element_length);
        lengthSpinner.setAdapter(adapter);

        lengthSpinner.setPrompt("Title");
        // выделяем элемент
        lengthSpinner.setSelection(1);


        lengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("New element");

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        TextView nameField = (TextView) getActivity().findViewById(R.id.element_name);
        Spinner lengthField = (Spinner) getActivity().findViewById(R.id.element_length);
        MainActivity activity = (MainActivity) getActivity();
        try {
            String name = nameField.getText().toString();
            if(name.length() <= 0)
                throw new DanceException("Empty name!");

            DanceElement newElement = new DanceElement(name, Integer.parseInt(lengthField.getSelectedItem().toString()));
            activity.getDanceSpace().save(newElement);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "The element hasn't been saved!", Toast.LENGTH_SHORT).show();
        }

        activity.showAllElements();
        return false;
    }


}