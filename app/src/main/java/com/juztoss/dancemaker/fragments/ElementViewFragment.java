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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.activities.MainActivity;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceException;
import com.juztoss.dancemaker.model.InOutMatrix;

import java.util.ArrayList;

/**
 * Created by Kirill on 2/27/2016.
 */
public class ElementViewFragment extends Fragment {

    private static final ArrayList<Integer> LENGTHS = new ArrayList<Integer>() {{
        add(2);
        add(4);
        add(8);
        add(12);
        add(16);
    }};

    private DanceElement mElement;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState == null)
            savedInstanceState = getArguments();

        if (savedInstanceState != null)
            mElement = (DanceElement) savedInstanceState.get(DanceElement.ALIAS);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_new_element, container, false);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, LENGTHS);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner lengthSpinner = (Spinner) view.findViewById(R.id.element_length);
        lengthSpinner.setAdapter(adapter);


        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        if (mElement != null) {
            if (actionBar != null)
                actionBar.setTitle("Element");

            TextView nameField = (TextView) view.findViewById(R.id.element_name);
            Spinner lengthField = (Spinner) view.findViewById(R.id.element_length);
            nameField.setText(mElement.getName());
            int spinnerIndex = LENGTHS.indexOf(mElement.getLength());
            if (spinnerIndex <= 0) spinnerIndex = 1;
            lengthField.setSelection(spinnerIndex, false);

            InOutMatrix matrix = mElement.getInOutMatrix();

            ((CheckBox)view.findViewById(R.id.checkBoxInHello)).setChecked(matrix.inHello);
            ((CheckBox)view.findViewById(R.id.checkBoxInBoth)).setChecked(matrix.inBoth);
            ((CheckBox)view.findViewById(R.id.checkBoxInLeft)).setChecked(matrix.inLeft);

            ((CheckBox)view.findViewById(R.id.checkBoxOutHello)).setChecked(matrix.outHello);
            ((CheckBox)view.findViewById(R.id.checkBoxOutBoth)).setChecked(matrix.outBoth);
            ((CheckBox)view.findViewById(R.id.checkBoxOutLeft)).setChecked(matrix.outLeft);

        } else {
            if (actionBar != null)
                actionBar.setTitle("New element");

            lengthSpinner.setSelection(1);
        }

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
            if (name.length() <= 0)
                throw new DanceException("Empty name!");

            InOutMatrix matrix = new InOutMatrix();

            matrix.inHello = ((CheckBox)getActivity().findViewById(R.id.checkBoxInHello)).isChecked();
            matrix.inBoth = ((CheckBox)getActivity().findViewById(R.id.checkBoxInBoth)).isChecked();
            matrix.inLeft = ((CheckBox)getActivity().findViewById(R.id.checkBoxInLeft)).isChecked();

            matrix.outHello = ((CheckBox)getActivity().findViewById(R.id.checkBoxOutHello)).isChecked();
            matrix.outBoth = ((CheckBox)getActivity().findViewById(R.id.checkBoxOutBoth)).isChecked();
            matrix.outLeft = ((CheckBox)getActivity().findViewById(R.id.checkBoxOutLeft)).isChecked();

            DanceElement element;
            if (mElement == null)
                element = new DanceElement(name, Integer.parseInt(lengthField.getSelectedItem().toString()), matrix);
            else
                element = new DanceElement(mElement.getId(), name, Integer.parseInt(lengthField.getSelectedItem().toString()), matrix);

            activity.getDanceSpace().save(element);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "The element hasn't been saved!", Toast.LENGTH_SHORT).show();
        }

        activity.showAllElements(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        return false;
    }


}