package com.bitcasa_fs.client;

import java.io.IOException;

import com.bitcasa_fs.client.api.BitcasaClientApi;
import com.bitcasa_fs.client.exception.BitcasaAuthenticationException;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.exception.BitcasaRequestErrorException;


public class Container extends Item {
	public Container() {
	}
	 
	/**
	 * This method requires a request to network
	 * @param api
	 * @return
	 * @throws IOException
	 * @throws BitcasaException
	 * @return all items(files or folder) under current item, one level down
	 */
    public Item[] list(BitcasaClientApi api) throws IOException, BitcasaException {
    	return api.getBitcasaFileSystemApi().getList(getAbsoluteParentPathId(), -1, 1, null);
    }
     
    /**
     * This method requires a request to network
     * @param api
     * @param item
     * @return
     * @throws BitcasaRequestErrorException
     * @throws BitcasaAuthenticationException
     * @throws IOException
     * @return Bitcasa Item meta representing newly created folder
     */
    public Item createFolder(BitcasaClientApi api, Container item) throws BitcasaRequestErrorException, BitcasaAuthenticationException, IOException {
    	return api.getBitcasaFileSystemApi().createFolder(item.getName(), this);
    }
}
