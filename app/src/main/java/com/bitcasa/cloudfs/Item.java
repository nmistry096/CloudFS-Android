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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The Item class provides accessibility to CloudFS Item.
 */
public abstract class Item implements Parcelable {

    /**
     * The REST Adapter instance.
     */
    protected final RESTAdapter restAdapter;

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
    protected boolean isMirrored;
    /**
     * The item's absolute parent path.
     */
    protected String absoluteParentPath;

    /**
     * The item's absolute path.
     */
    protected String absolutePath;

    /**
     * The item application data.
     */
    protected JsonObject applicationData;

    /**
     * The item's parent id.
     */
    private String parentId;

    /**
     * The parent state of the item.
     */
    private String state;

    /**
     * The share key of an item if the item is of type share.
     */
    private String shareKey;

    /**
     * Initializes an instance of the Item.
     *
     * @param restAdapter        The REST Adapter instance.
     * @param meta               The item meta data returned from REST Adapter.
     * @param absoluteParentPath The absolute parent path of this item.
     * @param state        The parent state of the item.
     * @param shareKey           The share key of the item if the item is of type share.
     */
    Item(final RESTAdapter restAdapter,
         final ItemMeta meta,
         final String absoluteParentPath,
         final String state,
         final String shareKey) {
        this.restAdapter = restAdapter;
        this.id = meta.getId();
        this.type = meta.getType();
        this.name = meta.getName();
        this.dateCreated = new Date(meta.getDateCreated());
        this.dateMetaLastModified = new Date(meta.getDateMetaLastModified());
        this.dateContentLastModified = new Date(meta.getDateContentLastModified());
        this.version = meta.getVersion();
        this.isMirrored = meta.isMirrored();
        this.applicationData = meta.getApplicationData();
        this.parentId = meta.getParentId();
        if (absoluteParentPath == null) {
            this.absoluteParentPath = "/";
        } else {
            this.absoluteParentPath = absoluteParentPath;
        }

        if (absoluteParentPath == null) {
            this.absolutePath = '/' + this.id;
        } else if ("/".equals(absoluteParentPath)) {
            this.absolutePath = absoluteParentPath + this.id;
        } else {
            this.absolutePath = absoluteParentPath + '/' + this.id;
        }

        this.state = state;
        this.shareKey = shareKey;
    }

    /**
     * Initializes the Item instance.
     *
     * @param source The parcel object parameter.
     */
    public Item(Parcel source) {
        id = source.readString();
        type = source.readString();
        name = source.readString();
        dateCreated = new Date(source.readLong());
        dateMetaLastModified = new Date(source.readLong());
        dateContentLastModified = new Date(source.readLong());
        version = source.readInt();
        isMirrored = source.readInt() != 0;
        applicationData = (JsonObject) new JsonParser().parse(source.readString());
        parentId = source.readString();
        absoluteParentPath = source.readString();
        absolutePath = source.readString();
        restAdapter = (RESTAdapter) source.readValue(
                RESTAdapter.class.getClassLoader());
        state = source.readString();
        shareKey = source.readString();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation
     *
     * @return a bitmask indicating the set of special object types marshalled by the Parcelable
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
        out.writeString(id);
        out.writeString(type);
        out.writeString(name);
        out.writeLong(dateCreated.getTime());
        out.writeLong(dateMetaLastModified.getTime());
        out.writeLong(dateContentLastModified.getTime());
        out.writeInt(version);
        out.writeInt(isMirrored ? 1 : 0);
        out.writeString(applicationData.toString());
        out.writeString(parentId);
        out.writeString(absoluteParentPath);
        out.writeString(absolutePath);
        out.writeValue(restAdapter);
        out.writeString(state);
        out.writeString(shareKey);
    }


    /**
     * Returns a string containing a concise, human-readable description of this object.
     *
     * @return A printable representation of this object.
     */
    @Override
    public String toString() {
        final StringBuilder stingBuilder = new StringBuilder();
        stingBuilder.append("\nid[").append(this.id)
                .append("] type[").append(this.type)
                .append("] name[").append(this.name).append("] dateCreated[").append(this.dateCreated.getTime())
                .append("] dateMetaLastModified[").append(this.dateMetaLastModified.getTime())
                .append("] dateContentLastModified[").append(this.dateContentLastModified.getTime())
                .append("] version[").append(this.version).append("] getIsMirrored[").append(this.isMirrored)
                .append(']').append("absoluteParentPathId[");

        return stingBuilder.toString();
    }

    /**
     * Gets the item id.
     *
     * @return The item id.
     */
    public String getId() {
        return this.id;
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
        return this.type;
    }

    /**
     * Gets a value indicating whether the item is mirrored.
     *
     * @return A value indicating whether the item is mirrored.
     */
    public boolean getIsMirrored() {
        return this.isMirrored;
    }

