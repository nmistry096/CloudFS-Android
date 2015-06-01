/**
 * Bitcasa Client Android SDK
 * Copyright (C) 2015 Bitcasa, Inc.
 * 1200 Park Place,
 * Suite 350 San Mateo, CA 94403.
 *
 * This file contains an SDK in Java for accessing the Bitcasa infinite drive in Android platform.
 *
 * For support, please send email to sdks@bitcasa.com.
 */

package com.bitcasa.cloudfs;

import android.os.Parcel;
import android.os.Parcelable;

import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ItemMeta;
import com.bitcasa.cloudfs.model.ShareItem;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The Share class provides accessibility to CloudFS Share.
 */
public class Share implements Parcelable {

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
        this.shareKey = shareItem.getShareKey();
        this.name = shareItem.getName();
        this.url = shareItem.getUrl();
        this.shortUrl = shareItem.getShortUrl();
        this.size = shareItem.getSize();
        this.dateCreated = shareItem.getDateCreated();
        this.meta = meta;
    }

    public Share() {

    }

    public static final Parcelable.Creator<Share> CREATOR = new Parcelable.Creator<Share>() {

        /**
         * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Share createFromParcel(Parcel source) {
            return new Share(source);
        }

        /**
         * Create a new array of the Parcelable class
         * @param size Size of the array
         * @return Returns an array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public Share[] newArray(int size) {
            return new Share[size];
        }
    };

    public Share(Parcel source) {
        restAdapter = (RESTAdapter) source.readValue(RESTAdapter.class.getClassLoader());
        shareKey = source.readString();
        name = source.readString();
        url = source.readString();
        shortUrl = source.readString();
        size = source.readLong();
        dateCreated = source.readLong();
        this.meta = (ItemMeta) source.readValue(ItemMeta.class.getClassLoader());
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation
     *
     * @return a bitmask indicating the set of special object types marshalled by the Parcelable
     * Restricted constructor.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param out   The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeValue(restAdapter);
        out.writeString(shareKey);
        out.writeString(name);
        out.writeString(url);
        out.writeString(shortUrl);
        out.writeLong(size);
        out.writeLong(dateCreated);
        out.writeValue(this.meta);
    }

    /**
     * Gets the share key.
     *
     * @return The share key.
     */
    public String getShareKey() {
        return this.shareKey;
    }

    /**
     * Gets the share name.
     *
     * @return The share name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the share name.
     *
     * @param name The share name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the share url.
     *
     * @return The share url.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Gets the share size.
     *
     * @return The share size.
     */
    public long getSize() {
        return this.size;
    }

    /**
     * Gets the share's application data.
     *
     * @return The share's application data.
     */
    public JsonObject getApplicationData() {
        return this.meta.getApplicationData();
    }

    /**
     * Gets the share's content last modified date.
     *
     * @return The content last modified date.
     */
    public Date getDateContentLastModified() {
        return new Date(this.meta.getDateContentLastModified());
    }

    /**
     * Gets the share's meta last modified date.
     *
     * @return The share's meta last modified date.
     */
    public Date getDateMetaLastModified() {
        return new Date(this.meta.getDateMetaLastModified());
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
    public boolean setName(final String newName, final String password) throws IOException, BitcasaException {
        final AbstractMap<String, String> params = new HashMap<String, String>();
        params.put(BitcasaRESTConstants.PARAM_NAME, newName);
        return this.changeAttributes(params, password);
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
    public boolean setPassword(final String newPassword, final String oldPassword)
            throws IOException, BitcasaException {
        final AbstractMap<String, String> params = new HashMap<String, String>();
        params.put(BitcasaRESTConstants.PARAM_PASSWORD, newPassword);
        return this.changeAttributes(params, oldPassword);
    }

    /**
     * Creates a string containing a concise, human-readable description of Share object.
     *
     * @return The printable representation of Share object.
     */
    @Override
    public String toString() {

        return "\nshare_key[" + this.shareKey + "] \nname[" + this.name + "] \nurl[" + this.url +
                "] \nshort_url[" + this.shortUrl + "] \nsize[" + Long.toString(this.size) +
                "] \ndate_created[" + Long.toString(this.dateCreated) + "]*****";
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
    public boolean changeAttributes(final Map<String, String> values, final String sharePassword)
            throws IOException, BitcasaException {
        final Share share = this.restAdapter.alterShare(this.getShareKey(), values, sharePassword);
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
        return this.restAdapter.browseShare(this.getShareKey(), null);
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
    public Item[] receive(final String path, final BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {
        return this.restAdapter.receiveShare(this.getShareKey(), path, exists);
    }

    /**
     * Deletes the share.
     *
     * @return The value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean delete() throws BitcasaException {
        return this.restAdapter.deleteShare(this.getShareKey());
    }

}
