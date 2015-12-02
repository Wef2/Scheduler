package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.listener.CustomDialogClickListener;

/**
 * Created by Kim on 2015-11-30.
 */
public class EventLongClickDialog extends Dialog {

    private TextView modifyText, deleteText;

    public EventLongClickDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_event_long_click);

        modifyText = (TextView)findViewById(R.id.modifyText);
        deleteText = (TextView)findViewById(R.id.deleteText);
    }

    public TextView getModifyText(){
        return modifyText;
    }

    public TextView getDeleteText(){
        return deleteText;
    }

}
