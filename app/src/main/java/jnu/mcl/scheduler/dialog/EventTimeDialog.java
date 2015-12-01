package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;

import jnu.mcl.scheduler.R;

/**
 * Created by Kim on 2015-12-01.
 */
public class EventTimeDialog  extends Dialog {

    private TimePicker timePicker;
    private Button confirmButton, cancelButton;

    public EventTimeDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_event_time);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
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

    public TimePicker getTimePicker(){
        return timePicker;
    }
}
