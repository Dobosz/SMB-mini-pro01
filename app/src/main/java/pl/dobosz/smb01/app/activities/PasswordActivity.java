package pl.dobosz.smb01.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import pl.dobosz.smb01.app.R;
import pl.dobosz.smb01.app.helpers.ConfigHelper;

public class PasswordActivity extends Activity {

    @Bind(R.id.password_entry)
    EditText passwordEditText;

    private ConfigHelper configHelper;

    @OnClick(R.id.enter_password_button)
    public void enterPasswordClick() {
        final String password = passwordEditText.getText().toString();
        final String hash = new String(Hex.encodeHex(DigestUtils.sha1(password)));
        final String savedPassword = configHelper.getPassword();
        if(savedPassword.equals(hash))
            startActivity(new Intent(this, MainActivity.class));
        else
            Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasword);
        ButterKnife.bind(this);
        configHelper = ConfigHelper.getInstance(this);
    }

}
