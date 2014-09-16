package com.bitcasa_fs.client;

import com.bitcasa_fs.client.api.BitcasaClientApi;

public class User {
	private String username;
	private long created_time;
	private String first_name;
	private String last_name;
	private String email;
	
	public User() {
    }
    
    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();		
		sb.append("\nusername[").append(username)
		.append("] \ncreated_time[").append(Long.toString(created_time))
		.append("] \nfirst_name[").append(first_name)
		.append("] \nlast_name[").append(last_name)
		.append("] \nemail[").append(email);
		sb.append("]*****");
		
		return sb.toString();
	}
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
