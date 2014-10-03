package com.bitcasa_fs.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import com.bitcasa_fs.client.Item.FileType;
import com.bitcasa_fs.client.api.BitcasaClientApi;
import com.bitcasa_fs.client.api.BitcasaFileSystemApi;
import com.bitcasa_fs.client.api.BitcasaHistoryApi;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.Exists;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.RestoreOptions;
import com.bitcasa_fs.client.api.BitcasaRESTConstants.VersionExists;
import com.bitcasa_fs.client.api.BitcasaTrashApi;
import com.bitcasa_fs.client.exception.BitcasaAuthenticationException;
import com.bitcasa_fs.client.exception.BitcasaException;
import com.bitcasa_fs.client.exception.BitcasaRequestErrorException;



public class FileSystem {
	private BitcasaClientApi api;
	
	public FileSystem(BitcasaClientApi api) {
		this.api = api;
	}
    
	/**
	 * This method requires a request to network
	 * @param item
	 * @return
	 * @throws IOException
	 * @throws BitcasaException
	 * @return all items(files and folders) under input item
	 */
    public Item[] list(Item item) throws IOException, BitcasaException {
    	if (item == null)
    		return api.getBitcasaFileSystemApi().getList(null, -1, 1, null);
    	else
    		return api.getBitcasaFileSystemApi().getList(item.getAbsolutePath(), -1, 1, null);
    }
    
    /**
     * This method requires a request to network
     * @param items
     * @return
     * @throws BitcasaRequestErrorException
     * @throws IOException
     * @throws BitcasaException
     * @return list of result(true or false) for each item
     */
    public boolean[] delete(Item[] items) throws BitcasaRequestErrorException, IOException, BitcasaException {
    	boolean[] results = new boolean[items.length];
    	for (int i=0; i<items.length; i++) {
    		if (items[i].getFile_type().equals(FileType.FILE))
    			results[i] = api.getBitcasaFileSystemApi().deleteFile(items[i].getAbsolutePath(), false);
    		else
    			results[i] = api.getBitcasaFileSystemApi().deleteFolder(items[i].getAbsolutePath(), false);
    	}
    	return results;
    }
 
    /**
     * This method requires a request to network
     * @param items
     * @param destination
     * @param exists
     * @return
     * @throws IOException
     * @throws BitcasaException
     * @return list of items been moved
     */
    public Item[] move(Item[] items, String destination, Exists exists) throws IOException, BitcasaException {
    	BitcasaFileSystemApi filesystemApi = api.getBitcasaFileSystemApi();
    	Item[] Results = new Item[items.length];
    	for (int i=0; i<items.length; i++) {
    		Results[i] = filesystemApi.move(items[i], destination, null, null);
    	}
    	return Results;
    }
    
    /**
     * This method requires a request to network
     * @param items
     * @param destination
     * @param exists
     * @return
     * @throws IOException
     * @throws BitcasaException
     * @return list of items been moved
     */
    public Item[] move(Item[] items, Container destination, Exists exists) throws IOException, BitcasaException {
    	BitcasaFileSystemApi filesystemApi = api.getBitcasaFileSystemApi();
    	Item[] Results = new Item[items.length];
    	for (int i=0; i<items.length; i++) {
    		Results[i] = filesystemApi.move(items[i], destination.getAbsolutePath(), null, null);
    	}
    	return Results;
    }
 
    /**
     * This method requires a request to network
     * @param items
     * @param destination
     * @param exists
     * @return
     * @throws IOException
     * @throws BitcasaException
     * @return list of items been copied
     */
    public Item[] copy(Item[] items, String destination, Exists exists) throws IOException, BitcasaException {
    	BitcasaFileSystemApi filesystemApi = api.getBitcasaFileSystemApi();
    	Item[] Results = new Item[items.length];
    	for (int i=0; i<items.length; i++) {
    		Results[i] = filesystemApi.copy(items[i], destination, null, null);
    	}
    	return Results;
    }
    
    /**
     * This method requires a request to network
     * @param items
     * @param destination
     * @param exists
     * @return
     * @throws IOException
     * @throws BitcasaException
     * @return list of items been copied
     */
    public Item[] copy(Item[] items, Container destination, Exists exists) throws IOException, BitcasaException {
    	BitcasaFileSystemApi filesystemApi = api.getBitcasaFileSystemApi();
    	Item[] Results = new Item[items.length];
    	for (int i=0; i<items.length; i++) {
    		Results[i] = filesystemApi.copy(items[i], destination.getAbsolutePath(), items[i].getName(), exists);
    	}
    	return Results;
    }
 
