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
import com.bitcasa.cloudfs.model.ItemMeta;

import java.io.IOException;
import java.util.HashMap;

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
     */
    public Container(final RESTAdapter restAdapter, final ItemMeta meta,
                     final String absoluteParentPath) {
        super(restAdapter, meta, absoluteParentPath);
    }

    /**
     * Lists the files and folders in the container.
     *
     * @return The list of files and folders in the container.
     */
    public Item[] list() throws IOException, BitcasaException {
        return this.restAdapter.getList(getAbsoluteParentPath(), -1, 1, null);
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

        Container meta;
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
