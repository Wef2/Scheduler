package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.service.EventService;

/**
 * Created by Kim on 2015-11-30.
 */
public class AddEventDialog extends Dialog implements View.OnClickListener {
    private EventService eventService = EventService.getInstance();

    private Button confirmButton, cancelButton;
    private EditText eventTitleText;

    private Context context;

    private int calendar_no;
    private String title;

    public AddEventDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_event);

        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        eventTitleText = (EditText) findViewById(R.id.eventTitleText);

        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    public String getTitle() {
        return eventTitleText.getText().toString();
    }

    public int getTitleLength() {
        return eventTitleText.getText().length();
    }

    @Override
    public void onClick(View v) {
        if (v == confirmButton) {
            String toastMessage;
            title = getTitle();
            if (getTitleLength() > 0) {
                eventService.addEvent(1, title, title);
                toastMessage = title + "이벤트가 생성되었습니다.";
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
