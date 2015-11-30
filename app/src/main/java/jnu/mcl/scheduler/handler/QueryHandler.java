package jnu.mcl.scheduler.handler;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import jnu.mcl.scheduler.listener.QueryListener;

/**
 * Created by 김 on 2015-11-29.
 */
public class QueryHandler extends AsyncQueryHandler {

    private ArrayList<QueryListener> queryListeners;
    private WeakReference<QueryListener> listener;

    public QueryHandler(Context context, QueryListener listener) {
        super(context.getContentResolver());
        setQueryListener(listener);
    }

    public void setQueryListener(QueryListener listener) {
        this.listener = new WeakReference<QueryListener>(listener);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        final QueryListener listener = this.listener.get();
        if (listener != null) {
            listener.onQueryComplete(token, cookie, cursor);
        } else if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        final QueryListener listener = this.listener.get();
        if (listener != null) {
            listener.onInsertComplete(token, cookie, uri);
        }
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        final QueryListener listener = this.listener.get();
        if (listener != null) {
            listener.onUpdateComplete(token, cookie, result);
        }
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        final QueryListener listener = this.listener.get();
        if (listener != null) {
            listener.onDeleteComplete(token, cookie, result);
        }
    }
}