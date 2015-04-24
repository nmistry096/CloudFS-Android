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
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.Exists;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.RestoreMethod;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.VersionExists;
import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ApplicationData;
import com.bitcasa.cloudfs.model.ItemMeta;
import com.bitcasa.cloudfs.model.Storage;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * The Item class provides accessibility to CloudFS Item.
 */
public abstract class Item {

    /**
     * The REST Adapter instance.
     */
    protected RESTAdapter restAdapter;

    /**
     * The item id.
     */
    protected String id;

    /**
     * The item type.
     */
    protected String type;

    /**
     * The item name.
     */
    protected String name;

    /**
     * The item created date.
     */
    protected Date dateCreated;

    /**
     * The item meta last modified date.
     */
    protected Date dateMetaLastModified;

    /**
     * The item content last modified date.
     */
    protected Date dateContentLastModified;

    /**
     * The item version.
     */
    protected int version;
    /**
     * A value that indicates whether the item is mirrored.
     */
    private boolean isMirrored;
    /**
     * The item's absolute parent path.
     */
    private String absoluteParentPath;

    /**
     * The item's absolute path.
     */
    private String absolutePath;

    /**
     * The item application data.
     */
    private ApplicationData applicationData;

    /**
     * Initializes an instance of the Item.
     *
     * @param restAdapter        The REST Adapter instance.
     * @param meta               The item meta data returned from REST Adapter.
     * @param absoluteParentPath The absolute parent path of this item.
     */
    Item(final RESTAdapter restAdapter, final ItemMeta meta, final String absoluteParentPath) {
        this.restAdapter = restAdapter;
        id = meta.getId();
        type = meta.getType();
        name = meta.getName();
        dateCreated = new Date(meta.getDateCreated());
        dateMetaLastModified = new Date(meta.getDateMetaLastModified());
        dateContentLastModified = new Date(meta.getDateContentLastModified());
        version = meta.getVersion();
        isMirrored = meta.isMirrored();
        applicationData = meta.getApplicationData();
        if (absoluteParentPath == null) {
            this.absoluteParentPath = "/";
        } else {
            this.absoluteParentPath = absoluteParentPath;
        }

        if (absoluteParentPath == null) {
            this.absolutePath = "/" + this.id;
        } else if (absoluteParentPath.equals("/")) {
            this.absolutePath = absoluteParentPath + this.id;
        } else {
            this.absolutePath = absoluteParentPath + '/' + this.id;
        }
    }

    /**
     * Returns a string containing a concise, human-readable description of this object.
     *
     * @return A printable representation of this object.
     */
    @Override
    public String toString() {
        StringBuilder stingBuilder = new StringBuilder();
        stingBuilder.append("\nid[").append(id)
                .append("] type[").append(type)
                .append("] name[").append(name).append("] dateCreated[").append(dateCreated.getTime())
                .append("] dateMetaLastModified[").append(dateMetaLastModified.getTime())
                .append("] dateContentLastModified[").append(dateContentLastModified.getTime())
                .append("] version[").append(version).append("] getIsMirrored[").append(isMirrored)
                .append("]").append("absoluteParentPathId[");

        return stingBuilder.toString();
    }

    /**
     * Gets the item id.
     *
     * @return The item id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the item's path.
     *
     * @return The item's path.
     */
    public String getPath() {
        return this.absolutePath;
    }

    /**
     * Gets the item type.
     *
     * @return The item type.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets a value indicating whether the item is mirrored.
     *
     * @return A value indicating whether the item is mirrored.
     */
    public boolean getIsMirrored() {
        return isMirrored;
    }

    /**
     * Gets the item name.
     *
     * @return The item name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the item name.
     * Updates the CloudFS account instantly.
     *
     * @param name The item name.
     * @return A value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     * @throws IOException      If a network error occurs.
     */
    public boolean setName(String name) throws BitcasaException, IOException {
        HashMap<String, String> values = new HashMap<String, String>();
        values.put(BitcasaRESTConstants.PARAM_NAME, name);
        boolean status = changeAttributes(values, VersionExists.FAIL);
        if (status) {
            this.name = name;
        }
        return status;
    }

    /**
     * Gets the item's created date.
     *
     * @return The item's created date.
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Gets the item's meta last modified date.
     *
     * @return The item's meta last modified date.
     */
    public Date getDateMetaLastModified() {
        return dateMetaLastModified;
    }

    /**
     * Gets the item's content last modified date.
     *
     * @return The item's content last modified date.
     */
    public Date getDateContentLastModified() {
        return dateContentLastModified;
    }

    /**
     * Gets the absolute parent path of the item.
     *
     * @return The absolute parent path of the item.
     */
    public String getAbsoluteParentPath() {
        return absoluteParentPath;
    }

