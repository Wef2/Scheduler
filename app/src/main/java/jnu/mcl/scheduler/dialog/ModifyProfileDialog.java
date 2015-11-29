package jnu.mcl.scheduler.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jnu.mcl.scheduler.R;
import jnu.mcl.scheduler.service.UserService;
import jnu.mcl.scheduler.util.SharedPreferenceUtil;

/**
 * Created by 김 on 2015-11-29.
 */
public class ModifyProfileDialog extends Dialog implements View.OnClickListener {

    private UserService userService = UserService.getInstance();
    private Button confirmButton, cancelButton;
    private EditText nameEditText, descriptionEditText;
    private Context context;

    private String id;

    public ModifyProfileDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_modify_profile);

        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        id = SharedPreferenceUtil.getSharedPreference(context, "id");
    }

    @Override
    public void onClick(View v) {
        Log.w("Test", "Test");
        if (v == confirmButton) {
            if(getDescriptionLength() > 0 && getNicknameLength() > 0) {
                userService.updateUser(id, getNickname(), getDescription());
                Log.w("Test1", "Test1");
                dismiss();
            }
            else{
                Toast.makeText(context, "내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
            }
        } else if (v == cancelButton) {
            Log.w("Test2", "Test2");
            dismiss();
        }
    }

    public String getNickname(){
        return nameEditText.getText().toString();
    }

    public int getNicknameLength(){
        return nameEditText.getText().length();
    }

    public String getDescription(){
        return descriptionEditText.getText().toString();
    }

    public int getDescriptionLength(){
        return descriptionEditText.getText().length();
    }
}
