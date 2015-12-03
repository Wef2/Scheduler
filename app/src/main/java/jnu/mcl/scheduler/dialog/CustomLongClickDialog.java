package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import jnu.mcl.scheduler.R;

/**
 * Created by Kim on 2015-11-30.
 */
public class CustomLongClickDialog extends Dialog {

    private TextView modifyText, deleteText, copyText;

    public CustomLongClickDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_long_click);

        modifyText = (TextView)findViewById(R.id.modifyText);
        deleteText = (TextView)findViewById(R.id.deleteText);
        copyText = (TextView)findViewById(R.id.copyText);
    }

    public TextView getModifyText(){
        return modifyText;
    }

    public TextView getDeleteText(){
        return deleteText;
    }

    public TextView getCopyText(){
        return copyText;
    }

}
