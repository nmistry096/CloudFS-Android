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

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ItemMeta;

import java.io.IOException;
import java.util.Map;

/**
 * The Folder class provides accessibility to CloudFS Folder.
 */
public class Folder extends Container {

    /**
     * Initializes an instance of CloudFS Folder.
     *
     * @param restAdapter        The REST Adapter instance.
     * @param meta               The folder meta data returned from REST Adapter.
     * @param absoluteParentPath The absolute parent path of this folder.
     * @param parentState        The parent state of the item.
     * @param shareKey           The share key of the item if the item is of type share.
     */
    public Folder(final RESTAdapter restAdapter,
                  final ItemMeta meta,
                  final String absoluteParentPath,
                  final String parentState,
                  final String shareKey) {
        super(restAdapter, meta, absoluteParentPath, parentState, shareKey);
    }

    /**
     * Initializes the Folder instance.
     *
     * @param source The parcel object parameter.
     */
    public Folder(Parcel source) {
        super(source);
    }

    /**
     * {@inheritDoc}
     */
    public static final Parcelable.Creator<Folder> CREATOR = new Parcelable.Creator<Folder>() {

        /**
         *Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Folder createFromParcel(Parcel source) {
            return new Folder(source);
        }

        /**
         *Create a new array of the Parcelable class
         * @param size Size of the array
         * @return Returns an array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };

    /**
     * Uploads a file into the specified file system path.
     *
     * @param filesystemPath The destination file system path of the upload.
     * @param exists         Action to take if the item already exists.
     * @param listener       The progress listener to track the upload progress.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public File upload(final String filesystemPath,
                       final BitcasaProgressListener listener,
                       final BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {
        return this.restAdapter.uploadFile(this, filesystemPath, exists, listener);
    }

    /**
     * Creates a new folder by name provided.
     *
     * @param name  The name of the folder to be created.
     * @param exist The action to take if the folder to be created already exists.
     * @return The folder that was created.
     * @throws IOException If a network error occurs.
     */
    public Folder createFolder(final String name, final BitcasaRESTConstants.Exists exist)
            throws IOException, BitcasaException {
        return this.restAdapter.createFolder(name, this, exist);
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

        final Folder meta = this.restAdapter.alterFolderMeta(this, values, this.version, ifConflict);

        this.id = meta.getId();
        this.type = meta.getType();
        this.name = meta.getName();
        this.dateCreated = meta.getDateCreated();
        this.dateMetaLastModified = meta.getDateMetaLastModified();
        this.dateContentLastModified = meta.getDateContentLastModified();
        this.version = meta.version;
        this.applicationData = meta.applicationData;

        return meta != null;
    }
}
