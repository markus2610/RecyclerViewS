package com.omzi43kf.gwbinsr3nken.recyclerviews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lgaji on 2015/06/08.
 */
public class DataDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "data.db";

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + DataContract.ItemEntry.TABLE_NAME + " (" +
                DataContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataContract.ItemEntry.COLUMN_ITEM_MAIN_TEXT + " TEXT NOT NULL," +
                DataContract.ItemEntry.COLUMN_ITEM_SUB_TEXT + " TEXT NOT NULL," +

                DataContract.ItemEntry.COLUMN_ITEM_POSITION + " INTEGER NOT NULL," +
                DataContract.ItemEntry.COLUMN_ITEM_STYLE + " INTEGER NOT NULL" +")";





        final String SQL_CREATE_ARCHIVE_TABLE = "CREATE TABLE " + DataContract.ArchiveEntry.TABLE_NAME + " (" +
                DataContract.ArchiveEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataContract.ArchiveEntry.COLUMN_ARCHIVE_MAIN_TEXT + " TEXT NOT NULL," +
                DataContract.ArchiveEntry.COLUMN_ARCHIVE_SUB_TEXT + " TEXT NOT NULL," +
                DataContract.ArchiveEntry.COLUMN_ARCHIVE_POSITION + " INTEGER NOT NULL," +
                DataContract.ArchiveEntry.COLUMN_ARCHIVE_STYLE + " INTEGER NOT NULL" +")";


        db.execSQL(SQL_CREATE_ITEM_TABLE);
        db.execSQL(SQL_CREATE_ARCHIVE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.ItemEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.ArchiveEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
