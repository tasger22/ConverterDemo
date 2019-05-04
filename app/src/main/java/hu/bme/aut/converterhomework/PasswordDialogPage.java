package hu.bme.aut.converterhomework;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import hu.bme.aut.converterhomework.fragments.ChangePasswordDialogFragment;
import hu.bme.iit.dynamiccodedialog.CodeDialogBase;
import hu.bme.iit.dynamiccodedialog.CryptographyImplementation;

public class PasswordDialogPage extends CodeDialogBase {

    private CryptographyImplementation crypter;
    private Activity ownerActivity;

    protected PasswordDialogPage(@NonNull Context context, int themeId, CryptographyImplementation cryptographyImplementation, Activity ownerActivity) {
        super(context, themeId, cryptographyImplementation);
        crypter = cryptographyImplementation;
        this.ownerActivity = ownerActivity;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String storedPassHexa = sp.getString(ChangePasswordDialogFragment.KEY_PREF_PASSWORD,"");
        setCode(storedPassHexa);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_dialog_page_layout);

        GridLayout inputLayout = findViewById(R.id.codeInputLayout);
        for (int i = 0; i < inputLayout.getChildCount(); i++) {
            TextView child = (TextView) inputLayout.getChildAt(i);
            child.setText(String.valueOf((i+1)%10));
        }
        setCodeButtonList(inputLayout);

        EditText passwordLine = findViewById(R.id.passwordLine);
        TextView enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener(view -> {
            String codeInput = passwordLine.getText().toString();
            if (!"".equals(codeInput) && compareCodeToInput(codeInput)) {
                Toast.makeText(getContext(), R.string.code_accepted,Toast.LENGTH_SHORT).show();
                dismiss();
            }
            else {
                Toast.makeText(getContext(), R.string.code_rejected,Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(view -> {
            String codeInput = passwordLine.getText().toString();
            if(!"".equals(codeInput)){
                int passLength = codeInput.length();
                String newPassLineText = codeInput.substring(0,passLength-1);
                passwordLine.setText(newPassLineText);
            }
        });
        randomizeButtons();
    }

    @Override
    protected void authenticationFailed() {
        ownerActivity.finishAffinity();
    }

    @Override
    protected boolean compareCodeToInput(String input) {
        byte[] inputEncryptedBytes = crypter.encrypt(input);
        String hexaInputString = crypter.byteArrayToHexString(inputEncryptedBytes);
        return super.compareCodeToInput(hexaInputString);
    }

    @Override
    protected void processCodeButtonPress(View view) {
        TextView numberButton = (TextView) view;
        EditText passwordLine = findViewById(R.id.passwordLine);

        passwordLine.append(numberButton.getText().toString());

        randomizeButtons();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            dismiss();
            ownerActivity.finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }
}
