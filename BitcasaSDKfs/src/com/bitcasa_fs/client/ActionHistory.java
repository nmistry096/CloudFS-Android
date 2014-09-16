package com.bitcasa_fs.client;

public class ActionHistory {
	private int version;
	private String action;
	private String path;
	private String type;
	
	private String data_share_key;
	private String data_exists;
	private String data_path;
	
	private String[] data_paths;
	private String data_url;
	
	private String data_id_from;
	private String data_id_to;
	
	private String data_name;
	private String data_application_data;	
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
