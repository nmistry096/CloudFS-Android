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

import com.bitcasa.cloudfs.Utils.BitcasaProgressListener;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants;
import com.bitcasa.cloudfs.Utils.BitcasaRESTConstants.Exists;
import com.bitcasa.cloudfs.api.RESTAdapter;
import com.bitcasa.cloudfs.exception.BitcasaException;
import com.bitcasa.cloudfs.model.ItemMeta;

import java.io.IOException;
import java.util.HashMap;

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
     */
    public Folder(final RESTAdapter restAdapter,
                  final ItemMeta meta,
                  final String absoluteParentPath) {
        super(restAdapter, meta, absoluteParentPath);
    }

    /**
     * Uploads a file into the specified file system path.
     *
     * @param filesystemPath The destination file system path of the upload.
     * @param exists         Action to take if the item already exists.
     * @param listener       The progress listener to track the upload progress.
     * @throws IOException      If a network error occurs.
     * @throws BitcasaException If a CloudFS API error occurs.
     */
    public void upload(String filesystemPath,
                       BitcasaProgressListener listener,
                       BitcasaRESTConstants.Exists exists)
            throws IOException, BitcasaException {
        this.restAdapter.uploadFile(this, filesystemPath, exists, listener);
    }

    /**
     * Creates a new folder by name provided.
     *
     * @param name  The name of the folder to be created.
     * @param exist The action to take if the folder to be created already exists.
     * @return The folder that was created.
     * @throws IOException If a network error occurs.
     */
    public Folder createFolder(String name, Exists exist)
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
     * @throws IOException      If a network error occurs.
     */
    public boolean changeAttributes(HashMap<String, String> values, BitcasaRESTConstants.VersionExists ifConflict)
            throws BitcasaException, IOException {
        if (ifConflict == null) {
            ifConflict = BitcasaRESTConstants.VersionExists.FAIL;
        }

        Folder meta;
        meta = restAdapter.alterFolderMeta(this, values, this.version, ifConflict);

        this.id = meta.getId();
        this.type = meta.getType();
        this.name = meta.getName();
        this.dateCreated = meta.getDateCreated();
        this.dateMetaLastModified = meta.getDateMetaLastModified();
        this.dateContentLastModified = meta.getDateContentLastModified();
        this.version = meta.version;

        return meta != null;
    }
}
