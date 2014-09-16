package com.bitcasa_fs.client;

import java.io.IOException;

import com.bitcasa_fs.client.api.BitcasaClientApi;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.Exists;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.utility.BitcasaProgressListener;

public class Folder extends Container {
	public Folder() {
	}
	
	/**
	 * This method requires a request to network
	 * @param api
	 * @param pathOfFile
	 * @param exists
	 * @param listener
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws BitcasaException
	 * @return Item meta of newly uploaded file
	 */
	public Item upload(BitcasaClientApi api, String pathOfFile, Exists exists, BitcasaProgressListener listener) throws IOException, InterruptedException, BitcasaException {
		return api.getBitcasaFileSystemApi().uploadFile(this, pathOfFile, exists, listener);
	}
}