    /**
     * Gets the item name.
     *
     * @return The item name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the item version number.
     *
     * @return The item version number;
     */
    public int getVersion() {
        return this.version;
    }

    /**
     * Gets the parent state of the item.
     *
     * @return The parent state.
     */
    public String getState() {
        return this.state;
    }

    /**
     * Set the parent state of the item.
     *
     * @param state The parent state to be set.
     */
    public void setState(final String state) {
        this.state = state;
    }

    /**
     * Sets the item name.
     * Updates the CloudFS account instantly.
     *
     * @param name The item name.
     * @return A value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean setName(final String name) throws BitcasaException {
        if (this.state.equals(BitcasaRESTConstants.ITEM_STATE_NORMAL)) {
            final AbstractMap<String, String> values = new HashMap<String, String>();
            values.put(BitcasaRESTConstants.PARAM_NAME, name);
            final boolean status = this.changeAttributes(values, BitcasaRESTConstants.VersionExists.FAIL);
            if (status) {
                this.name = name;
            }
            return status;
        } else {
            throw new BitcasaException("Set name operation cannot be performed on this item.");
        }
    }

    /**
     * Gets the item's created date.
     *
     * @return The item's created date.
     */
    public Date getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Gets the item's meta last modified date.
     *
     * @return The item's meta last modified date.
     */
    public Date getDateMetaLastModified() {
        return this.dateMetaLastModified;
    }

    /**
     * Gets the item's content last modified date.
     *
     * @return The item's content last modified date.
     */
    public Date getDateContentLastModified() {
        return this.dateContentLastModified;
    }

    /**
     * Gets the absolute parent path of the item.
     *
     * @return The absolute parent path of the item.
     */
    public String getAbsoluteParentPath() {
        return this.absoluteParentPath;
    }

    /**
     * Gets the item's application data.
     * Updates the CloudFS account instantly.
     *
     * @return The item's application data.
     */
    public JsonObject getApplicationData() {
        return this.applicationData;
    }

    /**
     * Gets the item's share key if the item is of type share.
     *
     * @return The share key of the item.
     */
    public String getShareKey() {
        return this.shareKey;
    }

    /**
     * Sets the item's share key.
     *
     * @param shareKey The share key to be set.
     */
    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    /**
     * Sets the application data and sends update to CloudFS instantly.
     *
     * @param applicationData The application data to be set.
     * @return A value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public boolean setApplicationData(final JsonObject applicationData)
            throws BitcasaException {
        if (this.state.equals(BitcasaRESTConstants.ITEM_STATE_NORMAL)) {
            final String applicationDataString = applicationData.toString();

            final AbstractMap<String, String> attributes = new HashMap<String, String>();
            attributes.put("application_data", applicationDataString);

            return this.changeAttributes(attributes, BitcasaRESTConstants.VersionExists.FAIL);
        } else {
            throw new BitcasaException("Set application data operation cannot be performed on this item.");
        }
    }

    /**
     * Gets the item's parent id.
     *
     * @return The item's parent id.
     */
    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Changes the specified item attributes.
     *
     * @param values     The attributes to be changed.
     * @param ifConflict The action to be taken if a conflict occurs.
     * @return boolean          A value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public abstract boolean changeAttributes(Map<String, String> values, BitcasaRESTConstants.VersionExists ifConflict)
            throws BitcasaException;

