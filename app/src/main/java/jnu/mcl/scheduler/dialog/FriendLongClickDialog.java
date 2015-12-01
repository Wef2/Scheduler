package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import jnu.mcl.scheduler.R;

/**
 * Created by Kim on 2015-12-01.
 */
public class FriendLongClickDialog extends Dialog {

    public FriendLongClickDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_friend_long_click);
    }
}
