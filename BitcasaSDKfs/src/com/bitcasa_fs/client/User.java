package com.bitcasa_fs.client;

import android.os.Parcel;
import android.os.Parcelable;

import com.bitcasa_fs.client.api.BitcasaClientApi;

public class User implements Parcelable {
	private String username;
	private long created_time;
	private String first_name;
	private String last_name;
	private String email;
	
	public User() {
    }
	
	public User(Parcel in) {
		username = in.readString();
		created_time = in.readLong();
		first_name = in.readString();
		last_name = in.readString();
		email = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(username);
		out.writeLong(created_time);
		out.writeString(first_name);
		out.writeString(last_name);
		out.writeString(email);
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
    
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
