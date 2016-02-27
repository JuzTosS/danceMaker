package com.juztoss.dancemaker;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Kirill on 2/27/2016.
 */
public class ElementsListFragment extends ListFragment implements DeleteListener {

    DatabaseHelper mDatabaseHelper;
    private DanceElementListAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDatabaseHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.query(mDatabaseHelper.TABLE_ELEMENTS, new String[]{DatabaseHelper._ID, DatabaseHelper.ELEMENT_NAME_COLUMN,
                        DatabaseHelper.ELEMENT_LENGTH_COLUMN},
                null, null,
                null, null, null);

        cursor.moveToFirst();

        ArrayList<DanceElement> danceElements = new ArrayList<>();
        while (cursor.moveToNext()) {
            String elementName = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ELEMENT_NAME_COLUMN));
            String id = cursor.getString(cursor.getColumnIndex(mDatabaseHelper._ID));
            int elementLength = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.ELEMENT_LENGTH_COLUMN));
            danceElements.add(new DanceElement(id, elementName, elementLength));
        }


        mAdapter = new DanceElementListAdapter(getActivity(), danceElements);
        mAdapter.setDeleteListener(this);
        cursor.close();
        setListAdapter(mAdapter);
    }

    @Override
    public void onDelete(DanceElement element) {
        mDatabaseHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.delete(mDatabaseHelper.TABLE_ELEMENTS, mDatabaseHelper._ID + "= ?", new String[]{element.id()});
    }
}