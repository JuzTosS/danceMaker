package com.juztoss.dancemaker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill on 2/28/2016.
 */
public class DanceSpace {

    public DanceSpace(Context context) throws DanceException{
        new DatabaseHelper(context);
    }

    public void save(DanceElement element) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ELEMENT_NAME_COLUMN, element.getName());
        values.put(DatabaseHelper.ELEMENT_LENGTH_COLUMN, element.getLength());
        values.put(DatabaseHelper.ELEMENT_INOOUTMATRIX_COLUMN, matrix2Int(element.getInOutMatrix()));

        if (element.isNew()) {
            DatabaseHelper.db().insert(DatabaseHelper.TABLE_ELEMENTS, DatabaseHelper.ELEMENT_NAME_COLUMN, values);
        } else {
            DatabaseHelper.db().update(DatabaseHelper.TABLE_ELEMENTS, values, DatabaseHelper._ID + "= ?", new String[]{Integer.toString(element.getId())});
        }
    }


    public void save(DanceSequence element) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SEQUENCES_NAME_COLUMN, element.getName());
        String elements = "";
        List<DanceElement> danceElements = element.getElements();
        for (DanceElement el : danceElements) {
            if (!elements.isEmpty())
                elements += ",";

            elements += el.getId();
        }
        values.put(DatabaseHelper.SEQUENCES_ELEMENTS_COLUMN, elements);

        if (element.isNew()) {
            DatabaseHelper.db().insert(DatabaseHelper.TABLE_SEQUENCES, DatabaseHelper.SEQUENCES_NAME_COLUMN, values);
        } else {
            int result = DatabaseHelper.db().update(DatabaseHelper.TABLE_SEQUENCES, values, DatabaseHelper._ID + "= ?", new String[]{Integer.toString(element.getId())});
            Log.d("DEBUG", "rows affected:" + result);
        }
    }

    private int matrix2Int(InOutMatrix matrix) {
        int result = 0;

        result |= matrix.inLeft ? 1 : 0;
        result |= matrix.inBoth ? 2 : 0;
        result |= matrix.inHello ? 4 : 0;
        result |= matrix.outLeft ? 8 : 0;
        result |= matrix.outBoth ? 16 : 0;
        result |= matrix.outHello ? 32 : 0;

        return result;
    }

    private InOutMatrix int2matrix(int value) {
        InOutMatrix result = new InOutMatrix();

        result.inLeft = (value & 1) > 0;
        result.inBoth = (value & 2) > 0;
        result.inHello = (value & 4) > 0;
        result.outLeft = (value & 8) > 0;
        result.outBoth = (value & 16) > 0;
        result.outHello = (value & 32) > 0;

        return result;
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
                DanceElement el = readElement(elementId);
                if(el != null)
                    elements.add(el);
            }

            sequences.add(new DanceSequence(sequenceId, sequenceName, elements));
        } while (cursor.moveToNext());
        cursor.close();

        return sequences;
    }

    private DanceElement readElement(String elementId) {
        Cursor elCursor = DatabaseHelper.db().rawQuery("select * from " + DatabaseHelper.TABLE_ELEMENTS + " where " + DatabaseHelper._ID + " in (" + elementId + ")", null);
        if (elCursor.moveToFirst()) {
            String elementName = elCursor.getString(elCursor.getColumnIndex(DatabaseHelper.ELEMENT_NAME_COLUMN));
            int id = elCursor.getInt(elCursor.getColumnIndex(DatabaseHelper._ID));
            int elementLength = elCursor.getInt(elCursor.getColumnIndex(DatabaseHelper.ELEMENT_LENGTH_COLUMN));
            int elementMatrix = elCursor.getInt(elCursor.getColumnIndex(DatabaseHelper.ELEMENT_INOOUTMATRIX_COLUMN));

            elCursor.close();
            return new DanceElement(id, elementName, elementLength, int2matrix(elementMatrix));
        }
        elCursor.close();
        return null;
    }

    public List<DanceElement> getAllElements() {
        List<DanceElement> elements = new ArrayList<>();
        Cursor cursor = DatabaseHelper.db().query(DatabaseHelper.TABLE_ELEMENTS, new String[]{DatabaseHelper._ID, DatabaseHelper.ELEMENT_NAME_COLUMN,
                        DatabaseHelper.ELEMENT_LENGTH_COLUMN, DatabaseHelper.ELEMENT_INOOUTMATRIX_COLUMN},
                null, null,
                null, null, null);

        cursor.moveToFirst();

        do {
            if (cursor.getCount() <= 0)
                break;

            String elementName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ELEMENT_NAME_COLUMN));
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper._ID));
            int elementLength = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ELEMENT_LENGTH_COLUMN));
            int elementMatrix = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ELEMENT_INOOUTMATRIX_COLUMN));
            elements.add(new DanceElement(id, elementName, elementLength, int2matrix(elementMatrix)));
        } while (cursor.moveToNext());
        cursor.close();

        return elements;
    }


}
