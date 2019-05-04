package hu.bme.aut.converterhomework.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import hu.bme.aut.converterhomework.R;
import hu.bme.iit.dynamiccodedialog.CryptographyImplementation;

public class ChangePasswordDialogFragment extends AppCompatDialogFragment {

        public static final String TAG = "ChangePasswordDialogFragment";
        public static final String KEY_PREF_PASSWORD = "pref_password";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.password))
                    .setView(getChangePasswordView())
                    .create();
        }

        private View getChangePasswordView() {

            final View changePassView = LayoutInflater.from(getContext()).inflate(
                    R.layout.change_password_layout, null);

            TextView okButton = changePassView.findViewById(R.id.okButton);
            okButton.setOnClickListener(view -> {
                EditText passField = changePassView.findViewById(R.id.passField);
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
                CryptographyImplementation encrypter = new CryptographyImplementation();
                String newPassword = passField.getText().toString();

                SharedPreferences.Editor sharedPreferencesEditor = settings.edit();
                if(!"".equals(newPassword) && newPassword.length() >= 4 && newPassword.length() <= 8) {
                    byte[] stringByteArray = encrypter.encrypt(newPassword);
                    String encryptedStr = encrypter.byteArrayToHexString(stringByteArray);
                    sharedPreferencesEditor.putString(KEY_PREF_PASSWORD,encryptedStr);
                    sharedPreferencesEditor.apply();
                    dismiss();
                }
            });

            TextView cancelButton = changePassView.findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(view -> dismiss());

            return changePassView;
        }
}