    /**
     * This method requires a request to network
     * @param items
     * @param exists
     * @return
     * @throws BitcasaAuthenticationException
     * @throws IOException
     * @return list of items been updated
     */
    public Item[] saveItems(Item[] items, VersionExists exists) throws BitcasaAuthenticationException, IOException {
    	BitcasaFileSystemApi filesystemApi = api.getBitcasaFileSystemApi();
    	Item[] Results = new Item[items.length];
    	for (int i=0; i<items.length; i++) {
    		HashMap<String, String> changes = new HashMap<String, String>();
    		changes.put("name", items[i].getName());
    		changes.put("date_created", Long.toString(items[i].getDate_created()));
    		changes.put("date_meta_last_modified", Long.toString(items[i].getDate_meta_last_modified()));
    		changes.put("date_content_last_modified", Long.toString(items[i].getDate_content_last_modified()));
    		Results[i] = filesystemApi.alterMeta(items[i], changes, items[i].getVersion(), exists);
    	}
    	return Results;
    }
 
    /**
     * This method requires a request to network
     * @param items
     * @param destination
     * @param restoreOption
     * @return
     * @throws UnsupportedEncodingException
     * @return result(true or false) of items restored
     */
    public boolean[] restore(Item[] items, String destination, RestoreOptions restoreOption) throws UnsupportedEncodingException {
    	BitcasaTrashApi trashApi = api.getBitcasaTrashApi();
    	boolean[] Results = new boolean[items.length];
    	for (int i=0; i<items.length; i++) {
    		Results[i] = trashApi.recoverTrashItem(items[i].getAbsolutePath(), restoreOption, destination);
    	}
    	return Results;
    }
    
    /**
     * This method requires a request to network
     * @param items
     * @param destination
     * @param restoreOption
     * @return
     * @throws UnsupportedEncodingException
     * @return result(true or false) of items restored
     */
    public boolean[] restore(Item[] items, Container destination, RestoreOptions restoreOption) throws UnsupportedEncodingException {
    	BitcasaTrashApi trashApi = api.getBitcasaTrashApi();
    	boolean[] Results = new boolean[items.length];
    	for (int i=0; i<items.length; i++) {
    		Results[i] = trashApi.recoverTrashItem(items[i].getAbsolutePath(), restoreOption, destination.getAbsolutePath());
    	}
    	return Results;
    }
    
    /**
     * This method requires a request to network
     * @param paths
     * @param destination
     * @param restoreOption
     * @return
     * @throws UnsupportedEncodingException
     * @return result(true or false) of items restored
     */
    public boolean[] restore(String[] paths, String destination, RestoreOptions restoreOption) throws UnsupportedEncodingException {
    	BitcasaTrashApi trashApi = api.getBitcasaTrashApi();
    	boolean[] Results = new boolean[paths.length];
    	for (int i=0; i<paths.length; i++) {
    		Results[i] = trashApi.recoverTrashItem(paths[i], restoreOption, destination);
    	}
    	return Results;
    }
    
    /**
     * This method requires a request to network
     * @param paths
     * @param destination
     * @param restoreOption
     * @return
     * @throws UnsupportedEncodingException
     * @return result(true or false) of items restored
     */
    public boolean[] restore(String[] paths, Container destination, RestoreOptions restoreOption) throws UnsupportedEncodingException {
    	BitcasaTrashApi trashApi = api.getBitcasaTrashApi();
    	boolean[] Results = new boolean[paths.length];
    	for (int i=0; i<paths.length; i++) {
    		Results[i] = trashApi.recoverTrashItem(paths[i], restoreOption, destination.getAbsolutePath());
    	}
    	return Results;
    }
 
    /**
     * File history up to 10 versions(default), this method requires a request to network
     * @param path
     * @return
     * @throws IOException
     */
    public Item[] fileHistory(String path) throws IOException {
    	BitcasaFileSystemApi filesystemApi = api.getBitcasaFileSystemApi();
    	Item[] results = null;
    	results = filesystemApi.listFileVersions(path, 0, -1, -1);
    	return results;
    }
    
    /**
     * File history up to current version, this method requires a request to network
     * @param item
     * @return
     * @throws IOException
     */
    public Item[] fileHistory(Item item) throws IOException {
    	BitcasaFileSystemApi filesystemApi = api.getBitcasaFileSystemApi();
    	Item[] results = null;
    	results = filesystemApi.listFileVersions(item.getAbsolutePath(), 0, item.getVersion(), -1);
    	return results;
    }
 
    /**
     * list action history, this method requires a request to network
     * @return
     * @throws IOException
     */
    public ActionHistory actionHistory() throws IOException {
    	BitcasaHistoryApi historyApi = api.getBitcasaHistoryApi();
    	ActionHistory history = null;
    	history = historyApi.listHistory(0, 10);
    	return history;
    }
}
