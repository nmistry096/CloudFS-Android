package com.bitcasa_fs.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import com.bitcasa_fs.client.api.BitcasaClientApi;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.RestoreOptions;
import com.bitcasa_fs.client.datamodel.ApplicationData;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.exception.BitcasaRequestErrorException;

public class Item implements Parcelable {
	private String id;
	private String parent_id;
	private String file_type;
	private String name;
	private long date_created;
	private long date_meta_last_modified;
	private long date_content_last_modified;
	private int version;
	private boolean is_mirrored;
	private String absoluteParentPathId;
	private String mime;
	
	private String blocklist_key;
	private String extension;
	private String blocklist_id;
	private long size;
	
	//application data
	ApplicationData applicationData;
	
	public interface FileType {
		String FILE = "file";
		String FOLDER = "folder";
		String MIRROR_FOLDER = "mirror_folder";
		String ROOT = "root";
		String METAFOLDER = "metafolder";
	}
	
    public Item() {
    	applicationData = new ApplicationData();
	}
    
    public Item(Parcel in) {
    	id = in.readString();
    	parent_id = in.readString();
    	file_type = in.readString();
    	name = in.readString();
    	date_created = in.readLong();
    	date_meta_last_modified = in.readLong();
    	date_content_last_modified = in.readLong();
    	version = in.readInt();
    	is_mirrored = in.readInt()==0?false:true;
    	absoluteParentPathId = in.readString();
    	mime = in.readString();
    	blocklist_key = in.readString();
    	extension = in.readString();
    	blocklist_id = in.readString();
    	size = in.readLong();
    }
    
