package com.bitcasa_fs.client.datamodel;

public class ShareItem {
	private String share_key;
	private String url;
	private String short_url;
	private long size;
	private long date_created;
	
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
