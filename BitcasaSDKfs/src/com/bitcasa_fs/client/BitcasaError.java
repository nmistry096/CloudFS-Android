/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2013 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
 */

package com.bitcasa_fs.client;

public class BitcasaError {

	private int mCode = -1;
	private String mMessage;
	private String mError;
	private String mData;
	
	public BitcasaError() {
		
	}
	
	public BitcasaError(int code, String error, String message) {
		this.mCode = code;
		this.mMessage = message;
		this.setError(error);
	}

	public int getCode() {
		return mCode;
	}

	public void setCode(int Code) {
		this.mCode = Code;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String Message) {
		this.mMessage = Message;
	}

	public String getError() {
		return mError;
	}

	public void setError(String mError) {
		this.mError = mError;
	}

	public String getData() {
		return mData;
	}

	public void setData(String mData) {
		this.mData = mData;
	}
	
}