    @Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(parent_id);
		out.writeString(file_type);
		out.writeString(name);
		out.writeLong(date_created);
		out.writeLong(date_meta_last_modified);
		out.writeLong(date_content_last_modified);
		out.writeInt(version);
		out.writeInt(is_mirrored?1:0);
		out.writeString(absoluteParentPathId);
		out.writeString(mime);
		out.writeString(blocklist_key);
	}
	
	public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
		@Override
		public Item createFromParcel(Parcel source) {
			return new Item(source);
		}

		@Override
		public Item[] newArray(int size) {
			return new Item[size];
		}
	};
 
    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nid[").append(id)
			.append("] parent_id[").append(parent_id).append("] file_type[").append(file_type).append("] name[")
			.append(name).append("] date_created[").append(date_created).append("] date_meta_last_modified[").append(date_meta_last_modified).append("] date_content_last_modified[")
			.append(date_content_last_modified).append("] version[").append(version).append("] is_mirrored[").append(is_mirrored).append("] mime[").append(mime).append("]")
			.append("absoluteParentPathId[").append(absoluteParentPathId).append("]");
		
		if (extension != null)
			sb.append("\nextension[").append(extension).append("]");
		
		if (size >= 0)
			sb.append("\nsize[").append(Long.toString(size)).append("]");
		return sb.toString();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDate_created() {
		return date_created;
	}

	public void setDate_created(long date_created) {
		this.date_created = date_created;
	}

	public long getDate_meta_last_modified() {
		return date_meta_last_modified;
	}

	public void setDate_meta_last_modified(long date_meta_last_modified) {
		this.date_meta_last_modified = date_meta_last_modified;
	}

	public long getDate_content_last_modified() {
		return date_content_last_modified;
	}

	public void setDate_content_last_modified(long date_content_last_modified) {
		this.date_content_last_modified = date_content_last_modified;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isIs_mirrored() {
		return is_mirrored;
	}

	public void setIs_mirrored(boolean is_mirrored) {
		this.is_mirrored = is_mirrored;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}
	
	public String getAbsoluteParentPathId() {
		return absoluteParentPathId;
	}

	public void setAbsoluteParentPathId(String absoluteParentPathId) {
		this.absoluteParentPathId = absoluteParentPathId;
	}

	public String getBlocklist_key() {
		return blocklist_key;
	}

	public void setBlocklist_key(String blocklist_key) {
		this.blocklist_key = blocklist_key;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getBlocklist_id() {
		return blocklist_id;
	}

	public void setBlocklist_id(String blocklist_id) {
		this.blocklist_id = blocklist_id;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public ApplicationData getApplicationData() {
		return applicationData;
	}

	public void setApplicationData(ApplicationData applicationData) {
		this.applicationData = applicationData;
	}

	/**
	 * This method requires a request to network
	 * @param api
	 * @param destination
	 * @return
	 * @throws IOException
	 * @throws BitcasaException
	 * @return meta of moved item
	 */
	public Item moveTo(BitcasaClientApi api, Container destination) throws IOException, BitcasaException {
    	return api.getBitcasaFileSystemApi().move(this, destination.getAbsoluteParentPathId(), null, null);
    }
    
	/**
	 * This method requires a request to network
	 * @param api
	 * @param destination
	 * @return
	 * @throws IOException
	 * @throws BitcasaException
	 * @return meta of moved item
	 */
    public Item moveTo(BitcasaClientApi api, String destination) throws IOException, BitcasaException {
    	return api.getBitcasaFileSystemApi().move(this, destination, null, null);
    }
 
    /**
     * This method requires a request to network
     * @param api
     * @param destination
     * @return
     * @throws IOException
     * @throws BitcasaException
     * @return meta of copied item
     */
    public Item copyTo(BitcasaClientApi api, Container destination) throws IOException, BitcasaException {
    	return api.getBitcasaFileSystemApi().copy(this, destination.getAbsoluteParentPathId(), null, null);
    }
    
    /**
     * This method requires a request to network
     * @param api
     * @param destination
     * @return
     * @throws IOException
     * @throws BitcasaException
     * @return meta of copied item
     */
    public Item copyTo(BitcasaClientApi api, String destination) throws IOException, BitcasaException {
    	return api.getBitcasaFileSystemApi().copy(this, destination, null, null);
    }
 
    /**
     * This method requires a request to network
     * @param api
     * @return
     * @throws BitcasaRequestErrorException
     * @throws IOException
     * @throws BitcasaException
     * @return result(true or false) of deleting this item
     */
    public boolean delete(BitcasaClientApi api) throws BitcasaRequestErrorException, IOException, BitcasaException {
    	if (getFile_type().equals(FileType.FOLDER))
    		return api.getBitcasaFileSystemApi().deleteFolder(getAbsoluteParentPathId(), false);
    	else
    		return api.getBitcasaFileSystemApi().deleteFile(getAbsoluteParentPathId(), false);
    }
 
    /**
     * This method requires a request to network
     * @param api
     * @param destination
     * @return
     * @throws UnsupportedEncodingException
     * @return result(true or false) of restoring this trash item
     */
    public boolean restore(BitcasaClientApi api, Container destination) throws UnsupportedEncodingException {
    	return api.getBitcasaTrashApi().recoverTrashItem(getPathFromTrash(), RestoreOptions.RESCUE, destination.getAbsolutePath());
    }
    
    /**
     * This method restores the item from trash, requires a request to network
     * @param api
     * @param path
     * @return
     * @throws UnsupportedEncodingException
     * @return result(true or false) of restoring this trash item
     */
    public boolean restore(BitcasaClientApi api, String path) throws UnsupportedEncodingException {
    	return api.getBitcasaTrashApi().recoverTrashItem(getPathFromTrash(), RestoreOptions.RESCUE, path);
    }
 
    /**
     * This method lists versions of this file in history from 0 to current version, requires a request to network
     * 
     * @param api
     * @return Returns the metadata for selected versions of a file as recorded in the History.
     * @throws IOException
     */
    public Item[] listHistory(BitcasaClientApi api) throws IOException {
    	return api.getBitcasaFileSystemApi().listFileVersions(getAbsolutePath(), 0, getVersion(), getVersion());
    }
    
    public String getAbsolutePath() {
		StringBuilder pathBuild = new StringBuilder();		
		if (absoluteParentPathId != null && !absoluteParentPathId.equals("")) {
			if (!absoluteParentPathId.startsWith("/"))
				pathBuild.append("/");
			pathBuild.append(absoluteParentPathId);
		}
		
		pathBuild.append("/");
		return pathBuild.toString();
	}
    
    public String getPathFromTrash() {
    	if (applicationData.getOriginal_path() != null && getId() != null) {
    		return applicationData.getOriginal_path() + getId();
    	}
    	return null;
    }
}
