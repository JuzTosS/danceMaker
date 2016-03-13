package com.juztoss.dancemaker.model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill on 2/28/2016.
 */
public class DanceSpace {

    public DanceSpace(Context context) {
        new DatabaseHelper(context);
    }

    public List<DanceSequence> getSequences() {
        List<DanceSequence> sequences = new ArrayList<>();
        Cursor cursor = DatabaseHelper.db().query(DatabaseHelper.TABLE_SEQUENCES, new String[]{DatabaseHelper._ID, DatabaseHelper.SEQUENCES_NAME_COLUMN,
                        DatabaseHelper.SEQUENCES_ELEMENTS_COLUMN},
                null, null,
                null, null, null);
        cursor.moveToFirst();
        do {
            if (cursor.getCount() <= 0)
                break;

            String sequenceName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SEQUENCES_NAME_COLUMN));
            int sequenceId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper._ID));
            String elementsString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SEQUENCES_ELEMENTS_COLUMN));
            ArrayList<DanceElement> elements = new ArrayList<>();
            String[] elementsIds = elementsString.split(",");

            for (String elementId : elementsIds) {
                Cursor elCursor = DatabaseHelper.db().rawQuery("select * from " + DatabaseHelper.TABLE_ELEMENTS + " where " + DatabaseHelper._ID + " in (" + elementId + ")", null);
                if (elCursor.moveToFirst()) {
                    String elementName = elCursor.getString(elCursor.getColumnIndex(DatabaseHelper.ELEMENT_NAME_COLUMN));
                    int id = elCursor.getInt(elCursor.getColumnIndex(DatabaseHelper._ID));
                    int elementLength = elCursor.getInt(elCursor.getColumnIndex(DatabaseHelper.ELEMENT_LENGTH_COLUMN));
                    elements.add(new DanceElement(id, elementName, elementLength));
                }
                elCursor.close();
            }

            sequences.add(new DanceSequence(sequenceId, sequenceName, elements));
        } while (cursor.moveToNext());
        cursor.close();

        return sequences;
    }

    public List<DanceElement> getAllElements() {
        List<DanceElement> elements = new ArrayList<>();
        Cursor cursor = DatabaseHelper.db().query(DatabaseHelper.TABLE_ELEMENTS, new String[]{DatabaseHelper._ID, DatabaseHelper.ELEMENT_NAME_COLUMN,
                        DatabaseHelper.ELEMENT_LENGTH_COLUMN},
                null, null,
                null, null, null);

        cursor.moveToFirst();

        do {
            if (cursor.getCount() <= 0)
                break;

            String elementName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ELEMENT_NAME_COLUMN));
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper._ID));
            int elementLength = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ELEMENT_LENGTH_COLUMN));
            elements.add(new DanceElement(id, elementName, elementLength));
        } while (cursor.moveToNext());
        cursor.close();

        return elements;
    }
}
