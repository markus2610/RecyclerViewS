package com.omzi43kf.gwbinsr3nken.recyclerviews;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    private String[] itemProjection = {
            DataContract.ItemEntry._ID,
            DataContract.ItemEntry.COLUMN_ITEM_MAIN_TEXT,
            DataContract.ItemEntry.COLUMN_ITEM_SUB_TEXT,
            DataContract.ItemEntry.COLUMN_ITEM_POSITION,
            DataContract.ItemEntry.COLUMN_ITEM_STYLE,

    };
    private String[] archiveProjection = {
            DataContract.ArchiveEntry._ID,
            DataContract.ArchiveEntry.COLUMN_ARCHIVE_MAIN_TEXT,
            DataContract.ArchiveEntry.COLUMN_ARCHIVE_SUB_TEXT,
            DataContract.ArchiveEntry.COLUMN_ARCHIVE_POSITION,
            DataContract.ArchiveEntry.COLUMN_ARCHIVE_STYLE,

    };

    //    private Cursor itemCursor;
    private Cursor archiveCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_container, new SubFragment()).commit();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //        itemProvider();
//        archiveProvider();
        getContentResolver().delete(DataContract.ItemEntry.URI_TABLE, null,null);
        ExtendAsyncQueryHandler extendAsyncQueryHandler = new ExtendAsyncQueryHandler(getContentResolver());
//        extendAsyncQueryHandler.startDelete(5000,null, DataContract.ItemEntry.buildItemUri(4), null,null);
//        extendAsyncQueryHandler.startDelete(5000,null, DataContract.ItemEntry.buildItemUri(2), null,null);
        for (int i = 0; i < 20; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataContract.ItemEntry.COLUMN_ITEM_MAIN_TEXT, "item" + i);
            contentValues.put(DataContract.ItemEntry.COLUMN_ITEM_SUB_TEXT, "sub item" + i);
            contentValues.put(DataContract.ItemEntry.COLUMN_ITEM_POSITION, i);
            contentValues.put(DataContract.ItemEntry.COLUMN_ITEM_STYLE, 100);
            extendAsyncQueryHandler.startInsert(i, null, DataContract.ItemEntry.CONTENT_URI, contentValues);
        }






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Cursor mCursor = getContentResolver().query(DataContract.ItemEntry.CONTENT_URI, itemProjection, null, null, null);


            if (mCursor != null) {
                if (mCursor.moveToFirst()) {
                    do {
                        int _id = mCursor.getInt(mCursor.getColumnIndex(DataContract.ItemEntry._ID));
                        String mainText = mCursor.getString(mCursor.getColumnIndex(DataContract.ItemEntry.COLUMN_ITEM_MAIN_TEXT));
                        String subText = mCursor.getString(mCursor.getColumnIndex(DataContract.ItemEntry.COLUMN_ITEM_SUB_TEXT));

                        int position = mCursor.getInt(mCursor.getColumnIndex(DataContract.ItemEntry.COLUMN_ITEM_POSITION));
                        int style = mCursor.getInt(mCursor.getColumnIndex(DataContract.ItemEntry.COLUMN_ITEM_STYLE));

                        Log.d(LOG_TAG, "ID: " + _id +
                                " Maintext: " + mainText +
                                " Sub text: " + subText +
                                " POsition: " + position +
                                " Style: " + style);




                    } while (mCursor.moveToNext());
                }
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
