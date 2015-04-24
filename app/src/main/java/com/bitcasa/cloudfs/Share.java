/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 215 Castro Street, 2nd Floor
 * Mountain View, CA 94041
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to support@bitcasa.com.
 */

package com.bitcasa.cloudfs;

import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ApplicationData;
import com.bitcasa.cloudfs.model.ItemMeta;
import com.bitcasa.cloudfs.model.ShareItem;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * The Share class provides accessibility to CloudFS Share.
 */
public class Share {

    /**
     * The Share Key.
     */
    private String shareKey;

    /**
     * The Share name.
     */
    private String name;

    /**
     * The share url.
     */
    private String url;

    /**
     * The share short url.
     */
    private String shortUrl;

    /**
     * The share size.
     */
    private long size;

    /**
     * The share created date.
     */
    private long dateCreated;

    /**
     * The share item meta.
     */
    private ItemMeta meta;

    /**
     * The api service to access REST end point.
     */
    private RESTAdapter restAdapter;

    /**
     * Initializes a new instance of Share.
     *
     * @param restAdapter The REST Adapter instance.
     * @param shareItem   Information of the shared item.
     * @param meta        The share meta data returned from REST Adapter.
     */
    public Share(final RESTAdapter restAdapter, final ShareItem shareItem, final ItemMeta meta) {
        this.restAdapter = restAdapter;
        shareKey = shareItem.getShareKey();
        name = shareItem.getName();
        url = shareItem.getUrl();
        shortUrl = shareItem.getShortUrl();
        size = shareItem.getSize();
        dateCreated = shareItem.getDateCreated();
        this.meta = meta;
    }

    /**
     * Gets the share key.
     *
     * @return The share key.
     */
    public String getShareKey() {
        return shareKey;
    }

    /**
     * Gets the share name.
     *
     * @return The share name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the share name.
     *
     * @param name The share name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the share url.
     *
     * @return The share url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the share size.
     *
     * @return The share size.
     */
    public long getSize() {
        return size;
    }

    /**
     * Gets the share's application data.
     *
     * @return The share's application data.
     */
    public ApplicationData getApplicationData() {
        return meta.getApplicationData();
    }

    /**
     * Gets the share's content last modified date.
     *
     * @return The content last modified date.
     */
    public Date getDateContentLastModified() {
        return new Date(meta.getDateContentLastModified());
    }

    /**
     * Gets the share's meta last modified date.
     *
     * @return The share's meta last modified date.
     */
    public Date getDateMetaLastModified() {
        return new Date(meta.getDateMetaLastModified());
    }

    /**
     * Sets a new name for the current share.
     *
     * @param newName  The new share name.
     * @param password The share password.
     * @return A value indicating whether the operation was successful or not.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean setName(String newName, String password) throws IOException, BitcasaException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(BitcasaRESTConstants.PARAM_NAME, newName);
        return changeAttributes(params, password);
    }

    /**
     * Sets a new password for the given share.
     *
     * @param newPassword The new share password.
     * @param oldPassword The current share password.
     * @return A value indicating whether the operation was successful or not.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean setPassword(String newPassword, String oldPassword)
            throws IOException, BitcasaException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(BitcasaRESTConstants.PARAM_PASSWORD, newPassword);
        return changeAttributes(params, oldPassword);
    }

    /**
     * Creates a string containing a concise, human-readable description of Share object.
     *
     * @return The printable representation of Share object.
     */
    @Override
    public String toString() {

        return "\nshare_key[" + shareKey + "] \nname[" + name + "] \nurl[" + url +
                "] \nshort_url[" + shortUrl + "] \nsize[" + Long.toString(size) +
                "] \ndate_created[" + Long.toString(dateCreated) + "]*****";
    }

    /**
     * Changes the share attributes according to the values provided.
     *
     * @param values        The values to be changed.
     * @param sharePassword The current share password.
     * @return A value indicating whether the operation was successful or not.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean changeAttributes(HashMap<String, String> values, String sharePassword)
            throws IOException, BitcasaException {
        Share share = restAdapter.alterShare(this.getShareKey(), values, sharePassword);
        return share != null;
    }

    /**
     * List the shared items created by the current user.
     *
     * @return The share item array.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] list() throws IOException, BitcasaException {
        return restAdapter.browseShare(this.getShareKey(), null);
    }

    /**
     * Receives the share items to the specified path.
     *
     * @param path   The path where the shares should be received.
     * @param exists The action to take if the files already exists.
     * @return The received item array.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public Item[] receive(String path, BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {
        return restAdapter.receiveShare(this.getShareKey(), path, exists);
    }

    /**
     * Deletes the share.
     *
     * @return The value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean delete() throws BitcasaException {
        return restAdapter.deleteShare(this.getShareKey());
    }

}
