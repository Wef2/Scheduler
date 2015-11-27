package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import jnu.mcl.scheduler.R;

/**
 * Created by 김 on 2015-11-28.
 */
public class AddCalendarDialog extends Dialog implements View.OnClickListener{

    private Button confirmButton, cancelButton;
    private EditText nameEditText, descriptionEditText;

    private Context context;

    public AddCalendarDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_calendar);

        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == confirmButton){
            dismiss();
        }
        else if(v == cancelButton){
            dismiss();
        }
    }
}
