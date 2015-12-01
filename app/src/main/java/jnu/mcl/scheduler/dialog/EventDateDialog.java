package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Date;

import jnu.mcl.scheduler.R;

/**
 * Created by Kim on 2015-11-30.
 */
public class EventDateDialog extends Dialog {

    private DatePicker datePicker;
    private Button confirmButton, cancelButton;

    public EventDateDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_event_date);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public DatePicker getDatePicker(){
        return datePicker;
    }
}