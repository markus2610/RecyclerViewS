package com.omzi43kf.gwbinsr3nken.recyclerviews;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgaji on 2015/06/09.
 */
public class ItemListLoader extends AsyncTaskLoader<List<Item>> {

    private static final String LOG_TAG =  ItemListLoader.class.getSimpleName();

    private List<Item> mItemList;
    private ContentResolver mContentResolver;
    private Cursor mCursor;


    private String[] itemProjection = {
            DataContract.ItemEntry._ID,
            DataContract.ItemEntry.COLUMN_ITEM_MAIN_TEXT,
            DataContract.ItemEntry.COLUMN_ITEM_SUB_TEXT,
            DataContract.ItemEntry.COLUMN_ITEM_POSITION,
            DataContract.ItemEntry.COLUMN_ITEM_STYLE,

    };

    public ItemListLoader(Context context, ContentResolver contentResolver) {
        super(context);
        mContentResolver = contentResolver;
    }


    @Override
    public List<Item> loadInBackground() {
//        return null;
        mItemList = new ArrayList<>();

//        String orderBy =  DataContract.ItemEntry.COLUMN_ITEM_POSITION + " DESC";

        mCursor = mContentResolver.query(DataContract.ItemEntry.CONTENT_URI, itemProjection, null, null, null);
//        mCursor = mContentResolver.query(DataContract.ItemEntry.CONTENT_URI, itemProjection, null, null, orderBy);


        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    int _id = mCursor.getInt(mCursor.getColumnIndex(DataContract.ItemEntry._ID));
                    String mainText = mCursor.getString(mCursor.getColumnIndex(DataContract.ItemEntry.COLUMN_ITEM_MAIN_TEXT));
                    String subText = mCursor.getString(mCursor.getColumnIndex(DataContract.ItemEntry.COLUMN_ITEM_SUB_TEXT));

                    int position = mCursor.getInt(mCursor.getColumnIndex(DataContract.ItemEntry.COLUMN_ITEM_POSITION));
                    int style  = mCursor.getInt(mCursor.getColumnIndex(DataContract.ItemEntry.COLUMN_ITEM_STYLE));

                    Log.d(LOG_TAG, "ID: " + _id +
                            " Maintext: " + mainText +
                            " Sub text: " + subText +
                            " POsition: " + position);


                    Item item = new Item(mainText, subText, _id);
                    item.setPosition(position);


                    mItemList.add(item);

                } while (mCursor.moveToNext());
            }
        }

        return  mItemList;

    }

    @Override
    public void deliverResult(List<Item> itemList) {
//        super.deliverResult(data);
        if (isReset()) {
            if (itemList != null) {
                mCursor.close();
            }
        }

        List<Item> oldTaskList = mItemList;
        mItemList = itemList;

        if (isStarted())  {
            super.deliverResult(itemList);
        }

        if (oldTaskList != null && mItemList != itemList) {
            mCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mItemList != null) {
            deliverResult(mItemList);
        }
        if (takeContentChanged() || mItemList == null) {
            forceLoad();
        }


    }


    @Override
    protected void onStopLoading() {
//        super.onStopLoading();
        cancelLoad();
    }


    @Override
    public void onCanceled(List<Item> data) {
        super.onCanceled(data);
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
    }
}
