package com.juztoss.dancemaker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.juztoss.dancemaker.R;

/**
 * Created by Kirill on 2/27/2016.
 */
public class AddNewElementFragment extends Fragment {

    private static final Integer[] LENGTHS = {2, 4, 8, 12, 16};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_add_new_element, container, false);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, LENGTHS);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner lengthSpinner = (Spinner)view.findViewById(R.id.element_length);
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

        return view;
    }
}