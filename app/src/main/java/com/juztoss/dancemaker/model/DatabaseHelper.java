package com.juztoss.dancemaker.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


/**
 * Created by Kirill on 2/27/2016.
 */
class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns{

    private static SQLiteDatabase mDb;
    // имя базы данных
    private static final String DATABASE_NAME = "mydatabase.db";
    // версия базы данных
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_ELEMENTS = "elements";
    public static final String TABLE_SEQUENCES = "sequences";

    public static final String ELEMENT_NAME_COLUMN = "name";
    public static final String ELEMENT_LENGTH_COLUMN = "length";
    public static final String ELEMENT_INOOUTMATRIX_COLUMN = "inout";

    public static final String SEQUENCES_NAME_COLUMN = "name";
    public static final String SEQUENCES_ELEMENTS_COLUMN = "elements";

    private static final String CREATE_ELEMENTS_TABLE = "create table "
            + TABLE_ELEMENTS + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + ELEMENT_NAME_COLUMN + " text not null, "
            + ELEMENT_INOOUTMATRIX_COLUMN + " integer default 0"
            + ELEMENT_LENGTH_COLUMN + " integer); ";

    private static final String CREATE_SEQUENCES_TABLE = "create table "
            + TABLE_SEQUENCES + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + SEQUENCES_NAME_COLUMN
            + " text not null, " + SEQUENCES_ELEMENTS_COLUMN + " integer); ";

    public DatabaseHelper(Context context) throws DanceException{
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(mDb != null)
        {
            throw new DanceException("Only one instance of DatabaseHelper is allowed");
        }
        mDb = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ELEMENTS_TABLE);
        db.execSQL(CREATE_SEQUENCES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("SQLite", "Updating from " + oldVersion + " to " + newVersion);

        if(oldVersion == 2 && newVersion == 3)
        {
            db.execSQL("ALTER TABLE " + TABLE_ELEMENTS + " ADD COLUMN " + ELEMENT_INOOUTMATRIX_COLUMN + " integer default 0");
        }
        else
        {
            db.execSQL("DROP TABLE IF EXISTS '" + TABLE_ELEMENTS + "';");
            db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SEQUENCES + "';");
            onCreate(db);
        }
    }

    public static SQLiteDatabase db()
    {
        return mDb;
    }
}