    /**
     * Moves the item to the given destination.
     *
     * @param destination The destination container which the item needs to be moved.
     * @param exists      The action to perform if the item already exists at the destination.
     * @return An reference to the item at the destination path.
     * @throws IOException      If response data can not be read.
     * @throws BitcasaException If the server can not move the item due to an error.
     */
    public Item move(final Container destination, BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {
        if (exists == null) {
            exists = BitcasaRESTConstants.Exists.RENAME;
        }
        if (this.getState().equals(BitcasaRESTConstants.ITEM_STATE_NORMAL)) {
            Item resultItem = this.restAdapter.move(this, destination.getPath(), null, exists);
            resultItem.setParentId(destination.getId());
            this.setState(BitcasaRESTConstants.ITEM_STATE_DEAD);
            return resultItem;
        } else {
            throw new BitcasaException("Move operation cannot be performed on this item.");
        }
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
    public Item copy(final Container destination, final String newName, BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {
        if (exists == null) {
            exists = BitcasaRESTConstants.Exists.RENAME;
        }

        if (this.getState().equals(BitcasaRESTConstants.ITEM_STATE_NORMAL)) {
            Item resultItem = this.restAdapter.copy(this, destination.getPath(), newName, exists);
            resultItem.setParentId(destination.getId());
            return resultItem;
        } else {
            throw new BitcasaException("Copy operation cannot be performed on this item.");
        }
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
    public boolean delete(final boolean commit, final boolean force) throws IOException, BitcasaException {
        boolean successResult = false;

        if (this.getState().equals(BitcasaRESTConstants.ITEM_STATE_TRASH)) {
            if (commit) {
                successResult = this.restAdapter.deleteTrashItem(this.getId());

                if (successResult) {
                    this.setState(BitcasaRESTConstants.ITEM_STATE_DEAD);
                }
            }
        } else if (this.getState().equals(BitcasaRESTConstants.ITEM_STATE_NORMAL)) {
            String path = this.getPath();
            this.applicationData.addProperty("_bitcasa_original_path", path);
            if (this.getType().equals(Item.FileType.FOLDER)) {
                successResult = this.restAdapter.deleteFolder(this.getPath(), commit, force);
            } else {
                successResult = this.restAdapter.deleteFile(this.getPath(), commit);
            }

            if (successResult && !commit) {
                this.setState(BitcasaRESTConstants.ITEM_STATE_TRASH);
            } else if (successResult && commit) {
                this.setState(BitcasaRESTConstants.ITEM_STATE_DEAD);
            }
        } else if (this.getState().equals(BitcasaRESTConstants.ITEM_STATE_SHARE)) {
            throw new BitcasaException("Delete operation should be performed on the main share.");
        }

        return successResult;
    }

    /**
     * Restore the item to given destination.
     *
     * @param destination      The restore destination.
     * @param method           The restore method.
     * @param restoreArgument  The restore argument.
     * @param maintainValidity If true, item maintains it's validity. The default is false.
     * @return boolean A value indicating whether the operation was successful or not.
     * @throws UnsupportedEncodingException If encoding is not supported.
     * @throws BitcasaException             If a CloudFS API error occurs.
     */
    public boolean restore(final Container destination, final BitcasaRESTConstants.RestoreMethod method,
                           final String restoreArgument, final boolean maintainValidity)
            throws UnsupportedEncodingException, BitcasaException {
        final boolean resultValue;
        if (this.getState().equals(BitcasaRESTConstants.ITEM_STATE_TRASH)) {
            final boolean result = this.restAdapter.recoverTrashItem(this.getId(), method, destination.getPath());
            if (result) {
                this.setState(BitcasaRESTConstants.ITEM_STATE_DEAD);
                if (maintainValidity) {
                    Item itemMeta;
                    if (method == BitcasaRESTConstants.RestoreMethod.RESCUE) {
                        if (destination.getPath().isEmpty() ||
                                destination.getState().equals(BitcasaRESTConstants.ITEM_STATE_DEAD)) {
                            this.absolutePath = '/' + this.getId();
                            this.absoluteParentPath = "/";
                            itemMeta = this.getType().equals(Item.FileType.FILE) ?
                                    this.restAdapter.getItemMeta(this.getPath()) : this.restAdapter.getFolderMeta(this.getPath());
                            //TODO: Need to check the error code for file not found.
                            this.maintainItemValidity(itemMeta);
                        } else {
                            this.absoluteParentPath = destination.getPath();
                            this.absolutePath = this.absoluteParentPath + '/' + this.getId();
                            itemMeta = this.getType().equals(Item.FileType.FILE) ?
                                    this.restAdapter.getItemMeta(this.getPath()) : this.restAdapter.getFolderMeta(this.getPath());
                            //TODO: Need to check the error code for file not found.
                            this.maintainItemValidity(itemMeta);
                        }
                    } else if (method == BitcasaRESTConstants.RestoreMethod.RECREATE) {
                        itemMeta = this.getType().equals(Item.FileType.FILE) ?
                                this.restAdapter.getItemMeta(this.getPath()) : this.restAdapter.getFolderMeta(this.getPath());
                        if (itemMeta == null) {
                            this.absolutePath = destination.getAbsoluteParentPath() + '/' + this.getId();
                            this.absoluteParentPath = destination.getAbsoluteParentPath();
                        }
                    } else {
                        itemMeta = this.getType().equals(Item.FileType.FILE) ?
                                this.restAdapter.getItemMeta(this.getPath()) : this.restAdapter.getFolderMeta(this.getPath());
                        this.maintainItemValidity(itemMeta);
                    }
                    this.setState(BitcasaRESTConstants.ITEM_STATE_NORMAL);
                }
            }
            resultValue = result;
        } else {
            resultValue = false;
        }

        return resultValue;
    }

    /**
     * File type interface containing the item types.
     */
    public interface FileType {
        final String FILE = "file";
        final String FOLDER = "folder";
    }

    /**
     * Set the attributes of an item which has been restored.
     *
     * @param item The item data to be set.
     */
    private void maintainItemValidity(final Item item) {
        this.id = item.getId();
        this.type = item.getType();
        this.name = item.getName();
        this.dateCreated = item.getDateCreated();
        this.dateMetaLastModified = item.getDateMetaLastModified();
        this.dateContentLastModified = item.getDateContentLastModified();
        this.version = item.getVersion();
        this.isMirrored = item.getIsMirrored();
        this.applicationData = item.applicationData;
    }
}
