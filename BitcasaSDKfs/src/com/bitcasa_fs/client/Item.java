package com.bitcasa_fs.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.bitcasa_fs.client.api.BitcasaClientApi;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.RestoreOptions;
import com.bitcasa_fs.client.datamodel.ApplicationData;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.exception.BitcasaRequestErrorException;

public class Item {
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
    	return api.getBitcasaFileSystemApi().move(this, destination.getAbsolutePath(), null, null);
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
    	return api.getBitcasaFileSystemApi().copy(this, destination.getAbsolutePath(), null, null);
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
    		return api.getBitcasaFileSystemApi().deleteFolder(getAbsolutePath());
    	else
    		return api.getBitcasaFileSystemApi().deleteFile(getAbsolutePath());
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
     * This method requires a request to network
     * @param api
     * @param path
     * @return
     * @throws UnsupportedEncodingException
     * @return result(true or false) of restoring this trash item
     */
    public boolean restore(BitcasaClientApi api, String path) throws UnsupportedEncodingException {
    	return api.getBitcasaTrashApi().recoverTrashItem(getPathFromTrash(), RestoreOptions.RESCUE, path);
    }
 
    public Item[] history() {
    	return null;
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
