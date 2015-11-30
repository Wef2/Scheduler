package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.service.CalendarService;

/**
 * Created by 김 on 2015-11-28.
 */
public class AddCalendarDialog extends Dialog implements View.OnClickListener {

    private CalendarService calendarService = CalendarService.getInstance();

    private Button confirmButton, cancelButton;
    private EditText nameEditText;

    private Context context;

    private String name;

    public AddCalendarDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_calendar);

        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);

        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    public String getName() {
        return nameEditText.getText().toString();
    }

    public int getNameLength() {
        return nameEditText.getText().length();
    }

    @Override
    public void onClick(View v) {
        if (v == confirmButton) {
            String toastMessage;
            name = getName();
            if (getNameLength() > 0) {
                calendarService.addCalendar(name, name, name, name);
                toastMessage = name + " 캘린더가 생성되었습니다.";
            } else {
                toastMessage = "입력해주세요";
            }
            Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
            dismiss();
        } else if (v == cancelButton) {
            dismiss();
        }
    }
}
