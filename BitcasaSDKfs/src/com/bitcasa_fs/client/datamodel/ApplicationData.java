package com.bitcasa_fs.client.datamodel;

import com.bitcasa_fs.client.BitcasaError;

import android.os.Parcel;
import android.os.Parcelable;

public class ApplicationData implements Parcelable{
	private String original_path;
	private String relative_id_path;
	private String nonce;
	private String payload;
	private String digest;
	private String album_art;
	
	public ApplicationData() {
		
	}
	
	public ApplicationData(Parcel in) {
		original_path = in.readString();
		relative_id_path = in.readString();
		nonce = in.readString();
		payload = in.readString();
		digest = in.readString();
		album_art = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(original_path);
		out.writeString(relative_id_path);
		out.writeString(nonce);
		out.writeString(payload);
		out.writeString(digest);
		out.writeString(album_art);
	}
	
	public static final Parcelable.Creator<ApplicationData> CREATOR = new Parcelable.Creator<ApplicationData>() {
		@Override
		public ApplicationData createFromParcel(Parcel source) {
			return new ApplicationData(source);
		}

		@Override
		public ApplicationData[] newArray(int size) {
			return new ApplicationData[size];
		}
	};
	
	public String getOriginal_path() {
		return original_path;
	}

	public void setOriginal_path(String original_path) {
		this.original_path = original_path;
	}

	public String getRelative_id_path() {
		return relative_id_path;
	}

	public void setRelative_id_path(String relative_id_path) {
		this.relative_id_path = relative_id_path;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getAlbum_art() {
		return album_art;
	}

	public void setAlbum_art(String album_art) {
		this.album_art = album_art;
	}	
}
