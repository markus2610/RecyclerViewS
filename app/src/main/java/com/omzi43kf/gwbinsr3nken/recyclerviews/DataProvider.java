package com.omzi43kf.gwbinsr3nken.recyclerviews;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by lgaji on 2015/06/08.
 */
public class DataProvider extends ContentProvider {


    private static final String LOG_TAG = DataProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private DataDbHelper mOpenHelper;

    static final int ITEM = 100;
    static final int ITEM_ID = 101;

    static final int ARCHIVE = 200;
    static final int ARCHIVE_ID = 201;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_ITEM, ITEM);
        matcher.addURI(authority, DataContract.PATH_ITEM + "/*", ITEM_ID);
        matcher.addURI(authority, DataContract.PATH_ARCHIVE, ARCHIVE);
        matcher.addURI(authority, DataContract.PATH_ARCHIVE + "/*", ARCHIVE_ID);

        return matcher;

    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new DataDbHelper(getContext());
        return true;
//        return false;
    }


    @Override
    public String getType(Uri uri) {


        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEM:
                return DataContract.ItemEntry.CONTENT_TYPE;
            case ITEM_ID:
                return DataContract.ItemEntry.CONTENT_ITEM_TYPE;
            case ARCHIVE:
                return DataContract.ArchiveEntry.CONTENT_TYPE;
            case ARCHIVE_ID:
                return DataContract.ArchiveEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


//        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {


        Log.v(LOG_TAG, "query(uri=" + uri);
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        Cursor retCursor;


        switch (match) {
            case ITEM:
                queryBuilder.setTables(DataContract.ItemEntry.TABLE_NAME);
                break;
            case ITEM_ID:
                queryBuilder.setTables(DataContract.ItemEntry.TABLE_NAME);
                String itemId = DataContract.ItemEntry.getItemId(uri);
                queryBuilder.appendWhere(DataContract.ItemEntry._ID + "=" + itemId);
                break;
            case ARCHIVE:
                queryBuilder.setTables(DataContract.ArchiveEntry.TABLE_NAME);

                break;
            case ARCHIVE_ID:
                queryBuilder.setTables(DataContract.ArchiveEntry.TABLE_NAME);
                String archiveId = DataContract.ArchiveEntry.getArchiveId(uri);
                queryBuilder.appendWhere(DataContract.ArchiveEntry._ID + "=" + archiveId);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;


//        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.v(LOG_TAG, "insert(uri=" + uri + ", values=" + values.toString());
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        final int match = sUriMatcher.match(uri);


        switch (match) {
            case ITEM:
                long _itemId = db.insert(DataContract.ItemEntry.TABLE_NAME, null, values);
                if (_itemId > 0)
                    returnUri = DataContract.ItemEntry.buildItemUri(_itemId);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;





            case ARCHIVE:
                long _archiveId = db.insert(DataContract.ArchiveEntry.TABLE_NAME, null, values);
                if (_archiveId > 0)
                    returnUri = DataContract.ArchiveEntry.buildArchiveUri(_archiveId);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);


                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;


//        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        Log.v(LOG_TAG, "delete(uri=" + uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
//        if (null == selection) selection = "1";

        if (uri.equals(DataContract.ItemEntry.URI_TABLE) || uri.equals(DataContract.ArchiveEntry.URI_TABLE)) {
            deleteDataBase();
            return 0;
        }

        switch (match) {

            case ITEM_ID:
                String itemId = DataContract.ItemEntry.getItemId(uri);
                String itemSelectionCriteria = DataContract.ItemEntry._ID + "=" + itemId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                rowsDeleted = db.delete(DataContract.ItemEntry.TABLE_NAME, itemSelectionCriteria, selectionArgs);

                break;

            case ARCHIVE_ID:
                String archiveId = DataContract.ArchiveEntry.getArchiveId(uri);
                String archiveSelectionCriteria = DataContract.ArchiveEntry._ID + "=" + archiveId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                rowsDeleted = db.delete(DataContract.ArchiveEntry.TABLE_NAME, archiveSelectionCriteria, selectionArgs);

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;

//        return 0;
    }

    private void deleteDataBase() {
        mOpenHelper.close();
        DataDbHelper.deleteDatabase(getContext());
        mOpenHelper = new DataDbHelper(getContext());
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.v(LOG_TAG, "update(uri=" + uri + ", values=" + values.toString());


        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated = 0;


        switch (match) {

            case ITEM_ID:
                String itemId = DataContract.ItemEntry.getItemId(uri);
                String itemSelectionCriteria = DataContract.ItemEntry._ID + "=" + itemId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                rowsUpdated = db.update(DataContract.ItemEntry.TABLE_NAME, values , itemSelectionCriteria, selectionArgs);
                break;

            case ARCHIVE_ID:
                String archiveId = DataContract.ArchiveEntry.getArchiveId(uri);
                String archiveSelectionCriteria = DataContract.ArchiveEntry._ID + "=" + archiveId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                rowsUpdated = db.update(DataContract.ArchiveEntry.TABLE_NAME, values , archiveSelectionCriteria, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;

//        return 0;
    }
}
