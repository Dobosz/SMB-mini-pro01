package pl.dobosz.smb01.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import butterknife.*;
import org.apache.commons.codec.digest.DigestUtils;
import pl.dobosz.smb01.app.R;
import pl.dobosz.smb01.app.helpers.ConfigHelper;

public class SettingsActivity extends Activity {

    @Bind(R.id.password)
    EditText passwordEditText;
    @Bind(R.id.password_enable)
    CheckBox passwordCheckBox;

    private ConfigHelper configHelper;

    @OnCheckedChanged(R.id.password_enable)
    public void passwordChackBoxClick(boolean checked) {
        passwordEditText.setEnabled(checked);
        ConfigHelper.getInstance(this).setPasswordEnable(checked);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        configHelper = ConfigHelper.getInstance(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        passwordCheckBox.setChecked(configHelper.isPasswordEnable());
    }

    @Override
    protected void onPause() {
        super.onPause();
        final String password = passwordEditText.getText().toString();
        if (!password.isEmpty())
            configHelper.setPassword(password);
    }
}
