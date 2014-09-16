package com.bitcasa_fs.client;

import java.io.IOException;
import java.io.InputStream;

import com.bitcasa_fs.client.api.BitcasaClientApi;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.utility.BitcasaProgressListener;


public class File extends Item {
	public File() {
	}
	
	public File(Item item) {
		setId(item.getId());
		setAbsoluteParentPathId(item.getAbsoluteParentPathId());
		setBlocklist_id(item.getBlocklist_id());
		setBlocklist_key(item.getBlocklist_key());
		setDate_content_last_modified(item.getDate_content_last_modified());
		setDate_created(item.getDate_created());
		setDate_meta_last_modified(item.getDate_meta_last_modified());
		setExtension(item.getExtension());
		setFile_type(item.getFile_type());
		setIs_mirrored(item.isIs_mirrored());
		setMime(item.getMime());
		setName(item.getName());
		setParent_id(item.getParent_id());
		setSize(item.getSize());
		setVersion(item.getVersion());
	}
	 
	/**
	 * This method requires a request to network
	 * @param api
	 * @return
	 * @throws BitcasaException
	 * @throws InterruptedException
	 * @throws IOException
	 * @return InputStream
	 */
    public InputStream read(BitcasaClientApi api) throws BitcasaException, InterruptedException, IOException {
    	return api.getBitcasaFileSystemApi().download(this, 0);
    }
    
    /**
     * This method requires a request to network
     * @param api
     * @param localDestinationPath
     * @param listener
     * @throws BitcasaException
     * @throws InterruptedException
     * @throws IOException
     */
    public void download(BitcasaClientApi api, String localDestinationPath, BitcasaProgressListener listener) throws BitcasaException, InterruptedException, IOException {
    	api.getBitcasaFileSystemApi().downloadFile(this, 0, localDestinationPath, listener);
    }
}
