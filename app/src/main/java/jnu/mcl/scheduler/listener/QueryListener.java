package jnu.mcl.scheduler.listener;

import android.database.Cursor;
import android.net.Uri;

/**
 * Created by 김 on 2015-11-29.
 */

public interface QueryListener {
    void onQueryComplete(int token, Object cookie, Cursor cursor);

    void onInsertComplete(int token, Object cookie, Uri uri);

    void onUpdateComplete(int token, Object cookie, int result);

    void onDeleteComplete(int token, Object cookie, int result);
}
