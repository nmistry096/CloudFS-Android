package com.bitcasa_fs.client.datamodel;

import com.bitcasa_fs.client.Item;

import android.os.Parcel;
import android.os.Parcelable;

public class ShareItem implements Parcelable{
	private String share_key;
	private String url;
	private String short_url;
	private long size;
	private long date_created;
	
	public ShareItem() {
		
	}
	
	public ShareItem(Parcel in) {
		share_key = in.readString();
		url = in.readString();
		short_url = in.readString();
		size = in.readLong();
		date_created = in.readLong();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(share_key);
		out.writeString(url);
		out.writeString(short_url);
		out.writeLong(size);
		out.writeLong(date_created);
	}
	
	public static final Parcelable.Creator<ShareItem> CREATOR = new Parcelable.Creator<ShareItem>() {
		@Override
		public ShareItem createFromParcel(Parcel source) {
			return new ShareItem(source);
		}

		@Override
		public ShareItem[] newArray(int size) {
			return new ShareItem[size];
		}
	};
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();		
		sb.append("\nshare_key[").append(share_key)
		.append("] \nurl[").append(url)
		.append("] \nshort_url[").append(short_url)
		.append("] \nsize[").append(Long.toString(size))
		.append("] \ndate_created[").append(Long.toString(date_created));
		sb.append("]*****");
		
		return sb.toString();
	}
	
	public String getShare_key() {
		return share_key;
	}
	public void setShare_key(String share_key) {
		this.share_key = share_key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShort_url() {
		return short_url;
	}
	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getDate_created() {
		return date_created;
	}
	public void setDate_created(long date_created) {
		this.date_created = date_created;
	}
}
