package com.omzi43kf.gwbinsr3nken.recyclerviews;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.io.ByteArrayInputStream;

/**
 * Created by lgaji on 2015/06/08.
 */
public class DataContract {
    private static final String LOG_TAG = DataContract.class.getSimpleName();


    public static final String CONTENT_AUTHORITY = "com.omzi43kf.gwbinsr3nken.recyclerviews.provider";
    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_ITEM = "item";
    public static final String PATH_ARCHIVE = "archive";


    public static final class ItemEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEM).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "vnd." + CONTENT_AUTHORITY + "." + PATH_ITEM;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + "vnd." + CONTENT_AUTHORITY + "." + PATH_ITEM;

        //Table name
        public static final String TABLE_NAME = "item";

        public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString() + "/" + PATH_ITEM);

        //column name
//        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_ITEM_MAIN_TEXT = "item_main_text";
        public static final String COLUMN_ITEM_SUB_TEXT = "item_sub_text";
        public static final String COLUMN_ITEM_POSITION = "item_position";
        public static final String COLUMN_ITEM_STYLE = "item_style";


        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);


        }

        public static String getItemId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class ArchiveEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARCHIVE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "vnd." + CONTENT_AUTHORITY + "." + PATH_ARCHIVE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + "vnd." + CONTENT_AUTHORITY + "." + PATH_ARCHIVE;

        //Table name
        public static final String TABLE_NAME = "archive";

        public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString() + "/" + PATH_ARCHIVE);

        //column name
//        public static final String COLUMN_ITEM_NAME = "archive_name";
        public static final String COLUMN_ARCHIVE_MAIN_TEXT = "archive_main_text";
        public static final String COLUMN_ARCHIVE_SUB_TEXT = "archive_sub_text";
        public static final String COLUMN_ARCHIVE_POSITION = "archive_position";
        public static final String COLUMN_ARCHIVE_STYLE = "archive_style";


        public static Uri buildArchiveUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);


        }

        public static String getArchiveId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

}
