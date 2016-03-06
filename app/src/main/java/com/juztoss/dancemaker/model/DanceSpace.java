package com.juztoss.dancemaker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill on 2/28/2016.
 */
public class DanceSpace {

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb;

    private ArrayList<DanceElement> mAllElements;
    private ArrayList<DanceSequence> mSequences;

    public DanceSpace(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);

        mDb = mDatabaseHelper.getWritableDatabase();
        updateLists();

    }

    private void updateLists() {
        mAllElements = new ArrayList<>();
        Cursor cursor = mDb.query(DatabaseHelper.TABLE_ELEMENTS, new String[]{DatabaseHelper._ID, DatabaseHelper.ELEMENT_NAME_COLUMN,
                        DatabaseHelper.ELEMENT_LENGTH_COLUMN},
                null, null,
                null, null, null);

        cursor.moveToFirst();

        do {
            if(cursor.getCount() <= 0)
                break;

            String elementName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ELEMENT_NAME_COLUMN));
            String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper._ID));
            int elementLength = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ELEMENT_LENGTH_COLUMN));
            mAllElements.add(new DanceElement(id, elementName, elementLength));
        } while (cursor.moveToNext());
        cursor.close();

        mSequences = new ArrayList<>();
        cursor = mDb.query(DatabaseHelper.TABLE_SEQUENCES, new String[]{DatabaseHelper._ID, DatabaseHelper.SEQUENCES_NAME_COLUMN,
                        DatabaseHelper.SEQUENCES_ELEMENTS_COLUMN},
                null, null,
                null, null, null);
        cursor.moveToFirst();
        do {
            if(cursor.getCount() <= 0)
                break;

            String sequenceName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SEQUENCES_NAME_COLUMN));
            String sequenceId = cursor.getString(cursor.getColumnIndex(DatabaseHelper._ID));
            String elementsString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SEQUENCES_ELEMENTS_COLUMN));
            ArrayList<DanceElement> elements = new ArrayList<>();
            Cursor elCursor = mDb.rawQuery("select * from " + DatabaseHelper.TABLE_ELEMENTS + " where " + DatabaseHelper._ID + " in (" + elementsString + ")", null);
            elCursor.moveToFirst();
            do {
                String elementName = elCursor.getString(elCursor.getColumnIndex(DatabaseHelper.ELEMENT_NAME_COLUMN));
                String id = elCursor.getString(elCursor.getColumnIndex(DatabaseHelper._ID));
                int elementLength = elCursor.getInt(elCursor.getColumnIndex(DatabaseHelper.ELEMENT_LENGTH_COLUMN));
                elements.add(new DanceElement(id, elementName, elementLength));
            } while (elCursor.moveToNext());
            elCursor.close();
            mSequences.add(new DanceSequence(sequenceId, sequenceName, elements));
        } while (cursor.moveToNext());
        cursor.close();
    }

    public List<DanceSequence> getSequences() {
        return (List<DanceSequence>) mSequences.clone();
    }

    public List<DanceElement> getAllElements() {
        return (List<DanceElement>) mAllElements.clone();
    }

    public void save(DanceElement element) throws DanceException {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ELEMENT_NAME_COLUMN, element.name());
        values.put(DatabaseHelper.ELEMENT_LENGTH_COLUMN, element.length());

        if (element.isNew()) {
            mDb.insert(DatabaseHelper.TABLE_ELEMENTS, DatabaseHelper.ELEMENT_NAME_COLUMN, values);
        } else {
            mDb.update(DatabaseHelper.TABLE_ELEMENTS, values, DatabaseHelper._ID + "= ?", new String[]{element.getId()});
        }

        updateLists();
    }

    public void save(DanceSequence sequence) throws DanceException {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SEQUENCES_NAME_COLUMN, sequence.getName());
        String elements = "";
        List<DanceElement> danceElements = sequence.getmElements();
        for (DanceElement el : danceElements) {
            if (!elements.isEmpty())
                elements += ",";

            elements += el.getId();
        }
        values.put(DatabaseHelper.SEQUENCES_ELEMENTS_COLUMN, elements);

        if (sequence.isNew()) {
            mDb.insert(DatabaseHelper.TABLE_SEQUENCES, DatabaseHelper.ELEMENT_NAME_COLUMN, values);
        } else {
            mDb.update(DatabaseHelper.TABLE_SEQUENCES, values, DatabaseHelper._ID + "= ?", new String[]{sequence.getId()});
        }

        updateLists();
    }

    public void delete(DanceElement element) throws DanceException {
        if (element.isNew()) {
            throw new DanceException("Element doesn't exist");
        }

        mDb.delete(DatabaseHelper.TABLE_ELEMENTS, DatabaseHelper._ID + "= ?", new String[]{element.getId()});
        updateLists();
    }

    public void delete(DanceSequence sequence) throws DanceException {
        if (sequence.isNew())
            throw new DanceException("Element doesn't exist");

//        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
//        db.delete(mDatabaseHelper.TABLE_ELEMENTS, mDatabaseHelper._ID + "= ?", new String[]{sequence.getId()});
    }


}
