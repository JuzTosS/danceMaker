package com.juztoss.dancemaker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


/**
 * Created by Kirill on 2/27/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns{

    // имя базы данных
    private static final String DATABASE_NAME = "mydatabase.db";
    // версия базы данных
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ELEMENTS = "elements";

    public static final String ELEMENT_NAME_COLUMN = "name";
    public static final String ELEMENT_LENGTH_COLUMN = "length";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + TABLE_ELEMENTS + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + ELEMENT_NAME_COLUMN
            + " text not null, " + ELEMENT_LENGTH_COLUMN + " integer); ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_ELEMENTS);
        // Создаём новую таблицу
        onCreate(db);
    }
}
