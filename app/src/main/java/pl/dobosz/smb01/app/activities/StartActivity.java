package pl.dobosz.smb01.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import pl.dobosz.smb01.app.helpers.ConfigHelper;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        if(ConfigHelper.getInstance(this).isPasswordEnable())
            startActivity(new Intent(this, PasswordActivity.class));
        else
            startActivity(new Intent(this, MainActivity.class));
    }
}
