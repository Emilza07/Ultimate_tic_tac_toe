package com.emil_z.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

public class LoginPreference {
	private static final String PREF_NAME = "LoginPrefs";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASSWORD = "password";

	private SharedPreferences sharedPreferences;
	private final Context context;

	public LoginPreference(Context context) {
		this.context = context;
		try {

			String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
			sharedPreferences = EncryptedSharedPreferences.create(
					PREF_NAME,
					masterKeyAlias,
					context,
					EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
					EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
			);
		} catch (Exception e) {
			Toast.makeText(context, "Please clear app data and try again", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	public void saveLoginCredentials(String email, String password) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(KEY_EMAIL, email);
		editor.putString(KEY_PASSWORD, password);
		editor.apply();
	}

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public String getEmail() {
		return sharedPreferences.getString(KEY_EMAIL, null);
	}

	public String getPassword() {
		return sharedPreferences.getString(KEY_PASSWORD, null);
	}

	public void clearLoginCredentials() {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(KEY_EMAIL);
		editor.remove(KEY_PASSWORD);
		editor.apply();
	}
}