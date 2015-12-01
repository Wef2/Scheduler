package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import jnu.mcl.scheduler.R;

/**
 * Created by Kim on 2015-12-01.
 */
public class EventTimeDialog  extends Dialog {
    public EventTimeDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_event_time);
    }
}
