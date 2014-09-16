package com.bitcasa_fs.client.datamodel;

import java.util.ArrayList;

import com.bitcasa_fs.client.Item;

public class BrowseShare {
	private ShareItem share;
	private Item meta;
	private Item[] sharedItems;
	
	public ShareItem getShare() {
		return share;
	}
	public void setShare(ShareItem share) {
		this.share = share;
	}
	public Item getMeta() {
		return meta;
	}
	public void setMeta(Item meta) {
		this.meta = meta;
	}
	public Item[] getSharedItems() {
		return sharedItems;
	}
	public void setSharedItems(Item[] sharedItems) {
		this.sharedItems = sharedItems;
	}
}
