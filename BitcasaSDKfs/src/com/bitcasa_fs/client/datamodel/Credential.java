package com.bitcasa_fs.client.datamodel;

import android.content.Context;
import android.content.SharedPreferences;

public class Credential {

	private static SharedPreferences bitcasaPreference;
	private static final String PREFERENCES_NAME = "com.bitcasa_fs.client";
	private static final String PREFS_ACCESS_TOKEN = "access_token";
	private static final String PREFS_TOKEN_TYPE = "token_type";
	private static final String PREFS_ENDPOINT = "endpoint";
	public static Credential sInstance;
	
	public Credential(Context applicationContext) {
		this.bitcasaPreference = applicationContext.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.BIND_AUTO_CREATE|Context.MODE_PRIVATE);
	}

	public String getAccessToken() {
		return bitcasaPreference.getString(PREFS_ACCESS_TOKEN, null);
	}

	public void setAccessToken(String accessToken) {
		SharedPreferences.Editor editContent = bitcasaPreference.edit();
		editContent.putString(PREFS_ACCESS_TOKEN, accessToken);
		editContent.commit();
	}

	public String getTokenType() {
		return bitcasaPreference.getString(PREFS_TOKEN_TYPE, null);
	}

	public void setTokenType(String tokenType) {
		SharedPreferences.Editor editContent = bitcasaPreference.edit();
		editContent.putString(PREFS_TOKEN_TYPE, tokenType);
		editContent.commit();
	}	
	
	public void setEndPoint(String endpoint) {
		SharedPreferences.Editor editContent = bitcasaPreference.edit();
		editContent.putString(PREFS_ENDPOINT, endpoint);
		editContent.commit();
	}
	
	public String getEndPoint() {
		return bitcasaPreference.getString(PREFS_ENDPOINT, null);
	}
}
