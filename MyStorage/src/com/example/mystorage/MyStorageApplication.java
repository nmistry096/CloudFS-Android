package com.example.mystorage;

import com.bitcasa_fs.client.Session;

import android.app.Application;

public class MyStorageApplication extends Application {

	private final static String CLIENT_ID = "abcdefghijklmnopqrstuvwxyz0123456789-abcdefg";
	private final static String CLIENT_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private final static String CLIENT_ENDPOINT = "xxxxxxxxxx.cloudfs.io";
	
	private Session mBitcasaSession;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mBitcasaSession = new Session(getApplicationContext(), CLIENT_ENDPOINT, CLIENT_ID, CLIENT_SECRET);
	}
	
	public Session getBitcasaSession() {
		return mBitcasaSession;
	}
	
}