    /**
     * Gets the item's application data.
     * Updates the CloudFS account instantly.
     *
     * @return The item's application data.
     */
    public ApplicationData getApplicationData() {
        return applicationData;
    }

    /**
     * Sets the application data and sends update to CloudFS instantly.
     *
     * @param applicationData The application data of the item.
     * @return A value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     * @throws IOException      If a network error occurs.
     */
    public boolean setApplicationData(ApplicationData applicationData)
            throws BitcasaException, IOException {
        HashMap<String, String> values = new HashMap<String, String>();

        if (applicationData.getAlbumArt() != null) {
            values.put(BitcasaRESTConstants.TAG_ALBUM_ART, applicationData.getAlbumArt());
        }
        if (applicationData.getDigest() != null) {
            values.put(BitcasaRESTConstants.TAG_DIGEST, applicationData.getDigest());
        }
        if (applicationData.getNonce() != null) {
            values.put(BitcasaRESTConstants.TAG_NONCE, applicationData.getNonce());
        }
        if (applicationData.getPayload()!= null) {
            values.put(BitcasaRESTConstants.TAG_PAYLOAD, applicationData.getPayload());
        }
        if (applicationData.getOriginalPath() != null) {
            values.put(BitcasaRESTConstants.TAG_BITCASA_ORIGINAL_PATH, applicationData.getOriginalPath());
        }
        if (applicationData.getRelativeIdPath() != null) {
            values.put(BitcasaRESTConstants.TAG_RELATIVE_ID_PATH, applicationData.getRelativeIdPath());
        }

        JSONObject applicationJson = new JSONObject(values);
        String applicationDataString = applicationJson.toString();


        HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put("application_data", applicationDataString);

        // TODO: Parse JSON accordignly. 
        boolean status = changeAttributes(attributes, VersionExists.FAIL);
        if (status) {
            this.applicationData = applicationData;
        }
        return status;
    }

    /**
     * Changes the specified item attributes.
     *
     * @param values     The attributes to be changed.
     * @param ifConflict The action to be taken if a conflict occurs.
     * @return boolean          A value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     * @throws IOException      If a network error occurs.
     */
    public abstract boolean changeAttributes(HashMap<String, String> values, VersionExists ifConflict)
            throws BitcasaException, IOException;

    /**
     * Moves the item to the given destination.
     *
     * @param destination The destination container which the item needs to be moved.
     * @param exists      The action to perform if the item already exists at the destination.
     * @return An reference to the item at the destination path.
     * @throws IOException      If response data can not be read.
     * @throws BitcasaException If the server can not move the item due to an error.
     */
    public Item move(Container destination, Exists exists)
            throws IOException, BitcasaException {
        if (exists == null) {
            exists = Exists.RENAME;
        }

        return restAdapter.move(this, destination.getPath(), null, exists);
    }

    /**
     * Copies the item to the given destination.
     *
     * @param destination The destination container which the item needs to be copied.
     * @param exists      The action to perform if the item already exists at the destination.
     * @return A reference to the item at the destination path.
     * @throws IOException      If response data can not be read.
     * @throws BitcasaException If the server can not copy the item due to an error.
     */
    public Item copy(Container destination, Exists exists)
            throws IOException, BitcasaException {
        if (exists == null) {
            exists = Exists.RENAME;
        }

        return restAdapter.copy(this, destination.getAbsoluteParentPath(), null, exists);
    }

    /**
     * Deletes the item from CloudFS.
     *
     * @param commit If true, item is deleted immediately. Otherwise, it is moved to the Trash.
     *               The default is false.
     * @param force  If true, item is deleted even if it contains sub-items. The default is false.
     * @return Returns true if the item is deleted successfully, otherwise false.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean delete(boolean commit, boolean force) throws IOException, BitcasaException {
        if (this.getType().equals(FileType.FOLDER)) {
            return restAdapter.deleteFolder(this.getPath(), commit, force);
        } else {
            return restAdapter.deleteFile(this.getPath(), commit, force);
        }
    }

    /**
     * Restore the item to given destination.
     *
     * @param destination     The restore destination.
     * @param method          The restore method.
     * @param restoreArgument The restore argument.
     * @return boolean A value indicating whether the operation was successful or not.
     * @throws UnsupportedEncodingException If encoding is not supported.
     * @throws BitcasaException             BitcasaException If a CloudFS API error occurs.
     */
    public boolean restore(Container destination, RestoreMethod method, String restoreArgument)
            throws UnsupportedEncodingException, BitcasaException {
        return restAdapter.recoverTrashItem(destination.getPath(), method, restoreArgument);
    }

    /**
     * File type interface containing the item types.
     */
    public interface FileType {
        String FILE = "file";
        String FOLDER = "folder";
    }
}
