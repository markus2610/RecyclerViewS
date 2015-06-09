package com.omzi43kf.gwbinsr3nken.recyclerviews;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Message;
import android.util.Log;

/**
 * Created by lgaji on 2015/06/08.
 */
public class ExtendAsyncQueryHandler extends AsyncQueryHandler{
    private static final String LOG_TAG = ExtendAsyncQueryHandler.class.getSimpleName();

    public ExtendAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        super.onInsertComplete(token, cookie, uri);
        Log.d(LOG_TAG, "insert complete"  + " token: " + token);
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        super.onUpdateComplete(token, cookie, result);
        Log.d(LOG_TAG, "update complete"  + " token: " + token);
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        super.onDeleteComplete(token, cookie, result);
        Log.d(LOG_TAG, "delete complete," + " token: " + token + "result: " + result);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
