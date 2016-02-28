package com.juztoss.dancemaker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
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
        Cursor cursor = mDb.query(mDatabaseHelper.TABLE_ELEMENTS, new String[]{DatabaseHelper._ID, DatabaseHelper.ELEMENT_NAME_COLUMN,
                        DatabaseHelper.ELEMENT_LENGTH_COLUMN},
                null, null,
                null, null, null);

        cursor.moveToFirst();

        mAllElements = new ArrayList<>();
        while (cursor.moveToNext()) {
            String elementName = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.ELEMENT_NAME_COLUMN));
            String id = cursor.getString(cursor.getColumnIndex(mDatabaseHelper._ID));
            int elementLength = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.ELEMENT_LENGTH_COLUMN));
            mAllElements.add(new DanceElement(id, elementName, elementLength));
        }

        cursor.close();

        mSequences = new ArrayList<>();
    }

    public List<DanceSequence> getSequences() {
        return (List<DanceSequence>)mSequences.clone();
    }

    public List<DanceElement> getAllElements() {
        return (List<DanceElement>)mAllElements.clone();
    }

    public void save(DanceElement element) throws DanceException {
        ContentValues values = new ContentValues();
        values.put(mDatabaseHelper.ELEMENT_NAME_COLUMN, element.name());
        values.put(mDatabaseHelper.ELEMENT_LENGTH_COLUMN, element.length());

        if (element.isNew()) {
            mDb.insert(mDatabaseHelper.TABLE_ELEMENTS, mDatabaseHelper.ELEMENT_NAME_COLUMN, values);
        } else {
            mDb.update(mDatabaseHelper.TABLE_ELEMENTS, values, mDatabaseHelper._ID + "= ?", new String[]{element.id()});
        }

        updateLists();
    }

    public void save(DanceSequence sequence) throws DanceException {

    }

    public void delete(DanceElement element) throws DanceException {
        if (element.isNew()) {
            throw new DanceException("Element doesn't exist");
        }

        mDb.delete(mDatabaseHelper.TABLE_ELEMENTS, mDatabaseHelper._ID + "= ?", new String[]{element.id()});
        updateLists();
    }

    public void delete(DanceSequence sequence) throws DanceException {
        if (sequence.isNew())
            throw new DanceException("Element doesn't exist");

//        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
//        db.delete(mDatabaseHelper.TABLE_ELEMENTS, mDatabaseHelper._ID + "= ?", new String[]{sequence.id()});
    }


}
