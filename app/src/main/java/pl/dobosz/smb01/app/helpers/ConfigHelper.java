package pl.dobosz.smb01.app.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class ConfigHelper {

    private static final String PREFS_NAME = "config";
    private static final String PASSWORD_ENABLE_CONFIG = "password.enable";
    private static final String PASSWORD_CONFIG = "password";
    private SharedPreferences settings;

    private static ConfigHelper instance = null;

    public static ConfigHelper getInstance(Context context) {
        if(instance == null)
            instance = new ConfigHelper(context);
        return instance;
    }

    private ConfigHelper(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isPasswordEnable() {
        return settings.getBoolean(PASSWORD_ENABLE_CONFIG, false);
    }

    public void setPasswordEnable(boolean enable) {
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putBoolean(PASSWORD_ENABLE_CONFIG, enable);
        settingsEditor.apply();
    }

    public String getPassword() {
        return settings.getString(PASSWORD_CONFIG, "def_not_to_be_empty_cuz_its_password");
    }

    public void setPassword(String password) {
        SharedPreferences.Editor settingsEditor = settings.edit();
        final String hash = new String(Hex.encodeHex(DigestUtils.sha1(password)));
        settingsEditor.putString(PASSWORD_CONFIG, hash);
        settingsEditor.apply();
    }

}
