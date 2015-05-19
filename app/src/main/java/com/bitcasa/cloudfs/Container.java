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

import java.io.IOException;
import java.util.Map;

/**
 * The Container class provides accessibility to CloudFS Container.
 */
public class Container extends Item {

    /**
     * Initializes an instance of Container.
     *
     * @param restAdapter        The REST Adapter instance.
     * @param meta               The container meta data returned from REST Adapter.
     * @param absoluteParentPath The absolute parent path of this container.
     * @param parentState        The parent state of the item.
     * @param shareKey           The share key of the item if the item is of type share.
     */
    public Container(final RESTAdapter restAdapter, final ItemMeta meta,
                     final String absoluteParentPath, final String parentState, final String shareKey) {
        super(restAdapter, meta, absoluteParentPath, parentState, shareKey);
    }

    /**
     * Initializes the Folder instance.
     *
     * @param source The parcel object parameter.
     */
    public Container(Parcel source)
    {
        super(source);
    }

    /**
     * {@inheritDoc}
     */
    public static final Parcelable.Creator<Container> CREATOR = new Parcelable.Creator<Container>() {

        /**
         *Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Container createFromParcel(Parcel source) {
                return new Container(source);
        }

        /**
         *Create a new array of the Parcelable class
         * @param size Size of the array
         * @return Returns an array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public Container[] newArray(int size) {
            return new Container[size];
        }
    };

    /**
     * Lists the files and folders in the container.
     *
     * @return The list of files and folders in the container.
     */
    public Item[] list() throws IOException, BitcasaException {
        if (this.getState().equals(BitcasaRESTConstants.ITEM_STATE_NORMAL)) {
            return this.restAdapter.getList(this.getPath(), -1, 1, null);
        } else if (this.getState().equals(BitcasaRESTConstants.ITEM_STATE_SHARE)) {
            return this.restAdapter.browseShare(this.getShareKey(), this.getPath());
        } else if (this.getState().equals(BitcasaRESTConstants.ITEM_STATE_TRASH)) {
            return this.restAdapter.browseTrash(this.getPath());
        } else {
            throw new BitcasaException("List operation cannot be performed on this item.");
        }
    }

    /**
     * Changes the specified item attributes.
     *
     * @param values     The attributes to be changed.
     * @param ifConflict The action to be taken if a conflict occurs.
     * @return boolean          A value indicating whether the operation was successful or not.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    @Override
    public boolean changeAttributes(final Map<String, String> values, BitcasaRESTConstants.VersionExists ifConflict)
            throws BitcasaException {
        if (ifConflict == null) {
            ifConflict = BitcasaRESTConstants.VersionExists.FAIL;
        }

        final Container meta = this.restAdapter.alterFolderMeta(this, values, this.version, ifConflict);

        this.id = meta.getId();
        this.type = meta.getType();
        this.name = meta.getName();
        this.dateCreated = meta.getDateCreated();
        this.dateMetaLastModified = meta.getDateMetaLastModified();
        this.dateContentLastModified = meta.getDateContentLastModified();
        this.version = meta.version;
        this.applicationData = meta.applicationData;

        return true;
    }
}
