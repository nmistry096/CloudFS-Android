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

import android.os.Parcel;
import android.os.Parcelable;

public class BitcasaError implements Parcelable{

	private int mCode = -1;
	private String mMessage;
	private String mError;
	private String mData;
	
	public BitcasaError() {
		
	}	
	
	public BitcasaError(Parcel in) {
		mCode = in.readInt();
		mMessage = in.readString();
		mError = in.readString();
		mData = in.readString();
	}	
	
	public BitcasaError(int code, String error, String message) {
		this.mCode = code;
		this.mMessage = message;
		this.setError(error);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(mCode);
		out.writeString(mMessage);
		out.writeString(mError);
		out.writeString(mData);
	}
	
	public static final Parcelable.Creator<BitcasaError> CREATOR = new Parcelable.Creator<BitcasaError>() {
		@Override
		public BitcasaError createFromParcel(Parcel source) {
			return new BitcasaError(source);
		}

		@Override
		public BitcasaError[] newArray(int size) {
			return new BitcasaError[size];
		}
	};

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
